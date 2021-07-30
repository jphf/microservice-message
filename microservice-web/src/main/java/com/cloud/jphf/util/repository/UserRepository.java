package com.cloud.jphf.util.repository;

import org.springframework.data.repository.CrudRepository;

import com.cloud.jphf.util.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);

}
