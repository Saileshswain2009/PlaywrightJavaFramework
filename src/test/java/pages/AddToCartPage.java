package pages;

import com.microsoft.playwright.Page;

public class AddToCartPage {
    Page page;
    private static final String productAddedToCart = ".inventory_item_name";
    private static final String productPriceAddedToCart = ".inventory_item_price";
    private static final String checkoutButton = "Checkout";

    public AddToCartPage(Page page) {
        this.page = page;
    }

    public boolean productAddedToCartVisibility() {
        boolean productVisible = page.locator(productAddedToCart).first().isVisible();
        boolean priceVisible = page.locator(productPriceAddedToCart).first().isVisible();
        return productVisible && priceVisible;
    }

    public void particularProductWithPriceAddedToCart() {
        if (productAddedToCartVisibility()) {
            System.out.println("Product and Price are visible");
        }
        String productName = page.locator(productAddedToCart).first().textContent();
        String productPrice = page.locator(productPriceAddedToCart).first().textContent();
        System.out.println("Product: " + productName + ", Price: " + productPrice);
    }

    public CheckoutInformationPage clickCheckout() {
        page.getByText(checkoutButton).click();
        return new CheckoutInformationPage(page);
    }
}
