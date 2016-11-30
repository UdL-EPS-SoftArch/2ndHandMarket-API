Feature: SellerCounterOffer
  In order to sell the product at best price
  As a seller
  I want to up the offer

  Scenario: Create seller counter offer
    Given There is an existing advertisement with title "Selling old boot"
    And There is a seller offer for the previous advertisement with price value 30.50
    And There is a buyer counter offer for the previous seller offer with price value 25.30
    When I create a seller counter offer for the previous buyer counter offer with price value 27.40
    Then There is a new seller counter offer with price value 27.40