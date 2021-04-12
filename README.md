# Java UDP Chat Application.

Java networking chat application. Facilitates communication between two clients through a server using the User Datagram Protocol, with checksum calculations for data integrity validation. Includes a simple Graphical User Interface made with Netbeans.

The application can be run through terminals: First download the the three classes: Server, Client1 and Client2. Then compile the classes by running the javac command on each of them. Then to run the classes do this in three different terminal windows and use the java command on them. i.e. java Server.java i.e. java Client1.java i.e. java Client2.java All in different terminal windows and begin communicating!

Unfortunately our GUI did not properly work. The GUI works for user authentication but not for displaying the messages being exchanged between the clients. Therefore, you can run the GUI by running the Home GUI first and proceeding to successfully do user authentication by using either of these two details: First: Username: client1 Password: one Second: Username: client2 Password: two However, after verifying the details, messages can be sent but they will not be displayed. Therefore, to check that our chat application works, it has to be run on three terminals.

Disclaimer for the makefile

When you build an Java application project that has a main class, the IDE automatically copies all of the JAR files on the projects classpath to your projects dist/lib folder. The IDE also adds each of the JAR files to the Class-Path element in the application JAR files manifest file (MANIFEST.MF).

To run the project from the command line, go to the dist folder and type the following:

java -jar "Assignment1.jar"

To distribute this project, zip up the dist folder (including the lib folder) and distribute the ZIP file.

Notes:

If two JAR files on the project classpath have the same name, only the first JAR file is copied to the lib folder.
Only JAR files are copied to the lib folder. If the classpath contains other types of files or folders, these files (folders) are not copied.
If a library on the projects classpath also has a Class-Path element specified in the manifest,the content of the Class-Path element has to be on the projects runtime path.
To set a main class in a standard Java project, right-click the project node in the Projects window and choose Properties. Then click Run and enter the class name in the Main Class field. Alternatively, you can manually type the class name in the manifest Main-Class element.
