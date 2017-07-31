package com.suncj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.suncj.dao.UserDao;
import com.suncj.dao.UserPermissionDao;
import com.suncj.dao.UserRoleDao;
import com.suncj.entity.Permission;
import com.suncj.entity.Role;
import com.suncj.entity.User;
import com.suncj.service.UserService;

@Component
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private UserPermissionDao userPermissionDao;

	@Override
	public User findUserByName(String username) {
		return userDao.findUserByName(username);
	}

	@Override
	public List<Role> selectRolesByUserId(int userId) {
		return userRoleDao.findRolesByUserId(userId);
	}

	@Override
	public List<Permission> selectPermissionsByUserId(int userId) {
		return userPermissionDao.findPermissionsByUserId(userId);
	}
}
