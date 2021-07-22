package com.jphf.cloud.util.repository;

import org.springframework.data.repository.CrudRepository;

import com.jphf.cloud.util.entity.User;



public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
