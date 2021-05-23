package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import config.Config;

/**
 * Logger Class: Use For Log Data To Console
 */
public class Logger {

	/**
	 * Logger Date Formatter
	 */
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Log Data To Console
	 * 
	 * @param logType Type Of Log Data
	 * @param logData Data To Log
	 */
	public static void log(String logType, String logData) {
		if (Config.ENABLE_LOGGER) {
			LocalDateTime now = LocalDateTime.now();

			System.out.println(String.format("%s [%s] %s", dateFormatter.format(now), logType, logData));
		}
	}

	/**
	 * Log Data With Type System
	 */
	public static void log(String logData) {
		log("System", logData);
	}

	/**
	 * Log Data With Type Debug
	 */
	public static void debug(String debugData) {
		log("Debug", debugData);
	}

	/**
	 * Log Data With Type Error
	 */
	public static void error(String errorData) {
		log("Error", errorData);
	}

	/**
	 * Log Data With Type Exception
	 */
	public static void exception(String exceptionData) {
		log("Exception", exceptionData);
	}

}
