package com.vnpt.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String role_name);

}