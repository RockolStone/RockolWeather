package com.example.rockolweather.model;

public class County {
	private int id;
	private String co_name;
	private String co_code;
	private int cityId;
	
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}
	
	public void setName(String name){
		this.co_name = name;
	}
	
	public String getName(){
		return co_name;
	}
	
	public void setCode(String code){
		this.co_code = code;
	}
	public String getCode(){
		return co_code;
	}
	
	public void setCityId(int id){
		this.cityId = id; 
	}
	public int getCityId(){
		return cityId;
	}
}
