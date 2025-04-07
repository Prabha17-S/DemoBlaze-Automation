package Pages;

import Base.BaseClassBrowser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

//import static Base.BaseClassBrowser.driver;

public class ProductDetails extends BaseClassBrowser {
    public static By HomeLink = By.xpath("//a[text()='Home ']");
    public static By Phones = By.xpath("//a[@onclick=\"byCat('phone')\"]");
    public static By Phone6 = By.xpath("//a[text()='Iphone 6 32gb']");
    public static By AddToCart1 = By.xpath("//a[@onclick=\"addToCart(5)\"]");
    public static By Laptops = By.xpath("//a[@onclick=\"byCat('notebook')\"]");
    public static By Laptop1 = By.xpath("//a[text()='MacBook air']");
    public static By AddtoCart2 = By.xpath("//a[@onclick=\"addToCart(11)\"]");
    public static By Monitors = By.xpath("//a[@onclick=\"byCat('monitor')\"]");
    public static By Monitor1 = By.xpath("//a[text()='Apple monitor 24']");
    public static By AddToCart3 = By.xpath("//a[@onclick=\"addToCart(10)\"]");

    // Locators for Product Information Details
    public static By ProductTitle = By.xpath("//h2[@class='name']");
    public static By ProductPrice = By.xpath("//h3[@class='price-container']");
    public static By ProductDescription = By.xpath("//div[@id='more-information']/p");

    public void verifyProductDetails() throws InterruptedException {
//        WebdriverUtils webDriverUtils = new WebdriverUtils(driver, 5);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Validate Product Details for Phone
        retryClick(Phones);
        retryClick(Phone6);
        // func call
        validateProductInfo("Iphone 6 32gb"); //Verify product details
        retryClick(AddToCart1);
        handleAlert();
        System.out.println("Iphone add to cart Validation done");
        retryClick(HomeLink);

        // Validate Product Details for Laptop
        retryClick(Laptops);
        retryClick(Laptop1);
        validateProductInfo("MacBook air"); //Verify product details
        // Add Same Product to the Cart for the quantity increase
        addMultipleMac();
        addMultipleMac();
        retryClick(HomeLink);

        // Validate Product Details for Monitors
        retryClick(Monitors);
        retryClick(Monitor1);
        validateProductInfo("Apple monitor 24"); //Verify Product details
        retryClick(AddToCart3);
        handleAlert();
        driver.switchTo().activeElement();
        System.out.println("Monitor add to cart Validation done");

    }
    public void validateProductInfo(String expectedProductName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Validate Product Name
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductTitle));
        String actualProductName = titleElement.getText();
        Assert.assertEquals(actualProductName, expectedProductName, "Product name does match!");

        // Validate Product Price
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductPrice));
        String productPrice = priceElement.getText();
        Assert.assertFalse(productPrice.isEmpty(), "Product price is not missing!");

        // Validate Product Description
        WebElement descriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductDescription));
        String productDescription = descriptionElement.getText();
        Assert.assertFalse(productDescription.isEmpty(), "Product description is not missing!");

    }
    public void addMultipleMac(){
        retryClick(AddtoCart2);
        handleAlert();
        driver.switchTo().activeElement();
        System.out.println("Mac Book Air added twice");
    }

    public void retryClick(By locator) {
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                System.out.println("Clicked successfully on: " + locator);
                return; // Exit loop if successful
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying click due to stale element: Attempt " + (attempt + 1));
            } catch (TimeoutException e) {
                System.out.println("Retrying click due to timeout: Attempt " + (attempt + 1));
            }
            attempt++;
        }
        System.out.println("Failed to click element after multiple attempts: " + locator);
    }
    public void handleAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            String alertMessage = alert.getText(); // Get alert text
            System.out.println("Alert Message: " + alertMessage);

            // Wait for the user to read the message (3 seconds)
            Thread.sleep(3000);

            // Expected alert messages and assertions
            if (alertMessage.contains("Product added.")) {
                Assert.assertEquals(alertMessage, "Product added.", "Product added to Cart.");
            }
            alert.accept();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
