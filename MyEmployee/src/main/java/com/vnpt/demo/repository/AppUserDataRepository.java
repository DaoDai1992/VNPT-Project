package com.vnpt.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.AppUserData;

public interface AppUserDataRepository extends CrudRepository<AppUserData, Integer>,AppUserDataRepositoryCustom {
}
