package Pages;

import Base.BaseClassBrowser;
import Utils.WebdriverUtils;
import org.openqa.selenium.By;

//import static Base.BaseClassBrowser.driver;

public class Logout extends BaseClassBrowser {
    public static By LogoutButton = By.id("logout2");

    public void verifyLogout(){
        WebdriverUtils webDriverUtils = new WebdriverUtils(driver, 5);
        webDriverUtils.click(LogoutButton);
    }
}
