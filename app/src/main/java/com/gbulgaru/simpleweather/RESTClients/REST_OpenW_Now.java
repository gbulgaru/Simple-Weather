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

package com.gbulgaru.simpleweather.RESTClients;

import com.gbulgaru.simpleweather.BuildConfig;
import com.gbulgaru.simpleweather.WeatherData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.util.Locale;

public class REST_OpenW_Now extends Thread {
	private final WeatherData weatherData;

	public REST_OpenW_Now(WeatherData weatherData) {
		super();
		this.weatherData = weatherData;
	}

	@Override
	public void run() {
		OkHttpClient client = new OkHttpClient();
		String API_KEY = BuildConfig.OpenW_API_KEY;
		Request request = new Request.Builder()
				.url("https://api.openweathermap.org/data/2.5/weather?lat=" + weatherData.getLatitude() + "&lon=" + weatherData.getLongitude() + "&appid=" + API_KEY + "&units=metric" + "&lang=" + Locale.getDefault().getLanguage())
				.build();
		try {
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			JSONObject jsonObject = new JSONObject(responseString);

			weatherData.setCity(jsonObject.getString("name"));
			weatherData.setCode(jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id"));
			weatherData.setTempNow(jsonObject.getJSONObject("main").getDouble("temp"));
			weatherData.setTempMax(jsonObject.getJSONObject("main").getDouble("temp_max"));
			weatherData.setTempMin(jsonObject.getJSONObject("main").getDouble("temp_min"));
			weatherData.setHumidity(jsonObject.getJSONObject("main").getInt("humidity"));
			weatherData.setVisibility(jsonObject.getInt("visibility"));
			weatherData.setPressure(jsonObject.getJSONObject("main").getInt("pressure"));
			weatherData.setWindSpeed(jsonObject.getJSONObject("wind").getDouble("speed"));
			weatherData.setWindDeg(jsonObject.getJSONObject("wind").getInt("deg"));
			weatherData.setShortDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}