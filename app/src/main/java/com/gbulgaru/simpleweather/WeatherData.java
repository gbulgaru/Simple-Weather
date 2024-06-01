package com.gbulgaru.simpleweather;

public class WeatherData {
	private int code;
	private double tempNow;
	private double tempMin;
	private double tempMax;
	private double feelsLike;
	private String description, shortDescription;
	private int humidity,visibility;
	private double windSpeed;
	private String latitude;
	private String longitude;
	private String city;
	private int windDeg;
	private int pressure;


	public WeatherData() {
		code=0;
		tempNow=0;
		tempMin=0;
		tempMax=0;
		feelsLike=0;
		description="";
		humidity=0;
		visibility=0;
		windSpeed=0;
		windDeg =0;
		latitude="";
		longitude="";
		city="";
		pressure=0;
		shortDescription="";
	}

	public void setTempNow(double tempNow) {
		this.tempNow = tempNow;
	}

	public double getTempNow() {
		return tempNow;
	}

	public double getTempMin() {
		return tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getFeelsLike() {
		return feelsLike;
	}

	public void setFeelsLike(double feelsLike) {
		this.feelsLike = feelsLike;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getHumidity() {
		return humidity;
	}

	public int getVisibility() {
		return visibility;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public int getWindDeg() {
		return windDeg;
	}

	public String getDescription() {
		return description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setWindDeg(int windDeg) {
		this.windDeg = windDeg;
	}
}