package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class TestBase {
    Page page;
    Browser browser;
    Playwright playwright;
    BrowserContext context;
    String base_Url;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            prop.load(fis);
        }
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
        System.out.println("Browser = " + browserName);
        playwright = Playwright.create();
        if ("firefox".equalsIgnoreCase(browserName)) {
            System.out.println("Launching Firefox");
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        } else if ("webkit".equalsIgnoreCase(browserName)) {
            browser = playwright.webkit().launch();
        } else if ("chrome".equalsIgnoreCase(browserName)) {
            //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
            browser = playwright.chromium().launch();
        } else {
            throw new RuntimeException("Unsupported Browser : " + browserName);
        }
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
        page.waitForTimeout(2000);
        base_Url = prop.getProperty("qa_url");
    }

    @org.testng.annotations.AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
