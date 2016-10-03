Feature: SellerCounterOffer
  In order to sell the product at best price
  As a seller
  I want to up the offer

  Scenario: Create SellerCounterOffer
    When I change the offer value to 25.9
    Then A counter-offer id has been generated
    And There is a new seller counter offer with an upper price value 25.9