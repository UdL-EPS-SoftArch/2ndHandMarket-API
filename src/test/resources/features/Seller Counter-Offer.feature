Feature: Seller Counter-Offer
  In order to sell the product at best price
  As a seller
  I want to up the offer

  Scenario: Create Seller Counter-Offer
    When I change the buyerCounterOffer value is "value"
    Then the buyerCounterOffer value is "value"
    And There is a product with buyerCounterOffer "value"