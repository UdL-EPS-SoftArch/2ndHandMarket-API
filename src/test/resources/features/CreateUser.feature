Feature: User Management
  In order to to sell/buy second hand products
  As a user
  I want to be able to register and update my personal data

  Scenario: Create a user
    When I create an user with username "johnsmith" and password "password"
    Then The user status is 201
    Then There is a registered user with username "johnsmith"
    And I can login with username "johnsmith" and password "password"

  Scenario: Create two users
    When I create an user with username "johnsmith" and password "password"
    Then The user status is 201
    Then I create an user with username "smithjohn" and password "password"
    Then The user status is 201
    Then There is a registered user with username "johnsmith"
    Then There is a registered user with username "smithjohn"

  Scenario: Create two users with the same username
    When I create an user with username "johnsmith" and password "password" and display name "john smith"
    Then The user status is 201
    Then There is a registered user with display name "john smith"
    Then I create an user with username "johnsmith" and password "password" and display name "super john smith"
    Then The user status is 500
    Then There is a registered user with username "johnsmith"
    Then There is a registered user with display name "john smith"

  Scenario: Update user password
    Given I create an user with username "johnsmith" and password "password"
    And I can login with username "johnsmith" and password "password"
    When I update username "johnsmith" password to "PassWord"
    Then I can login with username "johnsmith" and password "PassWord"
    And There is a registered user with display name "johnsmith"
    And I cannot login with username "johnsmith" and password "password"

  Scenario: Delete user
    Given I create an user with username "johnsmith" and password "password"
    And I login as "johnsmith" with password "password"
    Then I delete username "johnsmith"
    Then The user status is 204
    Then I cannot login with username "johnsmith" and password "password"

  Scenario: Delete an user which doesn't belong to oneself
    Given I create an user with username "johnsmith" and password "password"
    Given I create an user with username "yoda" and password "password"
    Then I login as "johnsmith" with password "password"
    Then I can login with username "yoda" and password "password"
    Then I delete username "yoda"
    Then The user status is 500
    Then I can login with username "yoda" and password "password"
    Then I delete username "yoda"
    Then The user status is 500
    Then I can login with username "johnsmith" and password "password"
    Then I can login with username "yoda" and password "password"
