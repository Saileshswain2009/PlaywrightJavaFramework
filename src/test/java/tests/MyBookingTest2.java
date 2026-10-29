package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class MyBookingTest2 {

    @Test
    public void testBooking2() {
        //Login API Test
       HashMap<Object,Object> loginPayload = new HashMap<>();
        loginPayload.put("email", "munasailesh2021@gmail.com");
        loginPayload.put("password", "Ommsairam@0");
        Playwright playwright = Playwright.create();
        APIRequestContext loginRequest = playwright.request().newContext(new APIRequest.NewContextOptions().setIgnoreHTTPSErrors(true));
        APIResponse loginResponse = loginRequest.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                RequestOptions.create().setData(loginPayload));
        Assert.assertTrue(loginResponse.ok());
        System.out.println("Response Status: " + loginResponse.status());
        System.out.println("Response Body: " + loginResponse.text());
        String token = JsonPath.read(loginResponse.text(), "$.token");
        System.out.println("Login success: " + token);
        //Create Booking API Test
        HashMap<Object, Object> bookingPayload = new HashMap<>();
        bookingPayload.put("customerEmail","suchismitaswain2009@gmail.com");
        bookingPayload.put("customerName","SUCHISMITA SWAIN");
        bookingPayload.put("customerPhone","8658821545");
        bookingPayload.put("eventId","27087");
        bookingPayload.put("quantity","1");
        APIResponse createBookingResponse = loginRequest.post("https://api.eventhub.rahulshettyacademy.com/api/bookings",
                RequestOptions.create().setHeader("Authorization", "Bearer " + token).setData(bookingPayload));
        System.out.println("Create Booking Response Status: " + createBookingResponse.status());
        System.out.println("Create Booking Response Body: " + createBookingResponse.text());
        Assert.assertTrue(createBookingResponse.ok(), "Create Booking API should succeed");
        //Get Booking API Test
        APIResponse getBookingResponse = loginRequest.get("https://api.eventhub.rahulshettyacademy.com/api/bookings",
                RequestOptions.create().setQueryParam("page","1").setQueryParam("limit","10").setHeader("Authorization", "Bearer " + token));
        System.out.println("Get Booking Response Status: " + getBookingResponse.status());
        System.out.println("Get Booking Response Body: " + getBookingResponse.text());
        Assert.assertTrue(getBookingResponse.ok(), "Get Booking API should succeed");
        //Cancel Booking API Test
        int bookingId = JsonPath.read(getBookingResponse.text(), "$.data[0].id");
        APIResponse cancelBookingResponse = loginRequest.delete("https://api.eventhub.rahulshettyacademy.com/api/bookings/" + bookingId,
                RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        System.out.println("Cancel Booking Response Status: " + cancelBookingResponse.status());
        System.out.println("Cancel Booking Response Body: " + cancelBookingResponse.text());
        Assert.assertTrue(cancelBookingResponse.ok(), "Cancel Booking API should succeed");
    }

}
