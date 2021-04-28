package com.increff.commons.browser;

import java.io.File;
import java.io.IOException;
import java.security.ProviderException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractBrowser {

	private static final int WEBDRIVER_WAIT = 60;

	private WebDriver driver;
	protected JavascriptExecutor executor;
	private WebDriverWait wait;
	private String baseUrl;
	private File tempDir;

	/**
	 * Instantiate a Chrome web driver instance using the specified download directory
	 * The download directory is cleaned up/deleted before termination
	 * @param tempDir Default chrome driver download location
	 */
	public AbstractBrowser(File tempDir) throws IOException {
		this.tempDir = tempDir;
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		driver = BrowserFactory.getWebDriver(tempDir);
		executor = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, WEBDRIVER_WAIT);
		cleanup();
	}

	/**
	 * Deletes the temporary download directory used by Chrome driver
	 */
	private void cleanup() throws IOException {
		FileUtils.deleteDirectory(tempDir);
	}

	/**
	 * Return the base url being used
	 * @return Base URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Set base URL to be used in chrome browser e.g. "www.google.com"
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Navigate to specified URL on chrome driver
	 * @param url URL to visit
	 */
	public void navigate(String url) {
		driver.get(url);
	}

	/**
	 * Visit a specific path on the base URL e.g. "/search/help"
	 * @param path Path to visit
	 */
	public void navigatePath(String path) {
		navigate(getBaseUrl() + path);
	}

	public void quit() {
		driver.quit();
	}

	/**
	 * Wait for page to load on the Chrome driver
	 */
	public void waitForLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return executor.executeScript("return document.readyState").equals("complete");
			}
		};
		waitFor(pageLoadCondition);
	}

	/**
	 * Wait for the loading of a specific element and return it once loaded
	 * @param by Selector for element to be returned
	 * @return Loaded element on the Website
	 */
	public WebElement waitFor(By by) {
		waitFor(ExpectedConditions.presenceOfElementLocated(by));
		return driver.findElement(by);
	}

	/**
	 * Wait for the loading of a specific nested element and return it once loaded
	 * @param parent Parent web element to search
	 * @param child Element to be searched and returned after load
	 * @return Loaded child if located
	 */
	public WebElement waitFor(WebElement parent, By child) {
		waitFor(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, child));
		return parent.findElement(child);
	}

	/**
	 * Wait until a condition is satisfied
	 * @param condition Condition to be checked
	 */
	public void waitFor(ExpectedCondition<?> condition) {
		wait.until(condition);
	}

	/**
	 * Wait for a specified number of seconds
	 * @param seconds Seconds to wait
	 */
	public void waitFor(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Error sleeping", e);
		}
	}

	/**
	 * Automatically move the mouse over the specified element
	 * @param by The element over which to position the mouse pointer
	 */
	public void mouseOver(By by) {
		Actions builder = new Actions(driver);
		WebElement webEl = driver.findElement(by);
		builder.moveToElement(webEl).perform();
	}

	/**
	 * Automatically click on a specified element
	 * @param by The element to be clicked on
	 */
	public void click(By by) {
		WebElement webEl = driver.findElement(by);
		webEl.click();
	}

	/**
	 * Automatically select a value from a list of options (e.g. in a drop-down)
	 * @param by Element for which to select a value/option
	 * @param value Value to be selected
	 */
	public void setOption(By by, String value) {
		WebElement webEl = driver.findElement(by);
		Select se = new Select(webEl);
		se.selectByValue(value);
	}

	/**
	 * Automatically set text of a text input field
	 * @param by Text field element
	 * @param text Text to be placed as input
	 */
	public void setText(By by, String text) {
		WebElement webEl = driver.findElement(by);
		webEl.clear();
		webEl.sendKeys(text);
	}

	/**
	 * Automate the selection of a checkbox item
	 * @param by Checkbox element
	 * @param select true to select, false to de-select
	 */
	public void setCheckbox(By by, boolean select) {
		WebElement webEl = driver.findElement(by);
		// If the checkbox is already in the given state then return
		if (select == webEl.isSelected()) {
			return;
		}
		webEl.click();
	}

	/**
	 * Get a web element
	 * @param by Selector for element
	 * @return WebElement specified by "by"
	 */
	public WebElement get(By by) {
		return driver.findElement(by);
	}

	/**
	 * Switch between iFrames
	 * @param frame Frame to switch to represented by name/index/id of frame
	 */
	public void switchTo(String frame) {
		driver.switchTo().frame(frame);
	}

	/**
	 * Switch to default view on the web page
	 */
	public void switchToDefaultView() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Fetch the last downloaded file stored in the tempDir location
	 * @return Fetched file
	 */
	public File getLastDownloadedFile() throws IOException, ProviderException {
		File[] files = tempDir.listFiles();
		if (files.length != 1)
			throw new ProviderException("Something went wrong with the temp directory: Size originally should be 1");
		return files[0];
	}

}
