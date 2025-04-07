package Test;

import Base.BaseClassBrowser;
import Pages.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class DemoblazeTest extends BaseClassBrowser {
   // BaseClassBrowser demoBrowser = new BaseClassBrowser();
    Signup demoSignup = new Signup();
    Login demoLogin = new Login();
    HomePage demoHome = new HomePage();
    Responsiveness demoResponsive = new Responsiveness();
    ProductDetails demoProduct = new ProductDetails();
    ShoppingCart demoCart = new ShoppingCart();
    Checkout demoOrderCheckup = new Checkout();
    Logout demoLogout = new Logout();

    @Test(priority = 1, description = "User Signup and Login Flow")
    public void userAuthentication() throws IOException, InterruptedException {
//        demoBrowser.verifySiteInitialized();
       // demoSignup.verifySignup();
        demoLogin.verifyLogin();
    }
    @Test(priority = 2, description = "Home Page UI Validation")
    public void validateHomeScreen() throws InterruptedException {
        demoHome.verifyHomePage(true);
        demoResponsive.testResponsiveness();
    }
    @Test(priority = 3, description = "Production Selection and Add to Cart Validation")
    public void productDetailsValidation() throws InterruptedException {
        demoProduct.verifyProductDetails();
    }
    @Test(priority = 4, description = "Shopping Cart Validation")
    public void shoppingCartValidation() throws InterruptedException {
        demoCart.verifyProductCart();
    }
    @Test(priority = 5, description = "Check and Order Confirmation")
    public void checkoutProcessValidation() throws InterruptedException, IOException {
        demoOrderCheckup.verifyCheckoutProcess();
    }
    @Test(priority = 6, description = "User Logout Validation")
    public void userLogoutValidation(){
        demoLogout.verifyLogout();
    }
}
