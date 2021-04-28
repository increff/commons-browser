package com.increff.commons.browser;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestAbstractBrowser {

    //@Test
    public void testAbstractBrowser() throws IOException, InterruptedException {
       BrowserFactory.setChromeDriverPath("C:\\ChromeWebdriver\\chromedriver.exe");
       AbstractBrowser browser = new AbstractBrowser(new File("src/test/java/com/increff/commons/browser/resources"));

       //TODO: Use a reliable internal test file
       browser.navigate("https://file-examples-com.github.io/uploads/2017/02/zip_2MB.zip");

       //Wait 5 seconds for file to be downloaded
       Thread.sleep(5000);
       File lastDownloadedFile = browser.getLastDownloadedFile();
       assertEquals("zip_2MB.zip", lastDownloadedFile.getName());

        FileUtils.deleteDirectory(new File("src/test/java/com/increff/commons/browser/resources"));
    }

}