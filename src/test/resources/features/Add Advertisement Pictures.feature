Feature: Add Advertisement Picture
  In order to make advertisements more informative and appealing
  As a seller
  I want to add pictures to my advertisements

  Scenario: List existing advertisement pictures
    Given There is an existing advertisement with title "Selling old Spring Boot" and price 1.0
    And The previous advertisement has already a picture with filename "overview.jpg"
    When I list the previous advertisement pictures
    Then I get a list containing 1 picture


  Scenario: Add additional picture to existing advertisement pictures
    Given There is an existing advertisement with title "Selling old Spring Boot" and price 1.0
    And The previous advertisement has already a picture with filename "overview.jpg"
    When I add a picture with filename "detail.jpg" of the previous advertisement
    And I list the previous advertisement pictures
    Then I get a list containing 2 picture