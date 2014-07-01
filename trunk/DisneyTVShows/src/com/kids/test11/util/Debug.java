package com.kids.test11.util;

import java.text.DecimalFormat;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.Log;

/**
 * The Logger Class for Debug.
 */
public class Debug {

	/** The debug. */
	public static boolean DEBUG = true;
	/** The counter map. */
	private static HashMap<String, Long> timerMap = new HashMap<String, Long>();
	public static String TIME_DEBUG_PRE_FIX = "TIME=>";

	/**
	 * Debug.
	 * 
	 * @param message
	 *            the message
	 */
	public static void debug(String message) {
		if (DEBUG)
			Log.d("Debuger", message);
	}

	/**
	 * Debug.
	 * 
	 * @param message
	 *            the message
	 * @param error
	 *            the error
	 */
	public static void debug(String message, Throwable error) {
		if (DEBUG)
			Log.d("Debuger", message, error);
	}

	/**
	 * Error.
	 * 
	 * @param message
	 *            the message
	 */
	public static void error(String message) {
		if (DEBUG)
			Log.e("Debuger", message);
	}

	/**
	 * Error.
	 * 
	 * @param message
	 *            the message
	 * @param error
	 *            the error
	 */
	public static void error(String message, Throwable error) {
		if (DEBUG)
			Log.e("Debuger", message, error);
	}

	/**
	 * Info.
	 * 
	 * @param message
	 *            the message
	 */
	public static void info(String message) {
		if (DEBUG)
			Log.i("Debuger", message);
	}

	public static void verbose(String message) {
		if (DEBUG)
			Log.v("Debuger", message);
	}

	public static String getStackTraceString(int depth) {
		StackTraceElement ele = Thread.currentThread().getStackTrace()[depth];
		String fullClassName = ele.getClassName();
		String methodName = ele.getMethodName();
		int lineNumber = ele.getLineNumber();
		return new StringBuilder(fullClassName).append(".").append(methodName)
				.append(":").append(lineNumber).append(" - ").toString();
	}

	/**
	 * Info.
	 * 
	 * @param message
	 *            the message
	 * @param error
	 *            the error
	 */
	public static void info(String message, Throwable error) {
		if (DEBUG)
			Log.i("Debuger", message, error);
	}

	@SuppressLint("UseValueOf")
	public static void logHeap(String location) {
		if (Debug.DEBUG) {
			Double allocated = new Double(
					android.os.Debug.getNativeHeapAllocatedSize())
					/ new Double((1048576));
			Double available = new Double(android.os.Debug.getNativeHeapSize()) / 1048576.0;
			Double free = new Double(android.os.Debug.getNativeHeapFreeSize()) / 1048576.0;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);

			Debug.debug("=============== LOG HEAP :" + location
					+ " =================");
			Debug.debug("LOG HEAP in:" + getStackTraceString(4));
			Debug.debug("LOG HEAP heap native: allocated "
					+ df.format(allocated) + "MB of " + df.format(available)
					+ "MB (" + df.format(free) + "MB free)");
			Debug.debug("LOG HEAP memory: allocated: "
					+ df.format(new Double(
							Runtime.getRuntime().totalMemory() / 1048576))
					+ "MB of "
					+ df.format(new Double(
							Runtime.getRuntime().maxMemory() / 1048576))
					+ "MB ("
					+ df.format(new Double(
							Runtime.getRuntime().freeMemory() / 1048576))
					+ "MB free)");
		}
	}

	public synchronized static void startTimeAnalyze(String key) {
		if (DEBUG)
			timerMap.put(key, System.currentTimeMillis());
	}

	/**
	 * Stop counter.
	 * 
	 * @param counter
	 *            the counter
	 * @return the long
	 */
	public synchronized static void stopTimeAnalyze(long startTime, String msg) {
		if (DEBUG)
			Debug.verbose(TIME_DEBUG_PRE_FIX + " " + msg + ": "
					+ (SystemClock.currentThreadTimeMillis() - startTime));
	}

	/**
	 * Stop counter.
	 * 
	 * @param counterName
	 *            the counter name
	 * @return the long
	 */
	public synchronized static void stopTimeAnalyze(String key, String msg) {
		if (timerMap.containsKey(key) && DEBUG)
			Debug.verbose(TIME_DEBUG_PRE_FIX + " " + msg + ": "
					+ (System.currentTimeMillis() - timerMap.get(key)));
	}

}
