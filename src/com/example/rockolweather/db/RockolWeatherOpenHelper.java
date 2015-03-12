package com.example.rockolweather.db;

import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.*;

public class RockolWeatherOpenHelper extends SQLiteOpenHelper {
	/**
	 * create province
	 */
	public static final String CREATE_PROVINCE = "create table Province ("
					+"id integer primary key autoincrement,"
					+"province_name text,"
					+"province_code text)";
	/**
	 * create city
	 */
	public static final String CREATE_CITY = "create table City ("
			+"id integer primary key autoincrement,"
			+"city_name text,"
			+"city_code text,"
			+"province_id integer)";
	/**
	 * create county
	 */
	public static final String CREATE_COUNTY = "create table County ("
			+"id integer primary key autoincrement,"
			+"county_name text,"
			+"county_code text,"
			+"city_id integer)";
	
	public RockolWeatherOpenHelper(Context context, String name, CursorFactory factory, int version){
		super (context, name, factory, version);
	}
	
	@Override
	public void onCreate (SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
