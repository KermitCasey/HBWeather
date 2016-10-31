package com.casey.hbweather.model;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月17日 下午3:48:22    类说明  
 *
 */
public class CityWarnBean {
	
	private String disaster;// "大雾",
	private String level;// "黄色",
	private String city;// "武汉",
	private String content;//警告内容
	private String time;// "2014-11-20 09:50:00.0",
	private String name;// "木兰古门风景区"

	public String getDisaster() {
		return disaster;
	}

	public void setDisaster(String disaster) {
		this.disaster = disaster;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
