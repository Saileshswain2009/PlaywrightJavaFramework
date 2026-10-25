import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
public class TempCheckoutCheck {
  public static void main(String[] args) throws Exception {
    try (Playwright pw = Playwright.create()) {
      Browser browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
      Page page = context.newPage();
      page.navigate("https://www.saucedemo.com/");
      page.getByPlaceholder("Username").fill("problem_user");
      page.getByPlaceholder("Password").fill("secret_sauce");
      page.locator("#login-button").click();
      page.locator("[data-test='inventory-item']").filter(new Locator.FilterOptions().setHasText("Sauce Labs Bike Light")).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Add to cart")).click();
      page.locator(".shopping_cart_badge").click();
      page.getByText("Checkout").click();
      page.locator("#first-name").fill("Jane");
      page.locator("#last-name").fill("Swain");
      page.locator("#postal-code").fill("67890");
      System.out.println("Before continue URL=" + page.url());
      System.out.println("Before continue title=" + page.locator(".title").textContent());
      page.locator("#continue").click();
      page.waitForTimeout(2000);
      System.out.println("After continue URL=" + page.url());
      System.out.println("After continue title=" + page.locator(".title").textContent());
      browser.close();
      pw.close();
    }
  }
}
