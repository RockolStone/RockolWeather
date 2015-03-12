package com.example.rockolweather.model;

public class City {
	private int id;
	private String c_name;
	private String c_code;
	private int provinceId;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	public void setName(String name){
		this.c_name = name;
	}
	public String getName(){
		return c_name;
	}
	
	public void setCode(String code){
		this.c_code = code;
	}
	public String getCode(){
		return c_code;
	}
	
	public void setProvinceId(int id){
		this.provinceId = id;
	}
	public int getProvinceId(){
		return provinceId;
	}
}
