package com.casey.hbweather.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  @author kermit  E-mail: kermitcasey@163.com  
 *  @version 创建时间：2014年12月23日 下午2:46:55    类说明  
 *
 */
public class SceneryBean implements Serializable {

	private String id;// "196",
	private String sceneryName;// "武汉-东湖落雁景区",
	private int sceneryPrice;// "0.0",
	private String regionId;// null,
	private String sceneryLevel;// "AAAA",
	private String sceneryNatrue;// null,
	private String sceneryHistory;// "自然生态园总面积345亩，由四个伸向水中的半岛组成，该园植被茂盛、风动林涛、港汊交错、水鸟众多，东湖风景区得天独厚的自然风光造就了它秀美的生态环境和景观，同时园内还集中修建了一批体现楚地民俗文化历史的雕塑和建筑，使您在欣赏旖旎景色回归自然的同时走进历史长廊，领略楚地民俗文化的经典。",
	private String sceneryClimate;// null,
	private double scenery_longitude;// "114.44137",
	private double scenery_latitude;// "30.5625",
	private String traffic;// null,
	private String address;// "武汉市东湖景区关中咀码头上岸",
	private String shortIntro;// "东湖落雁景区是国家4A级东湖风景名胜区的重要组成部分，它北依白马景区，南与磨山景区隔湖相望，东临青王公路，规划用地总面积为10.24平方公里，其中陆地5.92平方公里，水域4.32平方公里。建有清河古桥、鹊桥相会、雁洲索桥、团湖古渡、赵氏花园、古树奇观、雁栖坪沙、芦洲落雁等八大景观。",
	private Index index;
	private Weather weather;
	private String image;// http://219.140.167.87:8000/image/jingqu/196.jpg",
	private ArrayList<Forecast> forecastArray;

	public class Index implements Serializable {
		private String tripIndex;// 不适宜旅游，减少外出",
		private String humanIndex;// 不舒适，注意防寒保暖/防暑降温",
		private String ultravioletIndex;// 紫外线强度弱，可以适当采取一些防护措施，如：涂擦防护霜等",
		private String clothesIndex;// 严冬装：适宜穿着羽绒服、戴手套等",
		private String coldIndex;// 不易感冒，天气平稳或天气晴好，感冒病人偏少"

		public String getTripIndex() {
			return tripIndex;
		}

		public void setTripIndex(String tripIndex) {
			this.tripIndex = tripIndex;
		}

		public String getHumanIndex() {
			return humanIndex;
		}

		public void setHumanIndex(String humanIndex) {
			this.humanIndex = humanIndex;
		}

		public String getUltravioletIndex() {
			return ultravioletIndex;
		}

		public void setUltravioletIndex(String ultravioletIndex) {
			this.ultravioletIndex = ultravioletIndex;
		}

		public String getClothesIndex() {
			return clothesIndex;
		}

		public void setClothesIndex(String clothesIndex) {
			this.clothesIndex = clothesIndex;
		}

		public String getColdIndex() {
			return coldIndex;
		}

		public void setColdIndex(String coldIndex) {
			this.coldIndex = coldIndex;
		}

	}

	public class Weather implements Serializable {
		private int currTemp;// 0,
		private String currHumidity;// 2,
		private String currWindPo;// 0,
		private String currWindDe;// 0,
		private String cuurTime;// null,
		private String climate_exp;// 2,
		private int highTemp;// 12,
		private int lowTemp;// 2,
		private String weatherName;// "阴"

		public int getCurrTemp() {
			return currTemp;
		}

		public void setCurrTemp(int currTemp) {
			this.currTemp = currTemp;
		}

		public String getCurrHumidity() {
			return currHumidity;
		}

		public void setCurrHumidity(String currHumidity) {
			this.currHumidity = currHumidity;
		}

		public String getCurrWindPo() {
			return currWindPo;
		}

		public void setCurrWindPo(String currWindPo) {
			this.currWindPo = currWindPo;
		}

		public String getCurrWindDe() {
			return currWindDe;
		}

		public void setCurrWindDe(String currWindDe) {
			this.currWindDe = currWindDe;
		}

		public String getCuurTime() {
			return cuurTime;
		}

		public void setCuurTime(String cuurTime) {
			this.cuurTime = cuurTime;
		}

		public String getClimate_exp() {
			return climate_exp;
		}

		public void setClimate_exp(String climate_exp) {
			this.climate_exp = climate_exp;
		}

		public int getHighTemp() {
			return highTemp;
		}

		public void setHighTemp(int highTemp) {
			this.highTemp = highTemp;
		}

		public int getLowTemp() {
			return lowTemp;
		}

		public void setLowTemp(int lowTemp) {
			this.lowTemp = lowTemp;
		}

		public String getWeatherName() {
			return weatherName;
		}

		public void setWeatherName(String weatherName) {
			this.weatherName = weatherName;
		}

	}

	public class Forecast implements Serializable {
		private String day;// "2014-12-23",
		private String jingqu;// "196",
		private Index index;
		private Weather weather;

