package com.kids.test11.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class YoutubeV2 {

	public static boolean DEBUG = true;

	public static int ping(String url) throws MalformedURLException,
			IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url)
				.openConnection();
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);
		connection.setRequestMethod("HEAD");
		connection.disconnect();
		return connection.getResponseCode();
	}

	public interface VideoFetcherDelegate {

		public void onStart();

		public void onError(Throwable error);

		public void onFinish(String link);

	}

	public static class VideoNotFoundException extends Exception {

		private static final long serialVersionUID = -5004732277507208673L;

		public VideoNotFoundException(final String argMessage) {
			super(argMessage);
		}

	}

	public static class NoNetworkException extends Exception {

		private static final long serialVersionUID = 4236773993973647078L;

		public NoNetworkException(final String argMessage) {
			super(argMessage);
		}

	}

	public static class NetworkException extends Exception {

		private static final long serialVersionUID = -7608922151747002262L;

		public NetworkException(final String argMessage) {
			super(argMessage);
		}

		public NetworkException(final String argMessage, Throwable error) {
			super(argMessage, error);
		}

	}

	public static class VideoFetcherMobile extends Thread {

		private String videoId;
		private VideoFetcherDelegate delegate;

		public VideoFetcherMobile(String videoId) {
			this.videoId = videoId;

			if (videoId == null) {
				throw new NullPointerException("Video id cannot be null");
			} else if (videoId.trim().length() == 0) {
				throw new IllegalArgumentException("Video id cannot be empty");
			} else {
				// passed
			}
		}

		public String getAPIUrl() {
			return new StringBuilder(
					"https://gdata.youtube.com/feeds/api/videos/")
					.append(this.videoId).append("?v=2&alt=json").toString();
		}

		public void setDelegate(VideoFetcherDelegate delegate) {
			this.delegate = delegate;
		}

		public boolean checkInternetConnected(VideoFetcherDelegate delegate) {
			try {
				if (ping("http://google.com.vn") != 200) {
					delegate.onError(new NoNetworkException(
							"Network is not available"));
					return false;
				}
			} catch (Exception e) {
				if (DEBUG)
					e.printStackTrace();
				delegate.onError(new NoNetworkException(
						"Network is not available"));
				return false;
			}
			return true;
		}

		@Override
		public void run() {
			if (delegate == null) {
				throw new NullPointerException(
						"Video fetcher must be set delegate");
			}

			delegate.onStart();
			if (!checkInternetConnected(delegate)) {
				return;
			}

			try {
				URLConnection feedUrl = new URL(getAPIUrl()).openConnection();
				InputStream in = feedUrl.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				String content = sb.toString();
				JSONObject api = new JSONObject(content);
				JSONObject entry = api.getJSONObject("entry");
				JSONObject media = entry.getJSONObject("media$group");
				JSONArray array = media.getJSONArray("media$content");
				int leng = array.length();
				boolean flag = false;
				for (int i = 0; i < leng; i++) {
					JSONObject item = array.getJSONObject(i);
					int format = item.getInt("yt$format");
					if (format == 6) {
						delegate.onFinish(item.getString("url"));
						flag = true;
						break;
					}
				}
				if (!flag) {
					for (int i = 0; i < leng; i++) {
						JSONObject item = array.getJSONObject(i);
						int format = item.getInt("yt$format");
						if (format == 1) {
							delegate.onFinish(item.getString("url"));
							flag = true;
							break;
						}
					}
				}

				if (!flag) {
					delegate.onError(new NetworkException(
							"Not found content link"));
					return;
				}

			} catch (MalformedURLException e) {
				if (DEBUG)
					e.printStackTrace();
				delegate.onError(new NetworkException("URL is Malformed"
						+ getAPIUrl()));
				return;
			} catch (IOException e) {
				if (DEBUG)
					e.printStackTrace();
				delegate.onError(new NetworkException("IOExeption"
						+ e.getMessage()));
				return;
			} catch (JSONException e) {
				if (DEBUG)
					e.printStackTrace();
				delegate.onError(new NetworkException("Data parser error: "
						+ getAPIUrl()));
				e.printStackTrace();
			}

		}

	}

	public static class VideoFetcherMobileV2 extends Thread {

		private String videoId;
		private VideoFetcherDelegate delegate;
		private Context context;

		public VideoFetcherMobileV2(String videoId , Context context) {
			this.videoId = videoId;
			this.context = context;
			if (videoId == null) {
				throw new NullPointerException("Video id cannot be null");
			} else if (videoId.trim().length() == 0) {
				throw new IllegalArgumentException("Video id cannot be empty");
			} else {
				// passed
			}
		}

		public void setDelegate(VideoFetcherDelegate delegate) {
			this.delegate = delegate;
		}

		public boolean checkInternetConnected(VideoFetcherDelegate delegate) {

			ConnectivityManager conMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo i = conMgr.getActiveNetworkInfo();
			if (i == null)
			{
				delegate.onError(new NoNetworkException(
				"Network is not available"));
				return false;
			}
			else if (!i.isConnected())
			{
				delegate.onError(new NoNetworkException(
						"Network is not available"));
				return false;
			}
			else if (!i.isAvailable())
			{
				delegate.onError(new NoNetworkException(
						"Network is not available"));
				return false;
			}else
			return true;
		
//			try {
//				if (ping("http://google.com.vn") != 200) {
//					delegate.onError(new NoNetworkException(
//							"Network is not available"));
//					return false;
//				}
//			} catch (Exception e) {
//				if (DEBUG)
//					e.printStackTrace();
//				delegate.onError(new NoNetworkException(
//						"Network is not available"));
//				return false;
//			}
//			return true;
		}

		@Override
		public void run() {
			if (delegate == null) {
				throw new NullPointerException(
						"Video fetcher must be set delegate");
			}

			delegate.onStart();
			if (!checkInternetConnected(delegate)) {
				return;
			}

			try {
				Log.d(getClass().getSimpleName(), "On start get youtube links");
				String ret = getYoutubeLink(this.videoId);
				Log.d(getClass().getSimpleName(),
						"On start get youtube links done");
				if (ret.indexOf("fmt_stream_map") > 0) {
					ret = ret.substring(ret.indexOf("fmt_stream_map"));
					Log.d("tag", ret);
					if (ret.indexOf("{") > 0) {
						ret = ret.substring(ret.indexOf("{"));
					}
					if (ret.indexOf("]") > 0) {
						ret = ret.substring(0, ret.indexOf("]"));
					}
					if (ret.indexOf("http") > 0) {
						ret = ret.substring(ret.indexOf("http"));
						ret = ret.substring(0, ret.indexOf(",") - 2);
					}
					delegate.onFinish(cleanLink(ret));
				} else {
					delegate.onError(new NetworkException("Video: "
							+ this.videoId
							+ " is not connect to server for check video exist"));
				}
			} catch (Exception e) {
				if (DEBUG)
					e.printStackTrace();
				delegate.onError(new NetworkException("Video: " + this.videoId
						+ " is not connect to server"));
			}
		}

		private String cleanLink(String link) {
			String ret = backlashReplace(link);
			return ret.replaceAll("u0026", "&");
		}

		/**
		 * Backlash replace.
		 * 
		 * @param myStr
		 *            the my str
		 * @return the string
		 */
		private String backlashReplace(String myStr) {
			final StringBuilder result = new StringBuilder();
			final StringCharacterIterator iterator = new StringCharacterIterator(
					myStr);
			char character = iterator.current();
			while (character != CharacterIterator.DONE) {

				if (character == '\\') {
					result.append("");
				} else {
					result.append(character);
				}

				character = iterator.next();
			}
			return result.toString();

		}

		/**
		 * Gets the youtube link.
		 * 
		 * @param v
		 *            the v
		 * @return the youtube link
		 * @throws NetworkException
		 */
		public String getYoutubeLink(String v) throws VideoNotFoundException,
				NetworkException {
			String ret = "";
			StringBuilder stringBuilder = new StringBuilder("http://")
					.append("m.").append("youtube.com/watch?v=").append(v);
			try {
				URLConnection feedUrl = new URL(stringBuilder.toString())
						.openConnection();
				feedUrl.addRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
				InputStream stream = feedUrl.getInputStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				byte[] bufferData = new byte[2048];
				int dataLength = bufferData.length;
				int bytesReadedForBuffer;
				try {
					while ((bytesReadedForBuffer = stream.read(bufferData, 0,
							dataLength)) != -1) {
						buffer.write(bufferData, 0, bytesReadedForBuffer);
					}
					buffer.flush();
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ret = buffer.toString();
			} catch (Exception e) {
				throw new NetworkException("Error in connect to: "
						+ stringBuilder.toString() + ": " + e.getMessage());
			}
			return ret;
		}
	}

	public static class VideoFetcherMobileV3 extends Thread {

		private int MAX_TIME_RETRY = 5;
		private int countRetry = 0;

		private String videoId;
		private VideoFetcherDelegate delegate;

		public VideoFetcherMobileV3(String videoId) {
			this.videoId = videoId;

			if (videoId == null) {
				throw new NullPointerException("Video id cannot be null");
			} else if (videoId.trim().length() == 0) {
				throw new IllegalArgumentException("Video id cannot be empty");
			} else {
				// passed
			}
		}

		public void setMaxRetry(int times) {
			this.MAX_TIME_RETRY = times < 0 ? 0 : times;
		}

		public void setDelegate(VideoFetcherDelegate delegate) {
			this.delegate = delegate;
		}

		public void retry(Throwable error) {
			if (this.countRetry <= MAX_TIME_RETRY) {
				this.countRetry++;
				this.run();
			} else {
				if (DEBUG)
					error.printStackTrace();
				delegate.onError(error);
			}
		}

		public boolean checkInternetConnected(VideoFetcherDelegate delegate) {
			try {
				if (ping("http://google.com.vn") != 200) {
					retry(new NoNetworkException("Network is not available"));
					return false;
				}
			} catch (Exception e) {
				if (DEBUG)
					e.printStackTrace();
				retry(new NoNetworkException("Network is not available"));
				return false;
			}
			return true;
		}

		@Override
		public void run() {
			if (delegate == null) {
				throw new NullPointerException(
						"Video fetcher must be set delegate");
			}

			delegate.onStart();
			if (!checkInternetConnected(delegate)) {
				return;
			}

			try {
				Log.d(getClass().getSimpleName(), "On start get youtube links");
				String ret = getYoutubeLink(this.videoId);
				Log.d(getClass().getSimpleName(),
						"On start get youtube links done");

				if (ret.indexOf("fmt_stream_map") > 0) {
					ret = ret.substring(ret.indexOf("fmt_stream_map"));
					if (ret.indexOf("[") > 0) {
						ret = ret.substring(ret.indexOf("["));
					}
					if (ret.indexOf("]") > 0) {
						ret = ret.substring(0, ret.indexOf("]") + 1);
					}
					JSONArray array = new JSONArray(cleanLink(ret));
					delegate.onFinish(array.getJSONObject(0).getString("url"));
				} else {
					retry(new NetworkException("Video: " + this.videoId
							+ " is not connect to server for check video exist"));
				}
			} catch (Exception e) {
				retry(new NetworkException("Video: " + this.videoId
						+ " is not connect to server", e));
			}
		}

		private String cleanLink(String link) {
			String ret = backlashReplace(link);
			ret = ret.replace("codecs=\"", "codecs=\\\"");
			ret = ret.replace("\"\"", "\\\"\"");
			return ret.replaceAll("u0026", "&");
		}

		/**
		 * Backlash replace.
		 * 
		 * @param myStr
		 *            the my str
		 * @return the string
		 */
		private String backlashReplace(String myStr) {
			final StringBuilder result = new StringBuilder();
			final StringCharacterIterator iterator = new StringCharacterIterator(
					myStr);
			char character = iterator.current();
			while (character != CharacterIterator.DONE) {

				if (character == '\\') {
					result.append("");
				} else {
					result.append(character);
				}

				character = iterator.next();
			}
			return result.toString();

		}

		/**
		 * Gets the youtube link.
		 * 
		 * @param v
		 *            the v
		 * @return the youtube link
		 * @throws NetworkException
		 */
		public String getYoutubeLink(String v) throws VideoNotFoundException,
				NetworkException {
			String ret = "";
			StringBuilder stringBuilder = new StringBuilder("http://")
					.append("m.").append("youtube.com/watch?v=").append(v);

			try {
				if (ping(stringBuilder.toString()) != 200) {
					throw new VideoNotFoundException("Video :" + this.videoId
							+ " not found");
				}
				URLConnection feedUrl = new URL(stringBuilder.toString())
						.openConnection();
				feedUrl.addRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
				InputStream stream = feedUrl.getInputStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				byte[] bufferData = new byte[2048];
				int dataLength = bufferData.length;
				int bytesReadedForBuffer;
				try {
					while ((bytesReadedForBuffer = stream.read(bufferData, 0,
							dataLength)) != -1) {
						buffer.write(bufferData, 0, bytesReadedForBuffer);
					}
					buffer.flush();
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ret = buffer.toString();
			} catch (Exception e) {
				throw new NetworkException("Error in connect to: "
						+ stringBuilder.toString() + ": " + e.getMessage());
			}
			return ret;
		}
	}
}
