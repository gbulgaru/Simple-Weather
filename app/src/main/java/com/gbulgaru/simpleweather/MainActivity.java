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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Checks internet connection to choose whether to show the loading dialog or not
		if (isNetworkAvailable(this)) {
			loadForecastData();
		} else {
			// Show a warning dialog and close the app if there is no Internet connection
			new MaterialAlertDialogBuilder(this)
					.setTitle(R.string.errInternetTitle)
					.setMessage(R.string.errInternetMessage)
					.setIcon(R.drawable.ic_signal_disconnected_24px)
					.setPositiveButton(R.string.close, (dialog, which) -> finish())
					.show();
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}

		Network network = connectivityManager.getActiveNetwork();
		if (network == null) {
			return false;
		}
		NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
		return networkCapabilities != null &&
				(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
						networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
						networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
	}

	private void loadForecastData() {
		// Instantiate the ForecastFragment
		ForecastFragment forecastFragment = ForecastFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragmentContainer, forecastFragment).commit();
	}
}