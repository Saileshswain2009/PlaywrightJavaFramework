package pages;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class LoginPage {
    Page page;
    String baseUrl;
    private static final String username = "Username";
    private static final String password = "Password";
    private static final String loginBtn = "#login-button";

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public ProductsPage loginApplication(String username1, String password1) {
        page.navigate(baseUrl);
        System.out.println(page.title());
        page.getByPlaceholder(username).fill(username1);
        page.getByPlaceholder(password).fill(password1);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/loginPage.png")));
        page.locator(loginBtn).click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/ProductsPage.png")));
        return new ProductsPage(page);
    }
}