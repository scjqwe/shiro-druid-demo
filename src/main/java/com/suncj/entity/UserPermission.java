package com.suncj.entity;

import java.io.Serializable;

public class UserPermission implements Serializable {
	private static final long serialVersionUID = 3924094861449895142L;

	private int id;

	private int userId;

	private int permissionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

}
