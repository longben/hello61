package com.bitbee.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	private final static String TAG = "JSONParser";  
	
	private static CookieStore cookieStore;// 定义一个Cookie来保存session

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url, HttpPost httpPost) {
			
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();

			if (cookieStore != null) {
				httpClient.setCookieStore(cookieStore);
			}
			
			//Log.d(TAG, "url is" + url);
			
			//HttpPost httpPost = new HttpPost(url);
			
			if(httpPost == null){
				httpPost = new HttpPost(url);
				httpPost.addHeader("Content-Type",
						"application/x-www-form-urlencoded; charset=\"UTF-8\"");				
			}

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			cookieStore = ((AbstractHttpClient) httpClient).getCookieStore();  

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
}
