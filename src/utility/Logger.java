package utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import config.Config;

public class Logger {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void log(String logType, String logData) {
		if (Config.ENABLE_LOGGER) {
			LocalDateTime now = LocalDateTime.now();

			System.out.println(String.format("%s [%s] %s", dateFormatter.format(now), logType, logData));
		}
	}

	public static void log(String logData) {
		log("System", logData);
	}

	public static void debug(String debugData) {
		log("Debug", debugData);
	}

	public static void error(String errorData) {
		log("Error", errorData);
	}

}
