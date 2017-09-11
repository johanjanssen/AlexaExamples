This is a simple Alexa skill.

There are two ways to deploy this skill to AWS Lambda:

1. Manual
Run the create.bat and deploy the skill by uploading the resulting jar file in the AWS Lambda web interface.

2. Automatic
Fill in the Access Key and Secret Key in the deploy.bat and run deploy.bat.


- Role
The lambda user needs to assume a role when it executes. 

If you don't have a role yet go to https://aws.amazon.com and open the IAM part.
Click on 'Roles' and then click on 'Create New Role'.
Give the role a name and select 'AWS Lambda' as the Role Type.
Just continue until you see the 'Role ARN' and enter that one in the deploy.bat.

- S3
S3 is Amazon service where you can store things. The easiest way to deploy artifacts to AWS Lambda is to store them first on S3.

If you don't have a S3 bucket yet go to https://aws.amazon.com and open the S3 part.
Click on 'Create bucket' and follow the steps. Enter the bucket name in the deploy.bat.

- Access Key and Secret Key
These are used to sign requests send to AWS. You need these if you want to programmaticaly communicate with AWS instead of via the web interface 

If you don't have the keys go to https://aws.amazon.com. Click on your name in the top right and then click 'My Security Credentials'.
Select 'Access Keys' and click on 'Create New Access Key'. Enter the keys in the deploy.bat.

Be careful with these keys. If somebody else retrieves them then they can do lots of damage in your AWS account. 
You might consider generating these keys for a user in your AWS account that has less access rights than the default user.

- Arguments Lambda Maven Plugin
Some arguments are stored in the pom.xml, others in the deploy.bat. It's possible to change that, but be careful not to publicize any personal (security) information.

- Security
The current code in AWS Lambda is accessible for everyone. It's possible to make it only available for your specific skill by using the code below. 
Lookup the application id on https://developer.amazon.com/ and enter it in the code.

public final class MyFirstSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
	supportedApplicationIds.add("amzn1.echo-sdk-ams.app.[    see https://developer.amazon.com/       ]");
    }

    public TestSpeechletRequestStreamHandler() {

        super(new MyFirstSpeechlet(), supportedApplicationIds);
    }
} 