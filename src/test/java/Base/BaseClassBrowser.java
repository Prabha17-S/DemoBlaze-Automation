package Base;

import Config.LoadProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;


public class BaseClassBrowser extends LoadProperties {
    public static WebDriver driver;

    @BeforeTest
    public  void verifySiteInitialized() throws IOException {
        // Loads properties from Config file
        LoadPropertiesFile();
        String browserName = propertyconfig.getProperty("BROWSER");

        if (Objects.equals(browserName, "FireFox")){
           driver = new FirefoxDriver();
//            FirefoxOptions options = new FirefoxOptions();
//                options.addArguments("--headless"); // Enables headless mode for Firefox
//                options.addArguments("--disable-gpu"); // Disables GPU acceleration
//            driver = new FirefoxDriver(options);
        }
        else if (Objects.equals(browserName, "Chrome")){
            // Headless Mode
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            String tempUserDataDir = Files.createTempDirectory("chrome-profile-new").toString();
            options.addArguments("--user-data-dir=" + tempUserDataDir);
            driver = new ChromeDriver(options);
            //driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.get(propertyconfig.getProperty("URL"));
        System.out.println("Site is initialized");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser session closed.");
        }
    }
}
