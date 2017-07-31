package com.suncj.entity;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = 1983152955502417359L;

	private int id;

	private String name;

	private String title;

	private String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
