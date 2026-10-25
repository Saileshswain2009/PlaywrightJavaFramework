package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Download;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutCompletePage {
    Page page;
    private static final String thankYouMessage1 = ".complete-header";
    private static final String thankYouMessage2 = ".complete-text";
    private static final String generatePdfOrderButton = "Generate PDF order";

    public CheckoutCompletePage(Page page) {
        this.page = page;
    }

    public void verifyCheckoutComplete() {
        assertThat(page.locator(thankYouMessage1)).hasText("Thank you for your order!");
        assertThat(page.locator(thankYouMessage2)).hasText("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    public void generatePdfOrder(String filePath) throws IOException {
        java.nio.file.Path pdfPath = Paths.get(filePath);
        if (pdfPath.getParent() != null) {
            Files.createDirectories(pdfPath.getParent());
        }

        Download download = page.waitForDownload(
                () -> page.getByText(generatePdfOrderButton).click());
        download.saveAs(pdfPath);
        System.out.println("Downloaded order PDF to: " + pdfPath.toAbsolutePath());
    }

    public void verifyTextInsidePDF(String filePath, String expectedText) {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);

            String normalizedPdfText = pdfText == null ? "" : pdfText.replaceAll("\\s+", " ").trim();
            String normalizedExpectedText = expectedText == null ? "" : expectedText.replaceAll("\\s+", " ").trim();

            Assert.assertTrue(
                    normalizedPdfText.toLowerCase().contains(normalizedExpectedText.toLowerCase()),
                    "Expected text not found in downloaded PDF. Expected: '" + normalizedExpectedText
                            + "' but extracted text was: '" + normalizedPdfText + "'");

            System.out.println("Verified text in downloaded PDF: " + expectedText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
