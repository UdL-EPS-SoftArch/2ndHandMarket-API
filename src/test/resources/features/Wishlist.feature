Feature: Add Advertisement to a WishList
  A advertisement can be a favorite product and not necessarily buy
  As a buyer
  I want to add advertisements to my wishlist

  Scenario: Add advertisement to wishlist
    Given There is an existing advertisement with title "Laptop" and price 10.0
    When I create an user with username "Tom" and password "pass"
    And I post the advertisement with title "Laptop" to "Tom" wishlist
    Then User "Tom" has a wishlist with 1 advertisement
    And The advertisement with title "Laptop" has a wisher named "Tom"
