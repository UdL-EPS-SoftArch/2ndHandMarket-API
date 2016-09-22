Feature: Post Advertisement
  In order to advertise a second hand product to sell
  As a seller
  I want to create an advertisement about the product and selling conditions

  Scenario: Create advertisement
    When I post and advertisement with title "Selling old Spring Boot"
    Then The status is 201
    And There is an advertisement with title "Selling old Spring Boot"
