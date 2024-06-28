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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<JSONObject> {
	private Context context;
	private int resource;

	public ResultAdapter(Context context, int resource, ArrayList<JSONObject> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(resource, parent, false);

		TextView lblCityName = row.findViewById(R.id.lblCityName);
		TextView lblCityLocation = row.findViewById(R.id.lblCityLocation);

		try {
			lblCityName.setText(getItem(position).getString("name"));
			if (getItem(position).has("state")&&getItem(position).has("country"))
				lblCityLocation.setText(getItem(position).getString("state")+", "+getItem(position).getString("country"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return row;
	}
}
