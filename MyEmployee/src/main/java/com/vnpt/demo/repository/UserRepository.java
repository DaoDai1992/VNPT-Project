package com.vnpt.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.vnpt.demo.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	 User findByUsername(String user_name);

}