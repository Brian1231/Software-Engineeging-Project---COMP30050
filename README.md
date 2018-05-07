# Software Engineering Project - COMP30050
Andrew Fitzgerald - 12376456<br />
Brian O’Leary - 13475468<br />
Conal O’Neill - 13315756<br />
Jose Collado San Pedro - 17202360<br />
<br /><br />

## How to run our project
Our project consists of 3 applications. A server application, a desktop application and an android application. Builds of these applications may be found in the Builds folder of this repository<br />
To run our project:
* First run Server.jar. This application was made to run on an AWS instance but may also be run locally.
* Next run Desktop.jar. This application displays the graphics of our project. In the desktop application selecting New Game will connect
to our server application running on an AWS instance. Selecting Local Game will allow you to input the IP address of the Server.jar thats's being run locally.
* Once the desktop application is connected, the android app may be connected. This is connected similarly to the desktop application.
Choose local server on the app and enter the IP address of the machine running Server.jar. Once this is entered, select Join Game to enter the game.
* To begin the game, select Start Game on the Desktop application.

## How to access our AWS instance for running Server.jar remotely
### On MAC
Our AWS instance can be accessed by using the aws.sh file in the root folder of this repo. This shell script will cd into the server-aws folder, set the correct permisson for the server.pem file and then SSH into the AWS instance using the server.pem file in the server-AWS folder.

### On Windows
Our AWS instance can be accessed by using the application PuTTY. This is a SSH client for windows and may be downloaded from www.putty.org/. When connecting with PuTTY, the Host name is ubuntu@ec2-52-48-249-220.eu-west-1.compute.amazonaws.com. The path to serverKPPuTTY.ppk in the folder server-AWS must also be added to the Connection/SSH/Auth/Authentication Parameters in PuTTY. Once these parameters are set the AWS instance may be accessed by selecting 'Open'.
<br /><br />
Once the AWS instance is accessed, our server application may be ran by the command 'java -jar Server.jar'.
