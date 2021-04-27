# cvs-auto-svc-vott

This is the project for creating and managing the VOTT services automated tests

## Getting Started
These instructions will get you up and running with the automation framework.

### Prerequisites
- Browserstack credentials
- Jenkins access
- Java 1.8 SDK
- Maven
- Git
- IntelliJ

###Running Locally
In order to run the test locally your will need to complete the following steps:
- Connect to Jenkins VPN (required for database access)
- Populate the template config file: src>main>resources>config.json  
 !!Ensure only the template is committed to Git!!  
  
- Running can be triggered from IntelliJ and command line using `mvn test`

## Running the Tests
Pull the repo  
Run each test individually in each of the following folders  
Database integrity tests (src/test/java/vott/database)  
Document retrieval service (src/test/java/vott/documentretrieval)  
Enquiry service (src/test/java/vott/testhistory)  
E2E tests (src/test/java/vott/e2e/E2eTest.java)  

## Adding Additional Service and APIKey support
The current config file (src/main/resources/config.json) is configured with the following sections:

1. Database Properties: populate with values required to connect to required database
2. oAuth Properties: split into 2 sections covering implicit and client credentials access
3. API Properties: populate the base URL and branch for the required end point connection
4. API Keys: contains shared key for both the Vehicle and Doc Retrieval Service

Values are deserialized from json via the respective classes stored: src/main/java/vott/config

If an additional key is required for an existing section it will also need to be added to the relevant config class

If an additional section is required a corresponding class with need to be created in the above directory

## Planned improvements

## Contributors

- Robert Whitehouse @RobWhitehouseBJSS
- Emanuel State @Estatebjss
- Paul Benn @PaulBenn-BJSS
