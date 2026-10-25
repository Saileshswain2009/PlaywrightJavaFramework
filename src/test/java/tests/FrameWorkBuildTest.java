package tests;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.DataProviderUtil;

import java.io.IOException;
import java.util.HashMap;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FrameWorkBuildTest extends TestBase {

    @DataProvider(name = "fillOutData")
    public Object[][] fillOutData() throws IOException {
        return DataProviderUtil.getJsonDataToMap("/src/test/resources/fillOutData.json");
    }

    @Test(groups = {"framework"}, dataProvider = "fillOutData", description = "Book product and verify it is booked or not !!!")
    public void demoTest(HashMap<String, String> data) throws IOException {
        String productName = "Sauce Labs Bike Light";
        double productPrice = 9.99;
        LoginPage loginPage = new LoginPage(page, base_Url);
        ProductsPage productsPage = loginPage.loginApplication(data.get("username"), data.get("password"));
        Locator targetCard = productsPage.selectProductAndAddToCart(productName);
        productsPage.AddToCart(productName, targetCard);
        double productsPage1 = productsPage.verifyRate(productName, targetCard);
        Assert.assertEquals(productsPage1, productPrice);
        AddToCartPage addtoCartPage = productsPage.ClickAddToCart();
        assertThat(page).hasURL(base_Url + "cart.html");
        addtoCartPage.particularProductWithPriceAddedToCart();
        CheckoutInformationPage checkoutInformationPage = addtoCartPage.clickCheckout();
        checkoutInformationPage.fillCheckoutInformation(data.get("firstName"), data.get("lastName"), data.get("postalCode"));
        CheckoutOverviewPage checkoutOverviewPage = checkoutInformationPage.clickContinue();
        CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.verifyCheckoutOverview(productName, String.valueOf(productPrice));
        checkoutCompletePage.verifyCheckoutComplete();
        String pdfPath = "src/test/resources/OrderDetails.pdf";
        checkoutCompletePage.generatePdfOrder(pdfPath);
        checkoutCompletePage.verifyTextInsidePDF(pdfPath, productName);
    }
}
