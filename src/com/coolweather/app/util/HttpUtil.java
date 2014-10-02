package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
//		if(!isNetworkAvailable()) {
//			Toast.makeText(BoxApplication.getContext(), "network is unavailable", 
//					Toast.LENGTH_SHORT).show();
//			return;
//		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection conn = null;
				try {
					URL url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setConnectTimeout(20 * 1000);
					conn.setReadTimeout(20 * 1000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
//					conn.setRequestProperty(field, newValue)
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					StringBuilder response = new StringBuilder();
					String line = null;
					while((line = reader.readLine()) != null) {
						response.append(line);
					}
					if(listener != null) {
						//回调onFinish()方法
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener != null) {
						//回调onError()方法
						listener.onError(e);
					}
				} finally {
					if(conn != null) {
						conn.disconnect();
					}
				}
			}
		}).start();
	}
	
	/*private static boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) BoxApplication.getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}*/
}
