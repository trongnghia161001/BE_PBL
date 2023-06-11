package com.bookstore.addressservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByUserId(Long userId);

    List<Address> findByFullNameContainingIgnoreCase(String fullName);

    List<Address> findByPhoneNumberContainingIgnoreCase(String phoneNumber);

}
