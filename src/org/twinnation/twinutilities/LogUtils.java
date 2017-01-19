package org.twinnation.twinutilities;

import org.twinnation.twinutilities.exceptions.LogNotInitializedException;

/**
 * Utility class used to easily log events
 * 
 * TODO: Add an option to select which logging level should be saved to a log
 * (ex, WARN+ would save LEVEL_WARN and LEVEL_ERROR in a file whereas
 * LEVEL_LOG and LEVEL_INFO would only be printed on the console)
 */
public final class LogUtils {
	
	private static final String LEVEL_LOG   = "LOGGING";
	private static final String LEVEL_INFO  = "INFORMA";
	private static final String LEVEL_WARN  = "WARNING";
	private static final String LEVEL_ERROR = " ERROR ";
	
	/** Maximum file size per log file generated */
	private static final int MAX_LOG_FILE_SIZE_IN_KB = 250;
	
	/** Current number of log files created */
	private static int logFileCounter = 1;
	/** The default name of the logging file */
	private static String loggingFile = "logs.txt"; 
	/** Current logging file name */
	private static String currentLoggingFile;
	/** Whether the logging utility is initialized */
	private static boolean isInitialized = false;
	/** Whether to save the logging messages in a file */
	private static boolean isSavingToFile = false;
	/** Whether to print the logging messages directly to the console */
	private static boolean isPrintingToConsole = true;
	
	
	/** Prevents instantiation of this utility class */
	private LogUtils() {}
	
	
	/**
	 * Initializes the logging utility
	 */
	public static void init() {
		isInitialized = true;
		currentLoggingFile = loggingFile;
	}
	
	
	/**
	 * Initializes the logging utility
	 * @param saveLogToFile Whether to save logs to file
	 */
	public static void init(boolean saveLogToFile) {
		isSavingToFile = saveLogToFile;
		init();
	}
	
	
	/**
	 * Initializes the logging utility
	 * @param fName Name of the file
	 * @param saveLogToFile Saves logs to file or not
	 */
	public static void init(String fName, boolean saveLogToFile) {
		loggingFile = fName;
		isSavingToFile = saveLogToFile;
		init();
	}
	
	
	/**
	 * Initializes the logging utility
	 * @param fName Name of the file
	 * @param saveLogToFile Whether to save logs to file or not
	 * @param printLogToConsole Whether to print logs to console or not
	 */
	public static void init(String fName, boolean saveLogToFile,
			boolean printLogToConsole) {
		// TODO: Throw exception if !printLogToConsole && !saveLogToFile
		isPrintingToConsole = printLogToConsole;
		init(fName, saveLogToFile);
	}
	
	
	/**
	 * Logs a message with severity level "LOG"
	 * @param message Message to log
	 */
	public static void log(String message) {
		doLog(LEVEL_LOG, message);
	}
	
	
	/**
	 * Logs a message with severity level "INFORMATION"
	 * @param message Message to log
	 */
	public static void info(String message) {
		doLog(LEVEL_INFO, message);
	}
	
	
	/**
	 * Logs a message with severity level "WARNING"
	 * @param message Message to log
	 */
	public static void warn(String message) {
		doLog(LEVEL_WARN, message);
	}
	
	
	/**
	 * Logs a message with severity level "ERROR"
	 * @param message Message to log
	 */
	public static void error(String message) {
		doLog(LEVEL_ERROR, message);
	}
	
	
	/**
	 * Logs the message with the given severity level
	 * @param level Severity level
	 * @param message Message to log
	 * @throws LogNotInitializedException If LogUtils.init hasn't been called
	 */
	private static void doLog(String level, String message) throws LogNotInitializedException {
		if (!isInitialized) {
			throw new LogNotInitializedException();
		}
		if (FileUtils.getFileSizeInKb(currentLoggingFile) > MAX_LOG_FILE_SIZE_IN_KB) {
			CompressionUtils.gzipAndDelete(currentLoggingFile); 
			currentLoggingFile = FileUtils.stripExtension(loggingFile)
					+""+ConversionUtils.zeroPad(4, ""+logFileCounter++)
					+"."+(FileUtils.getExtension(loggingFile).equals("") ?
						"txt" : FileUtils.getExtension(loggingFile));
		}
		message = DateTimeUtils.getTimestamp()+" ["+level+"] -> "+message;
		if (isPrintingToConsole) {
			System.err.println(message);
		}
		if (isSavingToFile) {
			FileUtils.writeInFile(message, currentLoggingFile, true);
		}
	}
}
