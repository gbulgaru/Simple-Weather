/*
 * Copyright 2024 Bulgaru George Ionut
 *
 * This file is part of Simple Weather.
 *
 * Simple Weather is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simple Weather is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Simple Weather. If not, see <http://www.gnu.org/licenses/>.
 */

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