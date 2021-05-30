Description:

This program is used to identify the outliers from the given set of data.

Installation and Dependencies:

We use Springboot and maven configuration to install the application.
  - OpenCSv for reading the csv file
  - log4j for logging
  - Spring boot dependencies
  - Enter mvn clean install to run the program.

Steps for installation;
 - Checkout the code from github
 - Enter the command mvn clean install which will download the package and run the code with testcases


Application Logic.
   The curret application logic reads a static data from the CSV  but this application can support dynamic values.In case of dynamic values we can set the range of data which should be considered for the comparison.

Rest-end Point
 - The application can be exposed as rest end point.To access the application via rest api ,run the springboot application and hit the application
 - API - http://localhost:8080/api/v1/cleandata
