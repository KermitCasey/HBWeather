package com.casey.hbweather.model;

import java.io.Serializable;


public class IndicesBean implements Serializable{
	private String id;// "5",
	private String name;// "清江画廊景区",

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}