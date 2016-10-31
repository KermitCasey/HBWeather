package com.casey.hbweather.model;

import java.io.Serializable;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月6日 下午12:54:33    类说明  
 *
 */
@SuppressWarnings("serial")
public class ForeCastBean implements Serializable {

	private String time; //"20:00-23:00"
	private String weatherCode; //"7"
	private String weatherDesc; // "小雨"
	private String windDirect; //"东"
	private String windGrade; //"1"
	private int temperature; //"26.0"
	private String highTemp; //null
	private String lowTemp; //null
	
	public String getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the weatherDesc
	 */
	public String getWeatherDesc() {
		return weatherDesc;
	}

	/**
	 * @param weatherDesc the weatherDesc to set
	 */
	public void setWeatherDesc(String weatherDesc) {
		this.weatherDesc = weatherDesc;
	}

	/**
	 * @return the windDirect
	 */
	public String getWindDirect() {
		return windDirect;
	}

	/**
	 * @param windDirect the windDirect to set
	 */
	public void setWindDirect(String windDirect) {
		this.windDirect = windDirect;
	}

	/**
	 * @return the windGrade
	 */
	public String getWindGrade() {
		return windGrade;
	}

	/**
	 * @param windGrade the windGrade to set
	 */
	public void setWindGrade(String windGrade) {
		this.windGrade = windGrade;
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the highTemp
	 */
	public String getHighTemp() {
		return highTemp;
	}

	/**
	 * @param highTemp the highTemp to set
	 */
	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}

	/**
	 * @return the lowTemp
	 */
	public String getLowTemp() {
		return lowTemp;
	}

	/**
	 * @param lowTemp the lowTemp to set
	 */
	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}

}
