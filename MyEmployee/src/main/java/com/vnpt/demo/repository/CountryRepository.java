package com.vnpt.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.Country;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findByNameContaining(String q);

}
