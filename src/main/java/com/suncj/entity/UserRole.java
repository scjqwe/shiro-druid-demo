package com.suncj.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
	private static final long serialVersionUID = 8212292559521437319L;

	private int id;

	private int roleId;

	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
