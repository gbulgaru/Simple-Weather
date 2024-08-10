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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.gbulgaru.simpleweather.RESTClients.Ninjas_Geocoding;
import com.google.android.gms.location.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.search.SearchView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
	private FusedLocationProviderClient fusedLocationClient;
	private LocationCallback locationCallback;
	private ArrayList<JSONObject> searchResults = new ArrayList<>();
	private AlertDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		showLoadingAlert();
		setupSearchComponents();

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

		// Define the location callback
		locationCallback = new LocationCallback() {
			@Override
			public void onLocationResult(@NonNull LocationResult locationResult) {
				// Get the most recent location
				Location location = locationResult.getLastLocation();
				if (location != null) {
					loadForecastData(location.getLatitude(), location.getLongitude());
					fusedLocationClient.removeLocationUpdates(locationCallback);
				} else {
					Toast.makeText(MainActivity.this, R.string.errLocationOff, Toast.LENGTH_LONG).show();
					loadForecastData();
				}
			}
		};

		// Checks internet connection to choose whether to show the loading dialog or not
		if (isNetworkAvailable(this)) {
			// Checks if the device is running Android 12 or newer
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
				if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// Show rationale dialog before requesting permission
					new MaterialAlertDialogBuilder(this)
							.setTitle(R.string.gpsRationaleTitle)
							.setMessage(R.string.gpsRationaleMessage)
							.setIcon(R.drawable.ic_security_24px).setCancelable(false)
							.setPositiveButton("Ok", (dialog, which) -> {
								dialog.dismiss();
								// Request location permission after closing the dialog
								requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
							})
							.show();
				}
			} else {
				// Check location permission
				if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// Show rationale dialog before requesting permission
					new MaterialAlertDialogBuilder(this)
							.setTitle(R.string.gpsRationaleTitle)
							.setMessage(R.string.gpsRationaleMessage)
							.setIcon(R.drawable.ic_security_24px).setCancelable(false)
							.setPositiveButton("Ok", (dialog, which) -> {
								dialog.dismiss();
								// Request location permission after closing the dialog
								requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
							})
							.show();
				}
				getLastLocation();
			}
		} else {
			// Show a warning dialog and close the app if there is no Internet connection
			new MaterialAlertDialogBuilder(this)
					.setTitle(R.string.errInternetTitle)
					.setMessage(R.string.errInternetMessage)
					.setIcon(R.drawable.ic_signal_disconnected_24px).setCancelable(false)
					.setPositiveButton(R.string.close, (dialog, which) -> finish())
					.show();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
	                                       @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Location permission granted, get location
				getLastLocation();
			} else {
				// Location permission denied, show a message
				Toast.makeText(this, R.string.errPermission, Toast.LENGTH_LONG).show();
				loadForecastData();
			}
		}
	}

	private void getLastLocation() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
			// Check if fine location permission is denied
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
		} else {
			// Check if coarse location permission is denied
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
		}
		LocationRequest locationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(10000)
				.setFastestInterval(5000);

		fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

		fusedLocationClient.getLastLocation()
				.addOnSuccessListener(this, location -> {
					if (location != null) {
						loadForecastData(location.getLatitude(), location.getLongitude());
					} else {
						Toast.makeText(MainActivity.this, R.string.errLocationOff, Toast.LENGTH_LONG).show();
						//IP location will be used if GPS is off
						loadForecastData();
					}
				})
				.addOnFailureListener(this, e -> {
					Toast.makeText(MainActivity.this, "Failed to get location", Toast.LENGTH_LONG).show();
				});
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
		ForecastFragment forecastFragment = ForecastFragment.newInstance();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragmentContainer, forecastFragment).commit();
	}

	private void loadForecastData(double latitude, double longitude) {
		ForecastFragment forecastFragment = ForecastFragment.newInstance(latitude, longitude);
		// Removes all fragments currently in the container
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for (Fragment fragment : fragments) {
			getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragmentContainer, forecastFragment).commit();
	}

	private void setupSearchComponents() {
		SearchView searchView = findViewById(R.id.searchView);
		ListView searchResultsView = findViewById(R.id.searchResultsView);
		ResultAdapter resultAdapter = new ResultAdapter(this, R.layout.search_item, searchResults);

		// Set the listener to detect the enter key
		searchView.getEditText().setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
			if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
				if (event == null || !event.isShiftPressed()) {
					// Perform search action
					String query = searchView.getEditText().getText().toString();
					// Clears the search results and notifies the adapter
					searchResults.clear();
					resultAdapter.notifyDataSetChanged();

					Ninjas_Geocoding geocodingClient = new Ninjas_Geocoding(searchResults, query);
					geocodingClient.start();

					try {
						geocodingClient.join();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}

					// Notifies the adapter that the data has changed
					resultAdapter.notifyDataSetChanged();
					searchResultsView.setAdapter(resultAdapter);

					// Shows a dialog if no results were found
					if (searchResults.isEmpty()) {
						makeMaterialAlert(R.string.errNoResultsTitle, R.string.errNoResultsMessage, R.drawable.ic_not_listed_location_24px, R.string.close);
					}
					return true;
				}
			}
			return false;
		});

		// Set the listener to detect the selection of a result
		searchResultsView.setOnItemClickListener((parent, view, position, id) -> {
			searchView.hide();
			try {
				JSONObject selectedResult = searchResults.get(position);
				loadForecastData(selectedResult.getDouble("latitude"), selectedResult.getDouble("longitude"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void makeMaterialAlert(int title, int message, int icon, int positiveMsg) {
		MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(this);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setIcon(icon);
		alert.setPositiveButton(positiveMsg, (dialog, which) -> dialog.dismiss());
		alert.show();
	}

	private void showLoadingAlert() {
		LayoutInflater inflater = getLayoutInflater();

		MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
		builder.setView(inflater.inflate(R.layout.loading_dialog, null));
		loadingDialog = builder.create();
		loadingDialog.setCancelable(false);
		loadingDialog.show();
	}

	public void closeLoadingAlert() {
		loadingDialog.dismiss();
	}
}