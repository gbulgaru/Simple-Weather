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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gbulgaru.simpleweather.RESTClients.IPLocation;
import com.gbulgaru.simpleweather.RESTClients.OpenW_OneCall;
import com.gbulgaru.simpleweather.RESTClients.OpenW_Current;
import org.jetbrains.annotations.NotNull;
import com.bumptech.glide.Glide;

public class ForecastFragment extends Fragment {
	private static final WeatherData weatherData = new WeatherData();

	public static ForecastFragment newInstance() {
		return new ForecastFragment();
	}

	public static ForecastFragment newInstance(double latitude, double longitude) {
		weatherData.setLatitude(String.valueOf(latitude));
		weatherData.setLongitude(String.valueOf(longitude));
		return new ForecastFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_forecast, container, false);
	}

	@Override
	public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		fetchDataAndUpdateUI();
	}

	private void fetchDataAndUpdateUI() {
		if (weatherData.getLatitude().isEmpty() && weatherData.getLongitude().isEmpty()) {
			IPLocation ipLocation = new IPLocation(weatherData);
			ipLocation.start();
			try {
				ipLocation.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		OpenW_Current openW_current = new OpenW_Current(weatherData);
		openW_current.start();

		OpenW_OneCall openW_oneCall = new OpenW_OneCall(weatherData);
		openW_oneCall.start();
		try {
			openW_oneCall.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		updateForecast();
		MainActivity mainActivity = (MainActivity) getActivity();
		if (mainActivity != null) {
			mainActivity.closeLoadingAlert();
		}
	}

	private void updateForecast() {
		View view = requireView();
		TextView lblNow = view.findViewById(R.id.lblNow);
		TextView lblTempNow = view.findViewById(R.id.lblTempNow);
		TextView lblTempMax = view.findViewById(R.id.lblTempMax);
		TextView lblTempMin = view.findViewById(R.id.lblTempMin);
		TextView lblHumidity = view.findViewById(R.id.lblHumidity);
		TextView lblVisibility = view.findViewById(R.id.lblVisibility);
		TextView lblPressure = view.findViewById(R.id.lblPressure);
		TextView lblWindSpeed = view.findViewById(R.id.lblWindSpeed);
		TextView lblWindDeg = view.findViewById(R.id.lblWindDirection);

		updatePicture(weatherData.getCode());
		lblNow.setText(weatherData.getCity() + " - " + weatherData.getShortDescription());
		lblTempNow.setText(weatherData.getTempNow() + "°C");
		lblTempMax.setText(weatherData.getTempMax() + "° / ");
		lblTempMin.setText(weatherData.getTempMin() + "°");
		lblHumidity.setText(weatherData.getHumidity() + "%");
		lblVisibility.setText(weatherData.getVisibility() + " m");
		lblPressure.setText(weatherData.getPressure() + " hPa");
		lblWindSpeed.setText(weatherData.getWindSpeed() + " m/s");
		lblWindDeg.setText(weatherData.getWindDeg() + "°");
	}

	private void updatePicture(int code) {
		ImageView imgNow = requireView().findViewById(R.id.imgNow);
		imgNow.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels/3;

		if (code >= 200 && code <= 232) {
			Glide.with(this).load(R.drawable.cloud_lightning).into(imgNow);
		} else if (code >= 300 && code <= 321) {
			Glide.with(this).load(R.drawable.cloud_rain).into(imgNow);
		} else if (code >= 500 && code <= 504) {
			Glide.with(this).load(R.drawable.sun_rain_cloud).into(imgNow);
		} else if (code == 511) {
			Glide.with(this).load(R.drawable.cloud_snow).into(imgNow);
		} else if (code >= 520 && code <= 531) {
			Glide.with(this).load(R.drawable.cloud_rain).into(imgNow);
		} else if (code >= 600 && code <= 622) {
			Glide.with(this).load(R.drawable.cloud_snow).into(imgNow);
		} else if (code >= 701 && code <= 771) {
			imgNow.setImageResource(R.drawable.foggy);
		} else if (code == 781) {
			Glide.with(this).load(R.drawable.tornado).into(imgNow);
		} else if (code == 800) {
			Glide.with(this).load(R.drawable.sun).into(imgNow);
		} else if (code == 801) {
			Glide.with(this).load(R.drawable.sun_cloud).into(imgNow);
		} else if (code == 802) {
			Glide.with(this).load(R.drawable.sun_big_cloud).into(imgNow);
		} else if (code == 803 || code == 804) {
			Glide.with(this).load(R.drawable.cloud).into(imgNow);
		} else {
			imgNow.setImageResource(R.drawable.red_question_mark);
		}
	}
}