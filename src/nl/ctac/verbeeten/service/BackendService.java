package nl.ctac.verbeeten.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

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

	/** Request the response in JSON format. */
	public static final String JSON_FORMAT = "application/json";

	/** Request the response in PLIST format as XML. */
	public static final String DEFAULT_FORMAT = "";

	/** Request method constant GET. */
	private static final String REQUEST_METHOD_GET = "GET";

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

		// TODO needed? System.setProperty("http.keepAlive","false");

		HttpURLConnection conn = null;
		BufferedReader rd = null;
		String line;
		StringBuilder result = new StringBuilder("");
		try {
			URL url = new URL(endpoint);
			conn = (HttpURLConnection) url.openConnection();

			// TODO hardcoded barcode
			conn.addRequestProperty("barcode", barCode);

			conn.addRequestProperty("Content-type", format);
			conn.setRequestProperty("Accept", format);

			if (method == null || "".equals(method)) {
				conn.setRequestMethod(REQUEST_METHOD_GET);
			} else {
				conn.setRequestMethod(method);
			}

			// set timeout on connection...
			conn.setReadTimeout(TIMEOUT);
			conn.setConnectTimeout(TIMEOUT);

			if (data != null) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream());
				out.write(data);
				out.flush();
				out.close();
			}

			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BackendServiceException(e);
		} catch (ProtocolException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BackendServiceException(e);
		} catch (UnknownHostException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BackendServiceException(e);
		} catch (SocketTimeoutException e) {
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
