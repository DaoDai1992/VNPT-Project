package com.vnpt.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.AppRoleDataDetail;

public interface AppRoleDataDetailRepository extends CrudRepository<AppRoleDataDetail, Integer>,AppRoleDataDetailRepositoryCustom {
}
