package com.bookstore.promotionsservice.query.projection;

import com.bookstore.commonservice.model.PromotionResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsPromotionQuey;
import com.bookstore.promotionsservice.command.data.Promotions;
import com.bookstore.promotionsservice.command.data.PromotionsRepository;
import com.bookstore.promotionsservice.query.model.PromotionsResponseModel;
import com.bookstore.promotionsservice.query.queries.GetAllPromotionsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PromotionsProjection {

    @Autowired
    private PromotionsRepository repository;

    @QueryHandler
    public List<PromotionsResponseModel> getAllBook(GetAllPromotionsQuery getAll) {
        List<PromotionsResponseModel> list = new ArrayList<>();
        List<Promotions> List = repository.findAll();
        List.forEach(book -> {
            PromotionsResponseModel model = new PromotionsResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public PromotionResponseCommonModel getPromotion(GetDetailsPromotionQuey get) {
        Promotions promotions = repository.findByPromoCode(get.promoCode);
        if (promotions != null) {
            PromotionResponseCommonModel model = new PromotionResponseCommonModel();
            BeanUtils.copyProperties(promotions, model);
            return model;
        } else {
            return null;
        }
    }
}
