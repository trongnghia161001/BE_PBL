package com.bookstore.bill_promotionservice.command.saga;


import com.bookstore.bill_promotionservice.command.command.DeleteBillPromotionCommand;
import com.bookstore.bill_promotionservice.command.events.BillPromotionCreateEvent;
import com.bookstore.bill_promotionservice.command.events.BillPromotionDeleteEvent;
import com.bookstore.commonservice.command.UpdateStatusBillCommand;
import com.bookstore.commonservice.model.BillResponseCommonModel;
import com.bookstore.commonservice.model.PromotionResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsBillByIdQuery;
import com.bookstore.commonservice.query.GetDetailsPromotionQuey;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class BillPromotionSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BillPromotionCreateEvent event) {
        System.out.println("BillPromotionCreatedEvent in Saga for billId: " +
                event.getBillId() + " promotionId: "+event.getPromotionId());
        try {
            SagaLifecycle.associateWith("id", event.getBillId());
            GetDetailsPromotionQuey promotionQuey =
                    new GetDetailsPromotionQuey(event.getPromotionId());
            PromotionResponseCommonModel promotionResponseCommonModel = queryGateway.
                    query(promotionQuey, ResponseTypes.instanceOf(
                    PromotionResponseCommonModel.class
            )).join();
            if (promotionResponseCommonModel != null && promotionResponseCommonModel.getStatus() == 0) {
                GetDetailsBillByIdQuery getDetailsBillByIdQuery =
                        new GetDetailsBillByIdQuery(event.getBillId());
                BillResponseCommonModel billResponseCommonModel =
                        queryGateway.query(getDetailsBillByIdQuery,
                                ResponseTypes.instanceOf(BillResponseCommonModel.class)).join();
                if (billResponseCommonModel != null) {
                    UpdateStatusBillCommand command =
                            new UpdateStatusBillCommand();
                    BeanUtils.copyProperties(billResponseCommonModel, command);
                    command.setTotalMoney(billResponseCommonModel.getTotalMoney() -
                            promotionResponseCommonModel.getDiscountValue());
                    commandGateway.sendAndWait(command);
                    SagaLifecycle.end();
                }
            }
        } catch (Exception e) {
            rollBackBillPromotion(event.getId());
        }
    }

    private void rollBackBillPromotion(String id) {
        try {
            commandGateway.sendAndWait(new DeleteBillPromotionCommand(id));
        } catch (Exception e) {
            System.out.println("Error occurred while rolling back bill " +
                    "promotion" +
                    " " +
                    "record: " + e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BillPromotionDeleteEvent event) {
        System.out.println("BorrowDeletedEvent in Saga for Borrowing Id : {} " + event.getId());
        SagaLifecycle.end();
    }
}
