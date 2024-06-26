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

public class OpenW_OneCall extends Thread {
	private final WeatherData weatherData;

	public OpenW_OneCall(WeatherData weatherData) {
		super();
		this.weatherData = weatherData;
		updateMsSystem();
	}

	@Override
	public void run() {
		OkHttpClient client = new OkHttpClient();
		String API_KEY = BuildConfig.OpenW_API_KEY;
		Request request = new Request.Builder()
				.url("https://api.openweathermap.org/data/3.0/onecall?lat=" + weatherData.getLatitude() + "&lon=" +
						weatherData.getLongitude() + "&appid=" + API_KEY + "&units=" + weatherData.getMeasureSys() +
						"&exclude=minutely" + "&lang=" + Locale.getDefault().getLanguage())
				.build();
		try {
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			JSONObject jsonObject = new JSONObject(responseString);

			/*new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Response");
					builder.setMessage(responseString);
					builder.show();
				}
			});*/

			weatherData.setCode(jsonObject.getJSONObject("current").getJSONArray("weather").getJSONObject(0).getInt("id"));
			weatherData.setTempNow(jsonObject.getJSONObject("current").getDouble("temp"));
			weatherData.setHumidity(jsonObject.getJSONObject("current").getInt("humidity"));
			weatherData.setVisibility(jsonObject.getJSONObject("current").getInt("visibility"));
			weatherData.setPressure(jsonObject.getJSONObject("current").getInt("pressure"));
			weatherData.setWindSpeed(jsonObject.getJSONObject("current").getDouble("wind_speed"));
			weatherData.setWindDeg(jsonObject.getJSONObject("current").getInt("wind_deg"));
			weatherData.setShortDescription(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
			weatherData.setDescription(jsonObject.getJSONArray("daily").getJSONObject(0).getString("summary"));
			weatherData.setSunrise(jsonObject.getJSONObject("current").getInt("sunrise"));
			weatherData.setSunset(jsonObject.getJSONObject("current").getInt("sunset"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMsSystem() {
		try {
			// Get the current location
			String locale = Locale.getDefault().getCountry();

			// Determine the measurement system based on location
			if (locale.equals("US") || locale.equals("LR") || locale.equals("MM")) {
				weatherData.setMeasureSys("imperial");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}