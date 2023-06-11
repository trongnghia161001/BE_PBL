package com.bookstore.paymentsservice.query.controller;

import com.bookstore.commonservice.model.BillResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsBillQuery;
import com.bookstore.paymentsservice.config.Config;
import com.bookstore.paymentsservice.query.model.PaymentResDTO;
import com.bookstore.paymentsservice.query.model.PaymentsResponseModel;
import com.bookstore.paymentsservice.query.model.TransactionStatusDTO;
import com.bookstore.paymentsservice.query.queries.GetAllPaymentsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<PaymentsResponseModel> getAll() {
        GetAllPaymentsQuery getAllBookQuery = new GetAllPaymentsQuery();
        List<PaymentsResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(PaymentsResponseModel.class)).join();
        return list;
    }

    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment(
            HttpServletRequest req, HttpServletResponse resp,
            @RequestParam(required = false) String billId,
            @RequestParam(required = false) Long userId
    ) throws UnsupportedEncodingException {
        GetDetailsBillQuery billQuery = new GetDetailsBillQuery(billId, userId);
        BillResponseCommonModel billResponseCommonModel = queryGateway.query(billQuery,
                ResponseTypes.instanceOf(BillResponseCommonModel.class)).join();

        double amount = billResponseCommonModel.getTotalMoney() * 100;
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);
        String orderType = req.getParameter("ordertype");
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(((long) amount * 100)));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_OrderType", orderType);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("OK");
        paymentResDTO.setMessage("Successfully");
        paymentResDTO.setURL(paymentUrl);


        return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
    }

    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(@RequestParam(value = "vnp_Amount") String amount,
                                         @RequestParam(value = "vnp_BankCode") String bankCode,
                                         @RequestParam(value = "vnp_OrderInfo") String order,
                                         @RequestParam(value = "vnp_ResponseCode") String responseCode) {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")) {
            transactionStatusDTO.setStatus("OK");
            transactionStatusDTO.setMessage("Successfully");
            transactionStatusDTO.setData("");

        } else {
            transactionStatusDTO.setStatus("NO");
            transactionStatusDTO.setMessage("Failed");
            transactionStatusDTO.setData("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }
}

//update lai trang thai cua hoa don la da thanh toan hay chua thanh toan