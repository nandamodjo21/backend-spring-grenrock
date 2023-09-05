package com.greenrock.backendgreenrock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenrock.backendgreenrock.entity.User;

public interface UserDao extends JpaRepository<User, String> {

}
