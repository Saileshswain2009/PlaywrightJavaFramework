package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ProductsPage {
    Page page;
    private static final String products = "div[data-test='inventory-item']";
    private static final String addToCard="Add to cart";
    private static final String itemPrice=".inventory_item_price";
    private static final String clickAddToCart=".shopping_cart_badge";

    public ProductsPage(Page page) {
        this.page = page;
    }

    public Locator selectProductAndAddToCart(String productName) {
        Locator products1 = page.locator(products);
        System.out.println(products1.count());
        return products1.filter(new Locator.FilterOptions().setHasText(productName));
    }
    public void AddToCart(String productName, Locator targetCard) {
        targetCard.getByRole(AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName(addToCard))
                .click();
    }
    public double verifyRate(String productName,Locator targetCard)
    {
      //  Locator targetCard=selectProductAndAddToCart(productName);
        String price=targetCard.locator(itemPrice).textContent();
        System.out.println(price);
        //return Integer.parseInt(price);
        return Double.parseDouble(price.replace("$", ""));
    }

    public AddToCartPage ClickAddToCart()
    {
        page.locator(clickAddToCart).click();
        return new AddToCartPage(page);
    }
}
