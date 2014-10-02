package com.coolweather.app.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.coolweather.app.db.CoolWeatherDB;

import android.content.Context;

public class ContentHandler extends DefaultHandler {

	private CoolWeatherDB coolWeatherDB;
	private int autoIncrementProvince = 0;
	private int autoIncrementCity = 0;
	
	public ContentHandler(Context context) {
		coolWeatherDB = CoolWeatherDB.getInstance(context);
	}
	
	@Override
	public void startDocument() throws SAXException {
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if("province".equals(localName)) {
			autoIncrementProvince++;
			Province province = new Province();
			province.setProvinceCode(attributes.getValue(0));
			province.setProvinceName(attributes.getValue(1));
			coolWeatherDB.saveProvince(province);
		} else if("city".equals(localName)) {
			autoIncrementCity++;
			City city = new City();
			city.setCityCode(attributes.getValue(0));
			city.setCityName(attributes.getValue(1));
			city.setProvinceId(autoIncrementProvince);
			coolWeatherDB.saveCity(city);
		} else if("county".equals(localName)) {
			County county = new County();
			county.setCountyCode(attributes.getValue(0));
			county.setCountyName(attributes.getValue(1));
			county.setWeatherCode(attributes.getValue(2));
			county.setCityId(autoIncrementCity);
			coolWeatherDB.saveCounty(county);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}
	
	@Override
	public void endDocument() throws SAXException {
	}

}
