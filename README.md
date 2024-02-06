# FYP-API
A system that processes CRUD requests from the client side.

## Installation
The current build requires JDK 17 to compile. Run ```java -version``` to check.    

If your current JDK version is not 17, do the followings:

1. Install [JDK 17]([https://www.example.com](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. Add system envrionment variable JAVA_HOME to the directory of JDK 17, eg: C:\Program Files\Java\jdk-17

3. Run ```java -version``` on a **newly opened** command prompt
   
   The result should be like: ```java version "17.X.X" 2023-01-17 LTS```. If not, change the PATH variable as well:
   > ADD the directory of JDK 17, eg: C:\Program Files\Java\jdk-17, and move it above the default java path, eg: C:\ProgramData\Oracle\Java\javapath

   If that doesn't work, try:
  
   > Replace the default java path, eg: C:\ProgramData\Oracle\Java\javapath, with the directory of JDK 17, eg: C:\Program Files\Java\jdk-17

5. Done! You can now start the api server
