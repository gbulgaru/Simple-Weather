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

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AlertDialog;
import com.gbulgaru.simpleweather.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ninjas_Geocoding extends Thread{
	private ArrayList<JSONObject> suggestions;
	private String query;

	public Ninjas_Geocoding(ArrayList<JSONObject> suggestions, String query) {
		super();
		this.suggestions = suggestions;
		this.query = query;
	}

	@Override
	public void run() {
		OkHttpClient client = new OkHttpClient();
		String API_KEY = BuildConfig.Ninjas_API_KEY;
		Request request = new Request.Builder()
				.url("https://api.api-ninjas.com/v1/geocoding?city=" + query).header("X-Api-Key", API_KEY)
				.build();
		try {
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();

			JSONArray jsonArray = new JSONArray(responseString);
			synchronized (this) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					suggestions.add(jsonObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}