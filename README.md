# cvs-auto-svc-vott

This is the project for creating and managing the VOTT services automated tests

## Getting Started
These instructions will get you up and running with the automation framework.

### Prerequisites
- Browserstack credentials
- Jenkins access (provided by DevOps)
- Permissions for RDS DB to access the LDAP (provided by DevOps)
- Java 11 or above
- Maven
- Git
- IntelliJ

You will need to install the DVSA security tools - [repo-security-scanner](https://github.com/UKHomeOffice/repo-security-scanner) - on your machine as well as [git-secrets](https://github.com/awslabs/git-secrets) for AWS.
Then create a pre-commit hook:
```shell
echo 'git secrets --scan && git log -p | scanrepo' > .git/hooks/pre-commit && chmod +x .git/hooks/pre-commit
# You can check that the hook is present
cat .git/hooks/pre-commit
```

This hook will run every time you commit code and checks for aws secrets and dvsa secrets/patterns.

###Running Locally
In order to run the test locally your will need to complete the following steps once the LDAP access (with the relevant role) has been provided:
- Connect to Jenkins VPN (required for database access)
- Create config file in `src/main/resources/config.json` with the relevant values. An empty config.example.json file has been created to help.
- Running can be triggered from IntelliJ and command line using `mvn verify` or `mvn clean verify`
- To test on specific branch locally change following in your config.json:
  
  "databaseName": "CVSNOP[branch]"
  
  "branch": "[branch]"
  
  "apiKeys": {
  "enquiryService": "xxxxxxxxxxxxxxxxxxx-[branch]"
  }

It is recommended to use a java version manager such as [jenv](https://github.com/jenv/jenv) and package manager such as [brew](https://brew.sh/) (Mac OS) for example to run this repository since it will require Java 11+ and other CVS services run on Java8.
You will need to install the relevant jdk and configure your project accordingly. Please refer to the following documentation:
- [Managing jenv](https://www.jenv.be/)
- [brew and JDK](https://gist.github.com/tomysmile/a9a7aee85ff73454bd57e198ad90e614)

Once installed, you can run `jenv help` for its documentation.

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
