package Pages;

import Base.BaseClassBrowser;
import org.openqa.selenium.Dimension;

public class Responsiveness extends BaseClassBrowser {

    HomePage verifyHomePageObject = new HomePage();

    public void testResponsiveness() throws InterruptedException {

        Dimension tabletSize = new Dimension(768, 1024);
        Dimension mobileSize = new Dimension(375, 812);


        // Tablet View
        driver.manage().window().setSize(tabletSize);
        System.out.println("Tested Tablet View: " + tabletSize);
        verifyHomePageObject.verifyHomePage(false);

        // Mobile View
        driver.manage().window().setSize(mobileSize);
        System.out.println("Tested Mobile View: " + mobileSize);
        verifyHomePageObject.verifyHomePage(false);

        // Back to Normal Screen
        System.out.println("Set back to default screen size");
        driver.manage().window().maximize();
    }
}
