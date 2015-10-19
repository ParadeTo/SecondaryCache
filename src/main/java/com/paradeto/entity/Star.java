package com.paradeto.entity;

import java.io.Serializable;

public class Star implements Serializable{


	private static final long serialVersionUID = 6294224317839506005L;
	private Integer id;
	private String name;
	private Integer age;
	private String vocation;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getVocation() {
		return vocation;
	}
	public void setVocation(String vocation) {
		this.vocation = vocation;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
