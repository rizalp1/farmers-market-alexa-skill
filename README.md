# Alexa Skills Kit Project- Puget Sound Farmers Market

## Concepts
The package uses in memory collection of farmers market in puget sound area to search and find the one user wants.

## Building to a jar and uploading.
1. Run this command:
```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
```
2. Upload 'target/farmersmkt-1.0-jar-with-dependencies.jar' to S3 and use in Lambda, or directly to the Lambda function.

## Setup
To run this example skill you need to do two things. The first is to deploy the example code in lambda, and the second is to configure the Alexa skill to use Lambda.

### AWS Lambda Setup
Refer to [Hosting a Custom Skill as an AWS Lambda Function](https://developer.amazon.com/docs/custom-skills/host-a-custom-skill-as-an-aws-lambda-function.html) reference for a walkthrough on creating a AWS Lambda function with the correct role for your skill. When creating the function, select the “Author from scratch” option, and select the Java 8 runtime.


### Alexa Skill Setup
Please refer to [Developing Your First Skill](https://github.com/alexa/alexa-skills-kit-sdk-for-java/wiki/Developing-Your-First-Skill) for detailed instructions.
