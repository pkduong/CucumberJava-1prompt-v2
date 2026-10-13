package com.saucedemo.steps;

import com.saucedemo.config.TestConfig;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.support.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginSteps {
    private LoginPage loginPage;
    private String currentUsername;

    @Given("the user opens the SauceDemo login page")
    public void openLoginPage() {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.open(TestConfig.baseUrl());
    }

    @When("the configured user at position {int} logs in")
    public void loginAsConfiguredUser(int userPosition) {
        if (userPosition < 0 || userPosition >= TestConfig.usernames().size()) {
            throw new IllegalArgumentException("No configured username exists at position " + userPosition);
        }
        currentUsername = TestConfig.usernames().get(userPosition);
        loginPage.login(currentUsername, TestConfig.password());
    }

    @Then("the login outcome matches the configured user's access")
    public void verifyLoginOutcome() {
        if (TestConfig.lockedUsername().equals(currentUsername)) {
            assertEquals(
                    "Epic sadface: Sorry, this user has been locked out.",
                    loginPage.errorMessage(),
                    "The locked account should be denied with SauceDemo's locked-out message."
            );
            return;
        }

        assertEquals(
                "Products",
                new ProductsPage(DriverManager.getDriver()).pageTitle(),
                "An unlocked account should reach the Products page."
        );
    }
}
