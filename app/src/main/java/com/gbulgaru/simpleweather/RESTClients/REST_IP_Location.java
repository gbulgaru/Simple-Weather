package com.gbulgaru.simpleweather.RESTClients;

import com.gbulgaru.simpleweather.WeatherData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class REST_IP_Location extends Thread {
	private final WeatherData weatherData;

	public REST_IP_Location(WeatherData weatherData) {
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