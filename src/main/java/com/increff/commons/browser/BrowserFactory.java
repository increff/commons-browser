package com.increff.commons.browser;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BrowserFactory {

	private static String CHROME_DRIVER = "/path-to-chromedriver";

	protected WebDriver driver;

	/**
	 * Set path where chrome-driver is stored on disk
	 * @param chromeDriverPath Path to chrome-driver as String
	 */
	public static void setChromeDriverPath(String chromeDriverPath) {
		CHROME_DRIVER = chromeDriverPath;
	}

	/**
	 * Instantiate and return a chrome web driver instance
	 * @param tempDir Default directory for downloaded files
	 * @return Chrome WebDriver object
	 */
	public static WebDriver getWebDriver(File tempDir) {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
		WebDriver webDriver = new ChromeDriver(getCapabilities(tempDir));
		return webDriver;
	}

	/**
	 * Set the options/configurations for chrome driver
	 * @param tempDir Default directory for downloaded files
	 * @return Configured ChromeOptions object
	 */
	public static ChromeOptions getCapabilities(File tempDir) {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", tempDir.getAbsolutePath());
		chromePrefs.put("download.prompt_for_download", false);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-popup-blocking");
		options.setExperimentalOption("prefs", chromePrefs);
		return options;
	}

}
