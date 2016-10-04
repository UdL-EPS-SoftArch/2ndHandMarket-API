Feature: Add Advertisement Picture
  In order to make advertisements more informative and appealing
  As a seller
  I want to add pictures to my advertisements

  Scenario: List existing advertisement pictures
    Given There is an existing advertisement with title "Selling old Spring Boot" and price 1.0
    And The previous advertisement has already a picture with filename "overview.jpg"
    When I list the previous advertisement pictures
    Then I get a list containing 1 picture

  Scenario: Add picture to existing advertisement
    Given There is an existing advertisement with title "Selling old Spring Boot" and price 1.0
    And I login as "user" with password "password"
    When I add a picture with filename "overview.jpg" of the previous advertisement
    And I list the previous advertisement pictures
    Then I get a list containing 1 picture
    And Picture number 1 has filename "overview.jpg", owner "user" and was just created

  Scenario: Authentication required error when adding picture if not authenticated
    Given There is an existing advertisement with title "Selling old Spring Boot" and price 1.0
    And I'm not logged in
    When I add a picture with filename "overview.jpg" of the previous advertisement
    Then The error message is "Full authentication is required to access this resource"
    And I list the previous advertisement pictures
    And I get a list containing 0 pictures

  Scenario: Add additional picture to existing advertisement pictures
    Given There is an existing advertisement with title "Selling old Spring Boot" and price 1.0
    And The previous advertisement has already a picture with filename "overview.jpg"
    And I login as "user" with password "password"
    When I add a picture with filename "detail.jpg" of the previous advertisement
    And I list the previous advertisement pictures
    Then I get a list containing 2 pictures