package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutInformationPage {
    Page page;
    private static final String firstNameField = "#first-name";
    private static final String lastNameField = "#last-name";
    private static final String postalCodeField = "#postal-code";
    private static final String continueButton = "#continue";

    public CheckoutInformationPage(Page page) {
        this.page = page;
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First Name cannot be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last Name cannot be empty");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal Code cannot be empty");
        }

        page.locator(firstNameField).waitFor();
        page.locator(firstNameField).fill(firstName);
        page.locator(lastNameField).waitFor();
        page.locator(lastNameField).fill(lastName);
        page.locator(postalCodeField).waitFor();
        page.locator(postalCodeField).fill(postalCode);
    }

    public CheckoutOverviewPage clickContinue() {
        assertThat(page.locator(".title")).hasText("Checkout: Your Information");
        page.locator(continueButton).click();
        return new CheckoutOverviewPage(page);
    }
}
