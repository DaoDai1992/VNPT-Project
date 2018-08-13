package com.vnpt.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    List<Address> findByFullAddressContaining(String q);

}
