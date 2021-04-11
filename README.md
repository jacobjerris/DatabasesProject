# Database Project #
Authors: Jacob Jerris, Luke Sanchez, Nathan Smith

This project has included two .txt files, "Insert Statements.txt" and "create table statements.txt" these will give you the create statements for your tables and information to populate them with. Another thing is when you go to the INSERT statements document, you need to replace all of the schema names "group2" to the name of your schema. The best way will be to find and replace all using Ctrl + F.

//modify these to change sql connection accross program

username = "root";

password = "root";

forNameSTR = "com.mysql.cj.jdbc.Driver";

connectionSTR = "root";

schemaName = "local";



One of the requirements to run our program is to include a .jar file that is put into the build path to allow the java program to connect to the mysql server on your local machine.
The jar has been included in this project and is called "mysql-connector-java-8.0.23.jar" once you set up the program in eclipse/IntelliJ/Command-Line you'll want to add it to the build path.

FOR ECLIPSE:
Right click on your project
Select Build Path
Click on Configure Build Path
Click on Libraries 
Click on Module Path and select Add External JARs
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
    

You will then be able to navigate around the program.
