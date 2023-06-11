package com.bookstore.addressservice.query.projection;

import com.bookstore.addressservice.command.data.Address;
import com.bookstore.addressservice.command.data.AddressRepository;
import com.bookstore.addressservice.query.model.AddressResponseModel;
import com.bookstore.addressservice.query.queries.GetAllAddressByFirstnameQuery;
import com.bookstore.addressservice.query.queries.GetAllAddressByPhoneQuery;
import com.bookstore.addressservice.query.queries.GetAllAddressQuery;
import com.bookstore.commonservice.model.AddressResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsAddressQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressProjection {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressQuery getAllBookQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        List<Address> bookList = repository.findAll();
        bookList.forEach(book -> {
            AddressResponseModel model = new AddressResponseModel();
            BeanUtils.copyProperties(book, model);
            model.setAddressLine1(book.getAddressLine1());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public AddressResponseCommonModel handler(GetDetailsAddressQuery getDetailsAddressQuery) {
        AddressResponseCommonModel model = new AddressResponseCommonModel();
        Address address = repository.findByUserId(getDetailsAddressQuery.getUserId());
        if (address != null) {
            BeanUtils.copyProperties(address, model);
            return model;
        } else {
            return null;
        }
    }

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressByFirstnameQuery getAllAddressByFirstnameQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        List<Address> bookList =
                repository.findByFullNameContainingIgnoreCase(getAllAddressByFirstnameQuery.getFirstName());
        bookList.forEach(book -> {
            AddressResponseModel model = new AddressResponseModel();
            BeanUtils.copyProperties(book, model);
            model.setAddressLine1(book.getAddressLine1());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressByPhoneQuery getAllAddressByPhoneQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        List<Address> bookList = repository.findByPhoneNumberContainingIgnoreCase(getAllAddressByPhoneQuery.getPhoneNumber());
        bookList.forEach(book -> {
            AddressResponseModel model = new AddressResponseModel();
            BeanUtils.copyProperties(book, model);
            model.setAddressLine1(book.getAddressLine1());
            list.add(model);
        });
        return list;
    }
}
