Feature: Post Advertisement
  In order to register a user
  As a seller
  I want to create a user with the seller propieties

  Scenario: Create seller
    When I create a seller with name "Pepo"
    Then The status is 201
    And There is a seller with name "Pepo"
