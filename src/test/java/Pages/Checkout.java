package Pages;

import Base.BaseClassBrowser;
import Models.PurchaseFormType;
import Utils.PurchaseJsonReader;
import Utils.WebdriverUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

//import static Base.BaseClassBrowser.driver;

public class Checkout extends BaseClassBrowser {
    public static By Name = By.id("name");
    public static By Country = By.id("country");
    public static By City = By.id("city");
    public static By CreditCard = By.id("card");
    public static By Month = By.id("month");
    public static By Year = By.id("year");
    public static By PurchaseButton = By.xpath("//button[@onclick=\"purchaseOrder()\"]");
    public static By OkButton = By.xpath("//button[@class=\"confirm btn btn-lg btn-primary\"]");

    // Locators for Order placement conformation
    public static By ThankYouPurchase = By.xpath("//h2[text()='Thank you for your purchase!']");
    public static By OrderConformationParagraphDetails = By.xpath("//p[@class=\"lead text-muted \"]");

    public void verifyCheckoutProcess() throws InterruptedException, IOException {
        try{
            WebdriverUtils webDriverUtils = new WebdriverUtils(driver, 5);
            // Call Json Reader
            List<PurchaseFormType> orderForm = PurchaseJsonReader.main();

            for (PurchaseFormType purchaseDetail : orderForm) {
                // Clear the input fields before entering new data
                driver.findElement(Name).clear();
                driver.findElement(Country).clear();
                driver.findElement(City).clear();
                driver.findElement(CreditCard).clear();
                driver.findElement(Month).clear();
                driver.findElement(Year).clear();

                 File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                 File destFile = new File("failure.png");
                 FileHandler.copy(srcFile, destFile);
                // Entering order details from JSON
                webDriverUtils.SendKeys(Name, purchaseDetail.name);
                webDriverUtils.SendKeys(Country, purchaseDetail.country);
                webDriverUtils.SendKeys(City, purchaseDetail.city);
                webDriverUtils.SendKeys(CreditCard, purchaseDetail.creditCard);
                webDriverUtils.SendKeys(Month, purchaseDetail.month);
                webDriverUtils.SendKeys(Year, purchaseDetail.year);
                webDriverUtils.click(PurchaseButton);

                // Validate alerts
                handleAsserts();
                // Switch back to active element
                driver.switchTo().activeElement();
                System.out.println("Purchase Validation done");
            }
            // Validate order confirmation details
            orderConformationDetails();
            webDriverUtils.click(OkButton);
        }
        catch (Exception e) {
            System.out.println("Exception happened");
            throw e;
        }
    }

    public void handleAsserts() throws InterruptedException {
        // Wait for alert and handle it based on the message
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            String alertMessage = alert.getText(); // Get alert text
            System.out.println("Alert Message: " + alertMessage);

            // Wait for the user to read the message (3 seconds)
            Thread.sleep(3000);

            // Expected alert messages and assertions
            if (alertMessage.contains("Please fill out Name and Creditcard.")) {
                Assert.assertEquals(alertMessage, "Please fill out Name and Creditcard.", "Validation for Place Order Details");
            } else {
                Assert.fail("Unexpected alert message: " + alertMessage); // Fails test if an unexpected alert appears
            }
            alert.accept();

        }
        catch (TimeoutException e) {
            // Do not do anything when there is no alert
            System.out.println("No issue in the given data. So wont get alert. It will proceed to success flow.");
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void orderConformationDetails(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        String expectedThankYouPurchase = "Thank you for your purchase!";

        // Validate Thankyou title
        WebElement thankYoutitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ThankYouPurchase));
        String actualThankYouTitle = thankYoutitleElement.getText();
        Assert.assertEquals(actualThankYouTitle, expectedThankYouPurchase, "Thank you title appeared successfully!");

        // Validate order confirmation paragraph details
        WebElement orderConformParagraghElement = wait.until(ExpectedConditions.visibilityOfElementLocated(OrderConformationParagraphDetails));
        String actualorderConformParagraghtext = orderConformParagraghElement.getText();
        Assert.assertFalse(actualorderConformParagraghtext.isEmpty(), "Order conformation details is not missing from the paragraph!");
        // Retrieve latest order details from JSON for validation
        List<PurchaseFormType> orderForm = PurchaseJsonReader.main();
        actualorderConformParagraghtext.contains(orderForm.get(orderForm.size() - 1).name);
        Assert.assertFalse(actualorderConformParagraghtext.contains(orderForm.get(orderForm.size() - 1).name),"Order Details Name Matching");
    }
}
// orderForm.size() -> length -> 4
//        // orderForm.get(orderForm.size() - 1) -> orderForm.get(4 - 1) -> orderForm.get(3)
//        // orderForm.get(3) ->   {
//                            //     "name": "John Doe",
//                            //     "country": "USA",
//                            //     "city": "New York",
//                            //     "creditCard": "4111111111111111",
//                            //     "month": "12",
//                            //     "year": "2025"
//                            //   }
//        // orderForm.get(3).name -> "John Doe"