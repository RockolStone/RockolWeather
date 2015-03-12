package com.example.rockolweather.model;

public class Province {
	private int id;
	private String p_name;
	private String p_code;
	
	/*
	public Province (String name ,String code) {
		this.p_name = name;
		this.p_code = code;
	}
	*/
	public int getId(){
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name){
		this.p_name = name;
	}
	public void setCode(String code) {
		this.p_code = code;
	}
	
	public String getName() {
		return p_name;
	}
	public String getCode() {
		return p_code;
	}
}
