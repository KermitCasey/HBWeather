package com.casey.hbweather.controller;

/**
 * @author kermit 
 * E-mail: kermitcasey@163.com  
 * @version 创建时间：2014年12月10日 下午4:53:04  
 * 类说明  
 *
 */
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.casey.hbweather.model.CityBean;
import com.casey.hbweather.utils.Constants;

import android.util.Xml;

public class PollXmlService {
	public static final List<CityBean> readXml(InputStream xml) throws Exception {
		// 通过XmlPullParserFactory获取XmlPullParser对象
		// XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
		// XmlPullParser xParser=factory.newPullParser();

		XmlPullParser xmlPullParser = Xml.newPullParser();
		// 为Pull解析器设置输入流和编码格式
		xmlPullParser.setInput(xml, "UTF-8");
		// 获取XMl读取的位置
		int eventType = xmlPullParser.getEventType();

		List<CityBean> citys = null;
		CityBean city = null;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				citys = new ArrayList<CityBean>();
				break;
			case XmlPullParser.START_TAG:
				if (xmlPullParser.getName().equals("item")) {
					city = new CityBean();
					city.setId(xmlPullParser.getAttributeValue(0));
				} else if (xmlPullParser.getName().equals("weaid")) {
					city.setWeaid(xmlPullParser.nextText());
				} else if (xmlPullParser.getName().equals("citynm")) {
					city.setCitynm(xmlPullParser.nextText());
				} else if (xmlPullParser.getName().equals("cityno")) {
					city.setCityno(xmlPullParser.nextText());
				} else if (xmlPullParser.getName().equals("cityid")) {
					city.setCityid(xmlPullParser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if (xmlPullParser.getName().equals("item")) {
					if (city!=null) {
						Constants.HUDataBase.save(city);
						citys.add(city);
						city = null;
					}
				}
				break;
			case XmlPullParser.END_DOCUMENT:
				xml.close();
			}
			eventType = xmlPullParser.next();
		}
		return citys;
	}
}
