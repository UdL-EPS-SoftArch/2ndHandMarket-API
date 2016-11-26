Feature: Purchase
  In order to finalize the acquisition of a second hand product
  As a buyer
  I want to create a purchase to an advertisement

  @quick
  Scenario: Create a purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1"
    Then The purchase status is 201
    And There are 1 purchases
    And There is a purchase with advertisement title "Santa"
    And There is a purchase with purchaser "user2"
    And There is a purchase with date

  @quick
  Scenario: Not logged in user creates a purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I'm not logged in
    And I post a purchase to advertisement "1"
    Then The purchase status is 401
    And There are 0 purchases

  @quick
  Scenario: Create a purchase to no product
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to no advertisement
    Then The purchase status is 400
    Then There are 0 purchases

  @quick
  Scenario: Create a purchase to a non-existing product
    Given I login as "user" with password "password"
    And I post a purchase to advertisement "1"
    Then The purchase status is 400
    And There are 0 purchases

  @quick
  Scenario: Create a purchase to themselves
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I post a purchase to advertisement "1"
    Then The purchase status is 500
    And There are 0 purchases

  @quick
  Scenario: Create a purchase to a product that has already been purchased
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1"
    And I login as "user3" with password "password"
    And I post a purchase to advertisement "1"
    Then The purchase status is 500
    And There are 1 purchases

  @quick
  Scenario: Create purchase to the second product
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I post an advertisement with title "Reindeer" and price "6102"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "2"
    Then The purchase status is 201
    And There is a purchase with advertisement title "Reindeer"

  @quick
  Scenario: Update purchase
    Given I login as "user" with password "password"
    And I post an advertisement with title "Santa" and price "2016"
    And I login as "user2" with password "password"
    And I post a purchase to advertisement "1"
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
    And I post a purchase to advertisement "1"
    And I delete purchase "1"
    Then The purchase status is 500
    And There are 1 purchases
    And There is a purchase with advertisement title "Santa"