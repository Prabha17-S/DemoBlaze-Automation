package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.awt.SystemColor.text;

public class WebdriverUtils {

    private  WebDriverWait wait; // WebDriverWait instance for handling explicit waits

    //Constructor to initialize WebDriverWait with a specific timeout (Params: Webdriver instance to be used, wait time)
    public WebdriverUtils(WebDriver driver, int timeout) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }
    // Clicks an element after ensuring its clickable (By locator)
    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); // utility func of click
        element.click();
    }

    public void SendKeys(By locator, String text) {
        WaitForElementToBeVisible(locator).sendKeys(text); // Waits for the element to be visible, then sends the provided text

    }

    public WebElement WaitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); //Waits until the specified element is visible and returns it
    }
}
