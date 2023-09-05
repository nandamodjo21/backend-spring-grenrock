package com.greenrock.backendgreenrock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenrock.backendgreenrock.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

}
