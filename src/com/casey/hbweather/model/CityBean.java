package com.casey.hbweather.model;

import org.kymjs.aframe.database.annotate.Id;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月10日 下午4:33:35    类说明  
 *
 */
public class CityBean {

	@Id
	private String id;
	private String weaid;
	private String citynm;
	private String cityno;
	private String cityid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeaid() {
		return weaid;
	}

	public void setWeaid(String weaid) {
		this.weaid = weaid;
	}

	public String getCitynm() {
		return citynm;
	}

	public void setCitynm(String citynm) {
		this.citynm = citynm;
	}

	public String getCityno() {
		return cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	@Override
	public String toString() {
		return this.citynm;
	}

}
