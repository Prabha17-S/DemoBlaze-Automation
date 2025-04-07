package Pages;

import Base.BaseClassBrowser;
import Models.CredsType;
import Utils.JsonReader;
import Utils.WebdriverUtils;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

//import static Base.BaseClassBrowser.driver;

public class Login extends BaseClassBrowser {

    public static By LoginModel = By.id("login2");
    public static By UsernameField = By.id("loginusername"); //input[@id="loginusername"]
    public static By PasswordField = By.id("loginpassword");
    public static By LoginButton = By.xpath("//button[@onclick=\"logIn()\"]");
    //public static By CloseLogin = By.xpath("//div[@id='logInModal']//button[@class='close']");

    public void verifyLogin() {
        WebdriverUtils webDriverUtils = new WebdriverUtils(driver, 5);
        webDriverUtils.click(LoginModel);
        try {
            List<CredsType> users = JsonReader.main("LoginCreds.json");
            for (CredsType user : users) {

                webDriverUtils.SendKeys(UsernameField, user.username);
                webDriverUtils.SendKeys(PasswordField, user.password);
                webDriverUtils.click(LoginButton);

                //Assertion Method
                loginHandleAssertion();

                // Switch back to active element
                driver.switchTo().activeElement();
                System.out.println("Login Validation done");

                driver.findElement(UsernameField).clear();
                driver.findElement(PasswordField).clear();

            }
        } //webDriverUtils.click(CloseLogin);
        catch (ElementNotInteractableException e) {
            System.out.println("Login Success good to be proceed");
        }
    }

    public void loginHandleAssertion() {
        // Wait for alert and handle it based on the message
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            String alertMessage = alert.getText(); // Get alert text
            System.out.println("Alert Message: " + alertMessage);

            // Wait for the user to read the message (3 seconds)
            Thread.sleep(3000);

            // Expected alert messages and assertions
            if (alertMessage.contains("Wrong Password")) {
                Assert.assertEquals(alertMessage, "Wrong Password", "Validation for invalid password failed.");
            } else if (alertMessage.contains("Please fill out Username and Password.")) {
                Assert.assertEquals(alertMessage, "Please fill out Username and Password.", "Validation for empty fields failed.");
            }
            else if (alertMessage.contains("User does not exist.")) {
                Assert.assertEquals(alertMessage, "User does not exist.", "Validation for invalid credentials failed.");
            }
            else {
                Assert.fail("Unexpected alert message: " + alertMessage); // Fails test if an unexpected alert appears
            }

            alert.accept();

        } catch (TimeoutException e) {
            System.out.println("No alert appeared.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
