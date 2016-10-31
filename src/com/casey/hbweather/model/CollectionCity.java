package com.casey.hbweather.model;

import java.io.Serializable;

import org.kymjs.aframe.database.annotate.Id;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月11日 下午4:18:53    类说明  
 *
 */
public class CollectionCity implements Serializable {

	@Id
	private String id;
	private String weaid;
	private String citynm;
	private String cityno;
	private String cityid;

	private String week;// 星期六",
	private String temperature;// 12℃\/-3℃",
	private String days;// 2014-12-13",
	private String weather_icon;// http:\/\/api.k780.com:88\/upload\/weather\/d\/1.gif",
	private int temp_high;// 12",
	private int temp_low;// -3",
	private String humi_low;// 0",
	private String weatid1;// 2",
	private String humidity;// 0℉\/0℉",
	private String winpid;// 125",
	private String windid;// 124",
	private String winp;// 微风",
	private String wind;// 无持续风向",
	private String weather;// 多云",
	private String humi_high;// 0",
	private int weatid;// 2",
	private String weather_icon1;// http:\/\/api.k780.com:88\/upload\/weather\/n\/1.gif"
	private String refershTime;

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

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getWeather_icon() {
		return weather_icon;
	}

	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}

	public int getTemp_high() {
		return temp_high;
	}

	public void setTemp_high(int temp_high) {
		this.temp_high = temp_high;
	}

	public int getTemp_low() {
		return temp_low;
	}

	public void setTemp_low(int temp_low) {
		this.temp_low = temp_low;
	}

	public String getHumi_low() {
		return humi_low;
	}

	public void setHumi_low(String humi_low) {
		this.humi_low = humi_low;
	}

	public String getWeatid1() {
		return weatid1;
	}

	public void setWeatid1(String weatid1) {
		this.weatid1 = weatid1;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWinpid() {
		return winpid;
	}

	public void setWinpid(String winpid) {
		this.winpid = winpid;
	}

	public String getWindid() {
		return windid;
	}

	public void setWindid(String windid) {
		this.windid = windid;
	}

	public String getWinp() {
		return winp;
	}

	public void setWinp(String winp) {
		this.winp = winp;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getHumi_high() {
		return humi_high;
	}

	public void setHumi_high(String humi_high) {
		this.humi_high = humi_high;
	}

	public int getWeatid() {
		return weatid;
	}

	public void setWeatid(int weatid) {
		this.weatid = weatid;
	}

	public String getWeather_icon1() {
		return weather_icon1;
	}

	public void setWeather_icon1(String weather_icon1) {
		this.weather_icon1 = weather_icon1;
	}

	public String getRefershTime() {
		return refershTime;
	}

	public void setRefershTime(String refershTime) {
		this.refershTime = refershTime;
	}

}
