package Pages;

import Base.BaseClassBrowser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

//import static Base.BaseClassBrowser.driver;

public class HomePage extends BaseClassBrowser {
    public static By HomeLink = By.xpath("//a[text()='Home ']");
    public static By CarouselNext = By.xpath("//a[@class=\"carousel-control-next\"]");
    public static By CarouselPrevious = By.xpath("//a[@class=\"carousel-control-prev\"]");
    public static By Categories = By.id("cat");
    public static By FeaturedProducts = By.xpath("//div[@id='tbodyid']//div[@class='card h-100']");
    public static By NextButton = By.id("next2");
    public static By PreviousButton = By.id("prev2");

    // isDesktop Determines if the test is running on a desktop.
    public void verifyHomePage(boolean isDesktop) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Validate homepage UI components
        validateBannersAndCategories(isDesktop);
        validateFeaturedProducts();

        Thread.sleep(3000); // Pause to allow UI elements to settle
        retryClick(CarouselNext);
        retryClick(CarouselPrevious);
        Thread.sleep(3000);
        retryClick(Categories);
        Thread.sleep(3000);
        // If test is running on desktop,Clicks Next and Previous page navigation buttons
        if (isDesktop) {
            retryClick(NextButton);
            retryClick(PreviousButton);
        }
    }


    public void validateBannersAndCategories(boolean isDesktop) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Explicit wait for visibility

        // Validate Banner Carousel is Displayed on Desktop
        if (isDesktop){
            WebElement banner = wait.until(ExpectedConditions.visibilityOfElementLocated(CarouselNext));
            Assert.assertTrue(banner.isDisplayed(), "Banner is displayed correctly.");
            System.out.println(isDesktop);
            System.out.println("isDesktop");
        }

        // Validate Categories are Displayed
        WebElement categorySection = wait.until(ExpectedConditions.visibilityOfElementLocated(Categories));
        Assert.assertTrue(categorySection.isDisplayed(), "Categories are displayed correctly.");

        System.out.println("Banners and categories validation passed.");
    }

    // Validates that at least one featured product is displayed on the homepage
    public void validateFeaturedProducts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Wait until All products are visible
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(FeaturedProducts));

        // Ensure at least one product is displayed
        Assert.assertTrue(products.size() > 0, "Featured products displayed on homepage.");

        System.out.println("Featured products validation passed.");
    }

    // Attempts to click an element multiple times in case of stale element issues.
    public void retryClick(By locator) {
        int maxRetries = 3; // Maximum retry attempts
        int attempt = 0; // Tracks the current attempt number

        while (attempt < maxRetries) { // Loops until the max retries are reached
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Waits for element to be clickable
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click(); // Clicks the element
                System.out.println("Clicked successfully on: " + locator);
                return; // Exit the loop if successful
            } catch (StaleElementReferenceException e) { // Handles cases where the element is no longer valid
                System.out.println("Retrying click due to stale element: Attempt " + (attempt + 1));
            } catch (TimeoutException e) {
                System.out.println("Retrying click due to timeout: Attempt " + (attempt + 1));
            }
            attempt++;
        }
        System.out.println("Failed to click element after multiple attempts: " + locator);
    }
}