		public class Index implements Serializable {
			private String tripIndex;// "不适宜旅游，减少外出",
			private String humanIndex;// "不舒适，注意防寒保暖/防暑降温",
			private String ultravioletIndex;// "紫外线强度弱，可以适当采取一些防护措施，如：涂擦防护霜等",
			private String clothesIndex;// "严冬装：适宜穿着羽绒服、戴手套等",
			private String coldIndex;// "不易感冒，天气平稳或天气晴好，感冒病人偏少"

			public String getTripIndex() {
				return tripIndex;
			}

			public void setTripIndex(String tripIndex) {
				this.tripIndex = tripIndex;
			}

			public String getHumanIndex() {
				return humanIndex;
			}

			public void setHumanIndex(String humanIndex) {
				this.humanIndex = humanIndex;
			}

			public String getUltravioletIndex() {
				return ultravioletIndex;
			}

			public void setUltravioletIndex(String ultravioletIndex) {
				this.ultravioletIndex = ultravioletIndex;
			}

			public String getClothesIndex() {
				return clothesIndex;
			}

			public void setClothesIndex(String clothesIndex) {
				this.clothesIndex = clothesIndex;
			}

			public String getColdIndex() {
				return coldIndex;
			}

			public void setColdIndex(String coldIndex) {
				this.coldIndex = coldIndex;
			}

		}

		public class Weather implements Serializable {
			private String currTemp;// 0,
			private String currHumidity;// 2,
			private String currWindPo;// 0,
			private String currWindDe;// 0,
			private String cuurTime;// null,
			private String climate_exp;// 2,
			private String highTemp;// 12,
			private String lowTemp;// 2,
			private String weatherName;// "阴"

			public String getCurrTemp() {
				return currTemp;
			}

			public void setCurrTemp(String currTemp) {
				this.currTemp = currTemp;
			}

			public String getCurrHumidity() {
				return currHumidity;
			}

			public void setCurrHumidity(String currHumidity) {
				this.currHumidity = currHumidity;
			}

			public String getCurrWindPo() {
				return currWindPo;
			}

			public void setCurrWindPo(String currWindPo) {
				this.currWindPo = currWindPo;
			}

			public String getCurrWindDe() {
				return currWindDe;
			}

			public void setCurrWindDe(String currWindDe) {
				this.currWindDe = currWindDe;
			}

			public String getCuurTime() {
				return cuurTime;
			}

			public void setCuurTime(String cuurTime) {
				this.cuurTime = cuurTime;
			}

			public String getClimate_exp() {
				return climate_exp;
			}

			public void setClimate_exp(String climate_exp) {
				this.climate_exp = climate_exp;
			}

			public String getHighTemp() {
				return highTemp;
			}

			public void setHighTemp(String highTemp) {
				this.highTemp = highTemp;
			}

			public String getLowTemp() {
				return lowTemp;
			}

			public void setLowTemp(String lowTemp) {
				this.lowTemp = lowTemp;
			}

			public String getWeatherName() {
				return weatherName;
			}

			public void setWeatherName(String weatherName) {
				this.weatherName = weatherName;
			}

		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getJingqu() {
			return jingqu;
		}

		public void setJingqu(String jingqu) {
			this.jingqu = jingqu;
		}

		public Index getIndex() {
			return index;
		}

		public void setIndex(Index index) {
			this.index = index;
		}

		public Weather getWeather() {
			return weather;
		}

		public void setWeather(Weather weather) {
			this.weather = weather;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSceneryName() {
		return sceneryName;
	}

	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}

	public int getSceneryPrice() {
		return sceneryPrice;
	}

	public void setSceneryPrice(int sceneryPrice) {
		this.sceneryPrice = sceneryPrice;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSceneryLevel() {
		return sceneryLevel;
	}

	public void setSceneryLevel(String sceneryLevel) {
		this.sceneryLevel = sceneryLevel;
	}

	public String getSceneryNatrue() {
		return sceneryNatrue;
	}

	public void setSceneryNatrue(String sceneryNatrue) {
		this.sceneryNatrue = sceneryNatrue;
	}

	public String getSceneryHistory() {
		return sceneryHistory;
	}

	public void setSceneryHistory(String sceneryHistory) {
		this.sceneryHistory = sceneryHistory;
	}

	public String getSceneryClimate() {
		return sceneryClimate;
	}

	public void setSceneryClimate(String sceneryClimate) {
		this.sceneryClimate = sceneryClimate;
	}

	public double getScenery_longitude() {
		return scenery_longitude;
	}

	public void setScenery_longitude(double scenery_longitude) {
		this.scenery_longitude = scenery_longitude;
	}

	public double getScenery_latitude() {
		return scenery_latitude;
	}

	public void setScenery_latitude(double scenery_latitude) {
		this.scenery_latitude = scenery_latitude;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShortIntro() {
		return shortIntro;
	}

	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList<Forecast> getForecastArray() {
		return forecastArray;
	}

	public void setForecastArray(ArrayList<Forecast> forecastArray) {
		this.forecastArray = forecastArray;
	}

}
