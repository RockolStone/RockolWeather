package com.example.rockolweather.util;

import android.text.TextUtils;

import com.example.rockolweather.db.RockolWeatherDB;
import com.example.rockolweather.model.City;
import com.example.rockolweather.model.County;
import com.example.rockolweather.model.Province;

public class Utility {

	public synchronized static boolean handleProvincesResponse(RockolWeatherDB rockolWeatherDB, String response) {
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0) {
				for(String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setCode(array[0]);
					province.setName(array[1]);
					rockolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean handCitiesResponse(RockolWeatherDB rockolWeatherDB, String response) {
		if(!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length > 0){
				for(String p :allCities){
					String [] array = p.split("\\|");
					City city = new City();
					city.setCode(array[0]);
					city.setName(array[1]);
					rockolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean handCountiesResponse(RockolWeatherDB rockolWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			if(allCounties != null && allCounties.length > 0) {
				for(String p : allCounties){
					String[] array = p.split("\\|");
					County county = new County();
					county.setCode(array[0]);
					county.setName(array[1]);
					rockolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}
