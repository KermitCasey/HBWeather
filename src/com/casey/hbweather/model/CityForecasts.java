package com.casey.hbweather.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月13日 下午10:13:53    类说明  
 *
 */
public class CityForecasts implements Serializable {
	private String weaid;
	private ArrayList<CollectionCity> cityForeCast;

	public CityForecasts() {

	}

	public CityForecasts(String weaid) {
		this.weaid = weaid;
	}

	public CityForecasts(String weaid, ArrayList<CollectionCity> collectionCities) {
		this.weaid = weaid;
		this.cityForeCast = collectionCities;
	}

	public String getWeaid() {
		return weaid;
	}

	public void setWeaid(String weaid) {
		this.weaid = weaid;
	}

	public ArrayList<CollectionCity> getCityForeCast() {
		return cityForeCast;
	}

	public void setCityForeCast(ArrayList<CollectionCity> cityForeCast) {
		this.cityForeCast = cityForeCast;
	}

}
