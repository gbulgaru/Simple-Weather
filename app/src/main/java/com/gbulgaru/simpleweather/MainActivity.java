package com.gbulgaru.simpleweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity implements DataLoadListener {
	private AlertDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Checks internet connection to choose whether to show the loading dialog or not
		if (isNetworkAvailable(this)) {
			showLoadingDialog();
		} else {
			// Show a warning dialog and close the app if there is no Internet connection
			new MaterialAlertDialogBuilder(this)
					.setTitle(R.string.errorInternetTitle)
					.setMessage(R.string.errorInternetMessage)
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

	private void showLoadingDialog() {
		MaterialAlertDialogBuilder loadingDialogBuilder = new MaterialAlertDialogBuilder(this);

		View dialogView = getLayoutInflater().inflate(R.layout.loading_dialog, null);
		loadingDialogBuilder.setView(dialogView);
		loadingDialogBuilder.setCancelable(false);

		loadingDialog = loadingDialogBuilder.create();
		loadingDialog.show();
		loadForecastData();
	}

	private void loadForecastData() {
		// Instantiate the ForecastFragment and pass the current Activity instance as DataLoadListener
		ForecastFragment forecastFragment = ForecastFragment.newInstance(this);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragmentContainer, forecastFragment).commit();
	}

	@Override
	public void onDataLoaded() {
		// Hide the loading dialog when the data is ready
		loadingDialog.dismiss();
	}
}