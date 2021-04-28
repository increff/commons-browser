# commons-browser
commons-browser is a library used for Browser automation and web scraping using the Selenium library. Using commons-browser, many of the browser tasks, such as navigating to a page, filling forms, clicking on items, etc. can be automated. It can thus be used for automation testing. To allow the interaction with a browser, Selenium requires a Web Driver. commons-browser makes use of ChromeDriver to simulate a real browser

## Contents
- [commons-browser](#commons-browser)
    * [Installation](#installation)
    * [Key Classes](#key-classes)
        + [BrowserFactory](#browserfactory)
        + [AbstractBrowser](#abstractbrowser)
    * [License](#license)

## Installation
To add a dependency on this library using Maven, use the following:
```xml
<dependency>
    <groupId>com.increff.commons</groupId>
    <artifactId>increff-commons-browser</artifactId>
    <version>{commons-browser.version}</version>
</dependency>
```
*Note*: Note: A WebDriver is required to be downloaded. The library currently relies on use of ChromeDriver. Once downloaded, the location of the downloaded file must be specified through the BrowserFactory class as follows `BrowserFactory.setChromeDriverPath("C:\\Downloads\\chromedriver.exe");`

## Key Classes
### BrowserFactory
This provides a way to set a web driver, along with its configurations, depending on the browser that is needed which can then be used for browsing. For example: Chrome, Firefox, etc.

### AbstractBrowser
This class provides methods to instantiate a WebDriver and interact with it in order to simulate and automate a browser. To instantiate the AbstractBrowser, a single constructor argument is required. This argument represents the directory to be used a temporary file download location for the AbstractBrowser

```java
AbstractBrowser browser = new AbstractBrowser(new File("C:\\ChromeWebdriver\\TempDownloads"));
```
This class provides all functionalities required for browsing and interacting with a web page. The primary functions include:

- `navigate(String url)`: Navigate to the specified URL on the browser
- `get(By by)`: Find and return an element in the page
- `mouseOver(By by)`: Hover mouse over an element specified by a Selenium By object
- `click(By by)`: Single click an element on the webpage
- `setOption(By by, String value)`: Select a value from a list of options (e.g. a dropdown)
- `setText(By by, String text)`: Set the text for an input field
- `setCheckbox(By by, boolean select)`: Select/deselect a checkbox
- `waitFor(By by)`: Wait for a target element to be loaded and then return it
- `waitFor(WebElement parent, By child)`: Wait for the loading of a specific nested element and return it once loaded
- `waitFor(int seconds)`: Wait for a specified number of seconds
- `waitFor(ExpectedCondition<?> condition)`: Wait for a certain condition to be met
- `getLastDownloadedFile()`: Get the last downloaded file
## License
Copyright (c) Increff

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and limitations under
the License.
