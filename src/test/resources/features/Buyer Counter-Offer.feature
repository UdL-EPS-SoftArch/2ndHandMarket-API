Feature: Buyer Counter-Offer
  In order to try to buy the product cheaper
  As a buyer
  I want to propose a new lower price

  Scenario: Create advertisement
    When I post a new buyer counter offer with a lower price value "lower price"
    Then The status is 201
    And There is a new buyer counter offer with a lower price value "lower price"
