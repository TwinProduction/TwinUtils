package org.twinnation.twinutilities;


/**
 * Utility class used to easily log events
 * 
 * TODO: Add an option to select which logging level should be saved to a log
 * (ex, WARN+ would save LEVEL_WARN and LEVEL_ERROR in a file whereas
 * LEVEL_LOG and LEVEL_INFO would only be printed on the console)
 */
public final class LogUtils {
	
	/** String displayed at the start of all message classified as "log" */
	private static final String LEVEL_LOG   = "[LOG]  ";
	/** String displayed at the start of all message classified as "info" */
	private static final String LEVEL_INFO  = "[INFO] ";
	/** String displayed at the start of all message classified as "warn" */
	private static final String LEVEL_WARN  = "[WARN] ";
	/** String displayed at the start of all message classified as "error" */
	private static final String LEVEL_ERROR = "[ERROR]";
	
	/** Maximum file size per log file generated */
	private static final int MAX_LOG_FILE_SIZE_IN_KB = 512;
	
	/** Instance of LogUtils */
	private static LogUtils instance = null;
	
	/** Current number of log files created */
	private int logFileCounter = 1;
	/** The default name of the logging file */
	private String loggingFile = "logs.txt"; 
	/** Current logging file name */
	private String currentLoggingFile;
	/** Whether to save the logging messages in a file */
	private boolean isSavingToFile = false;
	/** Whether to print the logging messages directly to the console */
	private boolean isPrintingToConsole = true;
	
	
	/** Prevents instantiation of this utility class */
	private LogUtils() {}
	
	
	public static LogUtils getInstance() {
		if (instance == null) {
			instance = new LogUtils();
		}
		return instance;
	}
	
	
	/**
	 * Checks if LogUtils is currently saving logs in a file
	 * @return Whether LogUtils is saving the logs in a file
	 */
	public boolean isSavingToFile() {
		return isSavingToFile;
	}
	
	/**
	 * Sets whether to save the logs to the loggingFile or not.
	 * @param isSavingToFile
	 */
	public void setSavingToFile(boolean isSavingToFile) {
		this.isSavingToFile = isSavingToFile;
		if (isSavingToFile) {
			currentLoggingFile = loggingFile;
		}
	}
	
	
	/**
	 * Gets the logging file
	 * @return Name of the file to write logs in
	 */
	public String getLoggingFile() {
		return loggingFile;
	}
	
	/**
	 * Sets the logging file
	 * @param loggingFile Name of the file to write logs in
	 */
	public void setLoggingFile(String loggingFile) {
		this.loggingFile = loggingFile;
	}


	/**
	 * Checks if LogUtils is currently printing logs to console
	 * @return Whether the logs are being printed to console
	 */
	public boolean isPrintingToConsole() {
		return isPrintingToConsole;
	}


	/**
	 * Sets whether to print the logs to consol
	 */
	public void setPrintingToConsole(boolean isPrintingToConsole) {
		this.isPrintingToConsole = isPrintingToConsole;
	}


	/**
	 * Logs a message with severity level "LOG"
	 * @param message Message to log
	 */
	public void log(String message) {
		doLog(LEVEL_LOG, message);
	}
	
	
	/**
	 * Logs a message with severity level "INFORMATION"
	 * @param message Message to log
	 */
	public void info(String message) {
		doLog(LEVEL_INFO, message);
	}
	
	
	/**
	 * Logs a message with severity level "WARNING"
	 * @param message Message to log
	 */
	public void warn(String message) {
		doLog(LEVEL_WARN, message);
	}
	
	
	/**
	 * Logs a message with severity level "ERROR"
	 * @param message Message to log
	 */
	public void error(String message) {
		doLog(LEVEL_ERROR, message);
	}
	
	
	/**
	 * Logs the message with the given severity level
	 * @param level Severity level
	 * @param message Message to log
	 * @throws LogNotInitializedException If LogUtils.init hasn't been called
	 */
	private void doLog(String level, String message) {
		message = "[" + DateTimeUtils.getTimestamp() + "]"+level+" -> " + message;
		if (isSavingToFile) {
			// Maximum log file size has been reached
			if (FileUtils.getFileSizeInKb(currentLoggingFile) > MAX_LOG_FILE_SIZE_IN_KB) {
				CompressionUtils.gzipAndDelete(currentLoggingFile);
				currentLoggingFile = FileUtils.stripExtension(loggingFile) + "" 
						+ ConversionUtils.zeroPad(4, ""+logFileCounter++) + "." 
						+ (FileUtils.getExtension(loggingFile).equals("") ? "txt":FileUtils.getExtension(loggingFile));
			}
			FileUtils.writeInFile(message, currentLoggingFile, true);
		}
		if (isPrintingToConsole) {
			System.out.println(message);
		}
	}
}
