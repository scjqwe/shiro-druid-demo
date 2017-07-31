package com.suncj.service;

import java.util.List;

import com.suncj.entity.Permission;
import com.suncj.entity.Role;
import com.suncj.entity.User;

public interface UserService {
	
	public User findUserByName(String username);
	
	public List<Role> selectRolesByUserId(int userId);
	
	public List<Permission> selectPermissionsByUserId(int userId);
}
