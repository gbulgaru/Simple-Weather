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

import com.gbulgaru.simpleweather.WeatherData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class IPLocation extends Thread {
	private final WeatherData weatherData;

	public IPLocation(WeatherData weatherData) {
		super();
		this.weatherData = weatherData;
	}

	@Override
	public void run() {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://ipinfo.io/?token")
				.build();
		try {
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			JSONObject jsonObject = new JSONObject(responseString);
			weatherData.setLatitude(jsonObject.getString("loc").split(",")[0]);
			weatherData.setLongitude(jsonObject.getString("loc").split(",")[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}