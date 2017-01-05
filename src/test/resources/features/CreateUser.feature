Feature: User Management
  In order to to sell/buy second hand products
  As a user
  I want to be able to register and update my personal data

  Scenario: Create a user
    When I create an user with username "johnsmith" and password "password"
    Then There is a registered user with username "johnsmith"
    And I can login with username "johnsmith" and password "password"

  Scenario: Update user password
    Given I create an user with username "johnsmith" and password "password"
    And I can login with username "johnsmith" and password "password"
    When I update username "johnsmith" password to "PassWord"
    Then I can login with username "johnsmith" and password "PassWord"
    And There is a registered user with display name "johnsmith"
    And I cannot login with username "johnsmith" and password "password"
