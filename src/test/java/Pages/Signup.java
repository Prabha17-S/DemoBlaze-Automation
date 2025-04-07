package Pages;

import Base.BaseClassBrowser;
import Models.CredsType;
import Utils.JsonReader;
import Utils.WebdriverUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class Signup extends BaseClassBrowser {
    // Locators for signup elements
    public static By SignModel = By.id("signin2");
    public static By UsernameInput = By.id("sign-username");
    public static By PasswordInput = By.id("sign-password");
    public static By SignupButton = By.xpath("//button[@onclick='register()']");
    //public static By CloseButton = By.xpath("//div[@id='signInModal']//button[@class='close']");

    //verifySignup func: Automates the user signup process by reading credentials from JSON, entering them in the form, clicking signup, and handling alerts.
    public void verifySignup() throws InterruptedException {

        WebdriverUtils webDriverUtils = new WebdriverUtils(driver, 5); // Initializes WebDriver utilities with a 5-sec wait
        Thread.sleep(3000);
        webDriverUtils.click(SignModel);
        // Reads user credentials from JSON file
        List<CredsType> users = JsonReader.main("Creds.json");
        // Iterates through each user in the JSON file
        for (CredsType user : users) {
            // Clear the input fields before entering new data
            driver.findElement(UsernameInput).clear();
            driver.findElement(PasswordInput).clear();

            webDriverUtils.SendKeys(UsernameInput, user.username);
            webDriverUtils.SendKeys(PasswordInput, user.password);
            webDriverUtils.click(SignupButton);

            //Assertion Method
            handleAssertion();

            // Switch back to active element after handling the alert
            driver.switchTo().activeElement();
            System.out.println("Signup Validation done");
        }
            //webDriverUtils.click(CloseButton);
    }
    public void handleAssertion() {
        // Wait for alert and handle it based on the message
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Waits up to 5 seconds for the alert
            Alert alert = wait.until(ExpectedConditions.alertIsPresent()); // Waits until an alert appears

            String alertMessage = alert.getText(); // // Retrieves the alert message
            System.out.println("Alert Message: " + alertMessage);

            // Wait for the user to read the message (3 seconds)
            Thread.sleep(3000);

            // Expected alert messages and assertions
            if (alertMessage.contains("Sign up successful.")) {
                Assert.assertEquals(alertMessage, "Sign up successful.", "Signup should be successful.");
            }
            else if (alertMessage.contains("Please fill out Username and Password.")) {
                Assert.assertEquals(alertMessage, "Please fill out Username and Password.", "Validation for empty fields failed.");
            }
            else if (alertMessage.contains("This user already exist.")) {
                Assert.assertEquals(alertMessage, "This user already exist.", "Existing user validation failed.");
            }
            else {
                Assert.fail("Unexpected alert message: " + alertMessage); // Fails test if an unexpected alert appears
            }

            alert.accept(); // Clicks "OK" on the alert

        } catch (TimeoutException e) {
            System.out.println("No alert appeared.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

