package com.example.rockolweather;

import java.util.List;

import com.example.rockolweather.db.RockolWeatherDB;
import com.example.rockolweather.model.*;
import com.example.rockolweather.util.HttpCallbackListener;
import com.example.rockolweather.util.HttpUtil;
import com.example.rockolweather.util.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class ChooseAreaActivity extends Activity {

	private RockolWeatherDB rockolDB;
	
	private ListView listView;
	private TextView titleView;
	ArrayAdapter<String> adapter;
	private List<String> list_data;
	
	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countyList;
	
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY=2;
	
	private int currentLevel;
	
	private Province selectedProvince;
	private City selectedCity;
	//private County selecedCounty;
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		listView = (ListView)findViewById(R.id.list_view);
		titleView = (TextView)findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list_data);
		listView.setAdapter(adapter);
		rockolDB = RockolWeatherDB.getInstance(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3){
				if(currentLevel == LEVEL_PROVINCE){
					selectedProvince = provinceList.get(index);
					queryCities();
				}else if(currentLevel == LEVEL_CITY){
					selectedCity = cityList.get(index);
					queryCounties();
				}
			}
			
		});
		
		queryProvince();
	}
	
	private void queryProvince() {
		provinceList = rockolDB.loadProvince();
		if(provinceList.size()>0){
			list_data.clear();
			
			for(Province province :provinceList){
				list_data.add(province.getName());
			}
			
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleView.setText("China");
			currentLevel = LEVEL_PROVINCE;
		}else {
			queryFromServer(null,"province");
		}
	}

	private void queryCities() {
		cityList = rockolDB.loadCities(selectedProvince.getId());
		if(cityList.size() > 0){
			list_data.clear();
			for(City city : cityList){
				list_data.add(city.getName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleView.setText(selectedProvince.getName());
			currentLevel = LEVEL_CITY;
		}else {
			queryFromServer(selectedProvince.getCode(),"city");
		}
		
	}
	
	private void queryCounties() {
		countyList = rockolDB.loadCounty(selectedCity.getId());
		if(countyList.size() > 0){
			list_data.clear();
			
			for(County county : countyList){
				list_data.add(county.getName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleView.setText(selectedCity.getName());
			currentLevel = LEVEL_COUNTY;
		}else {
			queryFromServer(selectedCity.getCode(),"county");
		}
	}
	
	private void queryFromServer(final String code, final String type){
		String address;
		if(!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
		}else {
			address = "http://www.weather.com.cn/data/list3/city";
		}
		
		showProgressDialog();
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){
			@Override
			public void onFinish(String response){
				boolean result= false;
				
				if("province".equals(type)){
					
					result= Utility.handleProvincesResponse(rockolDB, response);
					
				}else if("city".equals(type)){
					result = Utility.handCitiesResponse(rockolDB, response);
				}else if("county".equals(type)){
					result = Utility.handCountiesResponse(rockolDB, response);
				}
				
				if(result){
					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							closeProgressDialog();
							if("province".equals(type)){
								queryProvince();
							}else if("city".equals(type)){
								queryCities();
							}else if("county".equals(type)){
								queryCounties();
							}
						}
						
					});
				}
				
			}
			
			@Override
			public void onError(Exception e){
				runOnUiThread(new Runnable(){
					@Override
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "failed to load data!", Toast.LENGTH_SHORT).show();
					}
				});
			}
			
		});
	}
	
	
	private void showProgressDialog() {
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("loading...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	
	private void closeProgressDialog() {
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
}
