package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutOverviewPage {
    Page page;
    private static final String finishButton = "Finish";
    private static final String cancelButton = "#cancel";
    private static final String itemName = ".inventory_item_name";
    private static final String itemPrice = ".inventory_item_price";

    public CheckoutOverviewPage(Page page) {
        this.page = page;
    }

    public CheckoutCompletePage verifyCheckoutOverview(String productName, String productPrice) {
        assertThat(page.locator(".title")).hasText("Checkout: Overview");
        String pname = page.locator(itemName).first().textContent();
        assertThat(page.locator(itemName).first()).hasText(productName);
        System.out.println("Product Name in Checkout Overview: " + pname);
        String actualPrice = page.locator(itemPrice).first().textContent();
        actualPrice = actualPrice.replace("$", "").trim();
        Assert.assertEquals(actualPrice, productPrice, "Product Price in Checkout Overview does not match the expected price.");
        System.out.println("Product Price in Checkout Overview: " + actualPrice);
        page.getByText(finishButton).click();
        assertThat(page.locator(".complete-header")).isVisible();
        return new CheckoutCompletePage(page);
    }
}
