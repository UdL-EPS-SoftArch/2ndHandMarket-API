Feature: Post Advertisement
  In order to register a user
  As a seller
  I want to create a user with the seller propieties

  Scenario: Create seller
    When I create a seller with name "Pepo" and mail "pepo@pepo.com" and password "hellopepo"
    Then An id is generated for this seller
    And There is a seller with name "Pepo"
    And There is a seller with mail "pepo@pepo.com"
    And There is a seller with password "hellopepo"