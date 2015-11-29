package com.eurekakids.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.eurekakids.com.eurekakids.db.DatabaseHandler;
import com.eurekakids.db.datamodel.District;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by paln on 28/11/2015.
 */
public class Httphandler {
	Context context;
	private String SERVER_BASE_URL = "http://192.168.117.37:8080";
	private final String GET_DISTRICT = "/district";
    private final String GET_BLOCK = "/block";
    private final String GET_VILLAGE = "/village";
    private final String GET_CENTRE = "/centre";
	private String TAG = "Http error";
	public Httphandler(Context context){
		this.context = context;
	}

	public void getAllDistricts(){
		final String url = SERVER_BASE_URL + GET_DISTRICT;
		new AsyncHttpTask().execute(url, GET_DISTRICT);
	}

    public void getAllBlocks(){
        final String url = SERVER_BASE_URL + GET_BLOCK;
        new AsyncHttpTask().execute(url, GET_BLOCK);
    }

    public void getAllVillages(){
        final String url = SERVER_BASE_URL + GET_VILLAGE;
        new AsyncHttpTask().execute(url, GET_VILLAGE);
    }

    public void getAllCentres(){
        final String url = SERVER_BASE_URL + GET_CENTRE;
        new AsyncHttpTask().execute(url, GET_CENTRE);
    }

	public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

		JSONObject jsonResponse = null;

		@Override
		protected Integer doInBackground(String... params) {
			InputStream inputStream = null;
			HttpURLConnection urlConnection = null;
			Integer result = 0;
			try {
                /* forming th java.net.URL object */
				URL url = new URL(params[0]);
				String type = params[1];
				urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
				urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
				urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
				urlConnection.setRequestMethod("GET");
				int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
				if (statusCode ==  200) {
					inputStream = new BufferedInputStream(urlConnection.getInputStream());
					String response = convertInputStreamToString(inputStream);
//					parseResult(response);
					handleResponse(response, type);
					result = 1; // Successful
				}else{
					result = 0;
				}
			} catch (Exception e) {

				Log.d(TAG, e.getLocalizedMessage());
			}
			return result; //"Failed to fetch data!";
		}

		@Override
		protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
			if(result == 1){
//				arrayAdapter = new ArrayAdapter(MyActivity.this, android.R.layout.simple_list_item_1, blogTitles);
//				listView.setAdapter(arrayAdapter);
				//handleResponse()
			}else{
				Log.e(TAG, "Failed to fetch data!");
			}
		}
	}

	private void handleResponse(String response, String type){
		try{
			JSONArray jsonResponse = new JSONArray(response);
			switch (type){
				case GET_DISTRICT:{
					DatabaseHandler db = new DatabaseHandler(context);
					db.addDistricts(jsonResponse);
					break;
				}

                case GET_BLOCK:{
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.addBlocks(jsonResponse);
                    break;
                }

                case GET_VILLAGE:{
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.addVillages(jsonResponse);
                    break;
                }

                case GET_CENTRE:{
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.addCentres(jsonResponse);
                    break;
                }
			}
		}catch (JSONException e){
			e.printStackTrace();
		}
	}

	private String convertInputStreamToString(InputStream inputStream) throws IOException {

		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

		String line = "";
		String result = "";

		while((line = bufferedReader.readLine()) != null){
			result += line;
		}

            /* Close Stream */
		if(null!=inputStream){
			inputStream.close();
		}

		return result;
	}

}
