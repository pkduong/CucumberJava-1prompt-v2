Feature: SauceDemo login
  As a store user
  I want to authenticate with my configured account
  So that access matches the account's status

  Scenario Outline: Login using each configured account
    Given the user opens the SauceDemo login page
    When the configured user at position <userIndex> logs in
    Then the login outcome matches the configured user's access

    Examples:
      | userIndex |
      | 0         |
      | 1         |
      | 2         |
      | 3         |
      | 4         |
      | 5         |
