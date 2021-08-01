package com.jphf.cloud.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.jphf.cloud.user.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);

}
