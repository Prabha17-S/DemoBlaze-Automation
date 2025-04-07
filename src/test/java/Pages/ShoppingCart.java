package Pages;

import Base.BaseClassBrowser;
import Utils.WebdriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

//import static Base.BaseClassBrowser.driver;

public class ShoppingCart extends BaseClassBrowser {
    public static By CartLink = By.id("cartur");
    public static  By DeleteLink = By.xpath("//td[contains(text(),'MacBook air')]/following-sibling::td/a[text()='Delete']");
    public static By PlaceOrder = By.xpath("//button[@data-target=\"#orderModal\"]");

    public void verifyProductCart() throws InterruptedException {
        WebdriverUtils webDriverUtils = new WebdriverUtils(driver, 5);
        webDriverUtils.click(CartLink);
        Thread.sleep(3000);
        deleteOneMacBookAir();
        //webDriverUtils.click(DeleteLink);
        System.out.println("Product deleted success");
        Thread.sleep(3000);
        webDriverUtils.click(PlaceOrder);
    }
    // Deletes one "MacBook Air" from the cart if multiple are present. It waits for delete buttons, clicks the first one, and validates the count.
    public void deleteOneMacBookAir() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Find all matching MacBook Air delete buttons in the cart before deletion
        List<WebElement> beforeDeleteButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(DeleteLink));
        int beforeDeleteCount = beforeDeleteButtons.size(); // Stores the initial count of MacBook Air products (declare and assign the count)
        System.out.println("Before deletion, MacBook Air count: " + beforeDeleteCount);

        if (!beforeDeleteButtons.isEmpty()) { // Checks if there is at least one MacBook Air in the cart
            beforeDeleteButtons.get(0).click(); // Click only the first delete button
            System.out.println("Clicked delete on one 'MacBook Air'.");

            // Waits until the count of delete buttons is reduced after deletion
           List<WebElement> afterDeleteButtons = wait.until(ExpectedConditions.numberOfElementsToBeLessThan(DeleteLink, beforeDeleteCount));
            int afterDeleteCount = afterDeleteButtons.size(); // Stores the updated count after deletion
            System.out.println("After deletion, MacBook Air count: " + afterDeleteCount);

            // Assertion to check expected and actual to be equal
            Assert.assertEquals(afterDeleteCount, beforeDeleteCount - 1, "Product count before and after deletion!");
        } else {
            System.out.println("No 'MacBook Air' found in the cart.");
        }
    }
}
