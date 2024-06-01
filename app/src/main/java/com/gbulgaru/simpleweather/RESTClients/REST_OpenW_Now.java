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