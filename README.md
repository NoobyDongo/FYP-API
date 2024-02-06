# FYP-API
A system that processes CRUD requests from the client side.

## Installation
The current build requires JDK 17 to compile. Run ```java -version``` to check.    

Make sure of the followings before you run the server:

- change the `sqlpath.txt` to the directory containing the `mysql_start.bat` and `mysql_stop.bat`
- make sure you have a MySQL database named accordingly to the Spring config in `FYP-API\main\src\main\resources\application.properties`

If your current JDK version is not 17, do the followings:

1. Install [JDK 17]([https://www.example.com](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. Add system envrionment variable JAVA_HOME to the directory of JDK 17, eg: C:\Program Files\Java\jdk-17

3. Run ```java -version``` on a **newly opened** command prompt
   
   The result should be like: ```java version "17.X.X" 2023-01-17 LTS```. If not, change the PATH variable as well:
   > Add the directory of JDK 17, eg: C:\Program Files\Java\jdk-17, and move it above the default java path, eg: C:\ProgramData\Oracle\Java\javapath

   If that doesn't work, try:
  
   > Replacing the default java path, eg: C:\ProgramData\Oracle\Java\javapath, with the directory of JDK 17, eg: C:\Program Files\Java\jdk-17

4. Done! You can now start the api serve
