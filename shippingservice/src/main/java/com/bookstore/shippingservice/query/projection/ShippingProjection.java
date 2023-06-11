package com.bookstore.shippingservice.query.projection;

import com.bookstore.commonservice.model.ShippingResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsShippingByIdQuery;
import com.bookstore.commonservice.query.GetDetailsShippingQuery;
import com.bookstore.shippingservice.command.data.Shipping;
import com.bookstore.shippingservice.command.data.ShippingRepository;
import com.bookstore.shippingservice.query.model.ShippingResponseModel;
import com.bookstore.shippingservice.query.queries.GetAllShippingQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShippingProjection {
    @Autowired
    private ShippingRepository repository;

    @QueryHandler
    public List<ShippingResponseModel> getAllBook(GetAllShippingQuery getAll) {
        List<ShippingResponseModel> list = new ArrayList<>();
        List<Shipping> List = repository.findAll();
        List.forEach(book -> {
            ShippingResponseModel model = new ShippingResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public ShippingResponseCommonModel getShippingByUser(GetDetailsShippingQuery query) {
        ShippingResponseCommonModel responseCommonModel = new ShippingResponseCommonModel();
        Shipping shipping = repository.findByUserId(query.getId());
        BeanUtils.copyProperties(shipping, responseCommonModel);
        return responseCommonModel;
    }

    @QueryHandler
    public ShippingResponseCommonModel getShippingById(GetDetailsShippingByIdQuery query) {
        ShippingResponseCommonModel responseCommonModel = new ShippingResponseCommonModel();
        Optional<Shipping> shipping = repository.findById(query.getShippingId());
        if (shipping.isPresent()) {
            BeanUtils.copyProperties(shipping.get(), responseCommonModel);
            return responseCommonModel;
        } else {
            return null;
        }


    }
}
