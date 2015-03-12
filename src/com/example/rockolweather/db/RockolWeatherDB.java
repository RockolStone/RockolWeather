package com.example.rockolweather.db;

import java.util.*;

import com.example.rockolweather.model.City;
import com.example.rockolweather.model.County;
import com.example.rockolweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.*;

public class RockolWeatherDB {
	/**
	 * DB name
	 */
	public static final String DB_NAME = "Rockol_Weather";
	
	/**
	 * DB version
	 */
	public static final int VERSION = 1;
	
	private static RockolWeatherDB rockolWeatherDB;
	
	private SQLiteDatabase db;
	
	private RockolWeatherDB(Context context) {
		RockolWeatherOpenHelper dbHelper = new RockolWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static RockolWeatherDB getInstance(Context context){
		if(rockolWeatherDB == null){
			rockolWeatherDB = new RockolWeatherDB(context);
		}
		return rockolWeatherDB;
	}
	
	public void saveProvince(Province province){
		if(province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getName());
			values.put("province_code", province.getCode());
			db.insert("Province", null, values);
		}
	}
	
	public List<Province> loadProvince() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province p = new Province();
				p.setId(cursor.getInt(cursor.getColumnIndex("id")));
				p.setName(cursor.getString(cursor.getColumnIndex("province_name")));
				p.setCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(p);
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	public void saveCity(City city) {
		if(city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getName());
			values.put("city_code", city.getCode());
			values.put("provinceId", city.getProvinceId());
			db.insert("City",null,values);
		}
	}
	
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[] {String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
			}while(cursor.moveToNext());
		}
		
		if(cursor != null){
			cursor.close();
		}
		
		return list;
	}
	
	public void saveCounty(County county) {
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getName());
			values.put("county_code", county.getCode());
			values.put("cityId", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	public List<County> loadCounty(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id= ?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()) {
			do{
				
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				county.setName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCode(cursor.getString(cursor.getColumnIndex("county_code")));
				
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
}
