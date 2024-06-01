package com.gbulgaru.simpleweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gbulgaru.simpleweather.RESTClients.REST_IP_Location;
import com.gbulgaru.simpleweather.RESTClients.REST_OpenW_Now;

public class ForecastFragment extends Fragment {

	private final WeatherData weatherData = new WeatherData();
	private DataLoadListener dataLoadListener;

	public static ForecastFragment newInstance(DataLoadListener dataLoadListener) {
		ForecastFragment fragment = new ForecastFragment();
		fragment.dataLoadListener = dataLoadListener;
		return fragment;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_forecast, container, false);
		fetchDataAndUpdateUI(view);
		return view;
	}

	private void fetchDataAndUpdateUI(View view) {
		REST_IP_Location rest_ip_location = new REST_IP_Location(weatherData);
		rest_ip_location.start();
		try {
			rest_ip_location.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		REST_OpenW_Now rest_openWeather_now = new REST_OpenW_Now(weatherData);
		rest_openWeather_now.start();
		try {
			rest_openWeather_now.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		requireActivity().runOnUiThread(() -> {
			updateForecast(view);
			dataLoadListener.onDataLoaded();
		});
	}

	private void updateForecast(View view) {
		TextView lblNow = view.findViewById(R.id.lblNow);
		TextView lblTempNow = view.findViewById(R.id.lblTempNow);
		TextView lblTempMax = view.findViewById(R.id.lblTempMax);
		TextView lblTempMin = view.findViewById(R.id.lblTempMin);
		TextView lblHumidity = view.findViewById(R.id.lblHumidity);
		TextView lblVisibility = view.findViewById(R.id.lblVisibility);
		TextView lblPressure = view.findViewById(R.id.lblPressure);
		TextView lblWindSpeed = view.findViewById(R.id.lblWindSpeed);
		TextView lblWindDeg = view.findViewById(R.id.lblWindDirection);

		updatePicture(view, weatherData.getCode());
		lblNow.setText(weatherData.getCity() + " - " + weatherData.getShortDescription());
		lblTempNow.setText(weatherData.getTempNow() + "°C");
		lblTempMax.setText(weatherData.getTempMax() + "°/");
		lblTempMin.setText(weatherData.getTempMin() + "°");
		lblHumidity.setText(getString(R.string.lblHumidity) + weatherData.getHumidity() + "%");
		lblVisibility.setText(getString(R.string.lblVisibility) + weatherData.getVisibility() + "m");
		lblPressure.setText(getString(R.string.lblPressure) + weatherData.getPressure() + "hPa");
		lblWindSpeed.setText(getString(R.string.lblWindSpeed) + weatherData.getWindSpeed() + "m/s");
		lblWindDeg.setText(getString(R.string.lblWindDirection) + weatherData.getWindDeg() + "°");
	}

	private void updatePicture(View view, int code) {
		TextView imgNow = view.findViewById(R.id.lblIcoNow);
		if (code >= 200 && code <= 232) {
			imgNow.setText("\uD83C\uDF29️");
		} else if (code >= 300 && code <= 321) {
			imgNow.setText("🌧️");
		} else if (code >= 500 && code <= 504) {
			imgNow.setText("\uD83C\uDF26️");
		} else if (code == 511) {
			imgNow.setText("❄");
		} else if (code >= 520 && code <= 531) {
			imgNow.setText("🌧️");
		} else if (code >= 600 && code <= 622) {
			imgNow.setText("\uD83C\uDF28️");
		} else if (code >= 701 && code <= 771) {
			imgNow.setText("🌫️");
		} else if (code == 781) {
			imgNow.setText("🌪️");
		} else if (code == 800) {
			imgNow.setText("☀️");
		} else if (code == 801) {
			imgNow.setText("🌤️");
		} else if (code == 802) {
			imgNow.setText("\uD83C\uDF25️");
		} else if (code == 803 || code == 804) {
			imgNow.setText("☁️");
		} else {
			imgNow.setText("❓");
		}
	}
}