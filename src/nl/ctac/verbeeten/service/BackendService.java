package nl.ctac.verbeeten.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;

/**
 * Service for doing backend requests.
 * 
 * At this moment only a http GET request is supported. Basic authentication is
 * possible.
 * 
 * @author sstar
 * 
 */
public class BackendService {

	// TODO temp
	public static Context context;

	/** Request the response in JSON format. */
	public static final String JSON_FORMAT = "application/json";

	/** Request the response in PLIST format as XML. */
	public static final String DEFAULT_FORMAT = "";

	/** Response code (error code). */
	private int responseCode;

	/** Response of request to backend. */
	private String response;

	/** Tag for logging. */
	private static final String TAG = BackendService.class.toString();

	/** Connection timeout of 15 seconds. */
	private static final int TIMEOUT = 15000;

	/**
	 * Execute the given URL.
	 * 
	 * @param context context
	 * 
	 * @param url
	 *            url to backend service
	 * @param method
	 *            http method
	 * @param data
	 *            http body data
	 * @param barCode
	 *            barcode
	 * @param format
	 *            result format for the response
	 * @throws BackendServiceException
	 *             error getting data from backend
	 */
	public void execute(final String url, final String method,
			final String data, final String barCode, final String format)
			throws BackendServiceException {

		// Check preconditions
		assert (url != null) : "url not filled";
		assert (format != null) : "format not filled";

		exec(url, method, data, barCode, format);
	}

	private void exec(String endpoint, String method, String data,
			String barCode, String format) throws BackendServiceException {

		HttpURLConnection conn = null;
		BufferedReader rd = null;
		String line;
		StringBuilder result = new StringBuilder("");
		try {
			
			DefaultHttpClient client = new MyHttpClient(context);
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
		    HttpConnectionParams.setSoTimeout(params, TIMEOUT);
		    
			HttpRequestBase httpCall = null;
			if (method == null || "".equals(method)) {
				// Default a http get will be done
				httpCall = new HttpGet(endpoint);
			} else {
				if (HttpPut.METHOD_NAME.equals(method)) {
					httpCall = new HttpPut(endpoint);
				} else if(HttpDelete.METHOD_NAME.equals(method)) {
					httpCall = new HttpDelete(endpoint);
				} else if(HttpPost.METHOD_NAME.equals(method)) {
					// TODO post not used yet?!
					httpCall = new HttpPost(endpoint);
				}
			}
			if (httpCall != null) {
				httpCall.setHeader("barcode", barCode);
			} else {
				throw new IllegalArgumentException("No http call created");
			}
			httpCall.setHeader("Content-type", format);
			httpCall.setHeader("Accept", format);
			
			HttpResponse getResponse;
			if (data != null) {
				StringEntity entity = new StringEntity(data);
				if (httpCall instanceof HttpPut) {
					((HttpPut) httpCall).setEntity(entity);
				}
				if (httpCall instanceof HttpPost) {
					((HttpPost) httpCall).setEntity(entity);
				}
			}
			getResponse = client.execute(httpCall);
			HttpEntity responseEntity = getResponse.getEntity();
			
			rd = new BufferedReader(
					new InputStreamReader(responseEntity.getContent()));
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BackendServiceException(e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BackendServiceException(e);
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
					throw new BackendServiceException(e);
				}
			}
		}
	}

	/**
	 * Get response code.
	 * 
	 * @return response code
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * Set response code.
	 * 
	 * @param responseCode
	 *            response code
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * Get response.
	 * 
	 * @return response response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Set response.
	 * 
	 * @param response
	 *            response
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	// TODO testing purpose
	public static class Log {

		public static void e(String m1, String m2) {
			System.out.println("m1 " + m1 + " m2 " + m2);
		}

		public static void e(String m1, String m2, Exception e) {
			System.out.println("m1 " + m1 + " m2 " + m2 + " e " + e);
		}
	}
}
