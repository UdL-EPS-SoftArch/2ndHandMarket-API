Feature: Purchase
  In order to finalize the acquisition of a second hand product
  As a buyer
  I want to create a purchase to an advertisement

  @quick
  Scenario: Create a purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1" who "user"
    Then The purchase status is 201
    And There are 1 purchases
    And There is an advertisement with purchase
    And There is a purchase with advertisement title "Santa"
    And There is a purchase with purchaser "user2"
    And There is a purchase with date
    And There is a purchase with total "2016.0"
    And There are 2 private messages

  @quick
  Scenario: Not logged in user creates a purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I'm not logged in
    And I post a purchase to advertisement "1" who "none"
    Then The purchase status is 401
    And There are 0 purchases

  @quick
  Scenario: Create a purchase to no product
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I "user2" post a purchase to no advertisement
    Then The purchase status is 500
    Then There are 0 purchases

  @quick
  Scenario: Create a purchase to a non-existing product
    Given I login as "user" with password "password"
    And I post a purchase to advertisement "1" who "user"
    Then The purchase status is 500
    And There are 0 purchases

  @quick
  Scenario: Create a purchase to themselves
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I post a purchase to advertisement "1" who "user"
    Then The purchase status is 500
    And There are 0 purchases

  @quick
  Scenario: Create a purchase to a product that has already been purchased
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1" who "user2"
    And I login as "user3" with password "password"
    And I post a purchase to advertisement "1" who "user3"
    Then The purchase status is 500
    And There are 1 purchases

  @quick
  Scenario: Create purchase to the second product
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I post an advertisement with title "Reindeer" and price "6102"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "2" who "user2"
    Then The purchase status is 201
    And There is a purchase with advertisement title "Reindeer"
    And There is a purchase with total "6102"
    And There are 2 private messages

  @quick
  Scenario: Update advertisement if it has already been purchased
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "ponies"
    And I fill in price with "42"
    And I post the advertisement
    Then I login as "user2" with password "password"
    Then I post a purchase to advertisement "1" who "user2"
    Then I login as "user" with password "password"
    Then I put the advertisement with id "1"
    Then The status is 500

  @quick
  Scenario: Delete advertisement if it has already been purchased
    Given I login as "user" with password "password"
    When I post an advertisement with title "Ponies" and price "42"
    Then I login as "user2" with password "password"
    Then I post a purchase to advertisement "1" who "user2"
    Then I login as "user" with password "password"
    Then I delete the advertisement with id "1"
    Then The status is 500

  @quick
  Scenario: Update purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1" who "user2"
    And I put purchase "1" with advertisement "1"
    Then The purchase status is 500
    And There are 1 purchases
    And There is a purchase with advertisement title "Santa"
    And I patch purchase "1" with advertisement "1"
    Then The purchase status is 500
    And There are 1 purchases
    And There is a purchase with advertisement title "Santa"

  @quick
  Scenario: Delete purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1" who "user2"
    And I delete purchase "1"
    Then The purchase status is 500
    And There are 1 purchases
    And There is a purchase with advertisement title "Santa"

  @quick
  Scenario: Create a mass-item purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I post an advertisement with title "Claus" and price "2017"
    And I login as "user2" with password "password"
    And I "user2" post a mass purchase to advertisements "1" and "2"
    Then The purchase status is 201
    Then There are 1 purchases
    Then There is a purchase with 2 advertisements
    Then There are 3 private messages