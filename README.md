# Database Project #
Authors: Jacob Jerris, Luke Sanchez, Nathan Smith

This project has included two .txt files, "Insert Statements.txt" and "create table statements.txt" these will give you the create statements for your tables and information to populate them with.

Ideally this program is designed for your Schema to be named "local" but if you are unable to make a schema named "local" you can Ctrl + F in your IDE or text editor of choice and find "local" in our program and "Find and replace all" instances of "local" with whatever the name of your schema is. Most instances of local 
will be in the "connections" and the SQL statements themselves.



One of the requirements to run our program is to include a .jar file that is put into the build path to allow the java program to connect to the mysql server on your local machine.
The jar has been included in this project and is called "mysql-connector-java-8.0.23.jar" once you set up the program in eclipse/IntelliJ/Command-Line you'll want to add it to the build path.

FOR ECLIPSE:
Right click on your project
Select Build Path
Click on Configure Build Path
Click on Libraries and select Add External JARs
Select the jar file from the required folder
Click and Apply and Ok

FOR INTELLIJ:
To import jar file in your Eclipse IDE, follow the steps given below.

Right click on your project
Select Build Path
Click on Configure Build Path
Click on Libraries and select Add External JARs
Select the jar file from the required folder
Click and Apply and OK

FOR COMMAND-LINE:
  ON LINUX:
    javac -cp .:/Jar_File_Path <prog_name>.java
    java -cp .:/Jar_File_Path <prog_name>
  ON WINDOWS:
    javac -cp ";:/Jar_File_Path" <prog_name>
    
Once that is complete the program should run and the user will be prompted for their username and password. This username and password is the user and password you use to log into your local MySql server. 

You will then be able to navigate around the program.
