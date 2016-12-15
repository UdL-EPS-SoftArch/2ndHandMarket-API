Feature: Add Advertisement to a WishList
  A advertisement can be a favorite product and not necessary buy
  As a buyer
  I want to add advertisements in a list

  Scenario: Add advertisement to the wish
    Given There is an existing advertisement with title "Laptop" and price 10.0
    When I create an user with username "Tom" and password "pass"
    And I post a wishlist to advertisement "Tom"
