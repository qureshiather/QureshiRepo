# Welcome to Fred's Fun Fitness Facts For Friends!!!

Team consisted of 2nd Year CompSci Students:
Skeni Atul Patel, Henry Pun, Ather Qureshi, Linxue Ren, David Alexander Tasker, An Qi Xu

Before you get started, please ensure Git is set up and Maven has been updated to the latest version (3.3.9)

## Synopsis

Fred's Fun Fitness Facts For Friends (FFFFFF) provides users with an organized way of viewing their fitness data. 
It includes features such as tip of the day, accolades, and much more.

The project was implement using the Fitbit API (https://dev.fitbit.com/docs/) and used the com.toedter.jcalendar
library for date implementation and com.itextpdf.itextpdf for exporting pages to PDF.

## Build

Clone our repository onto your local system:

```
git clone ssh://git@repo.gaul.csd.uwo.ca:7999/cs2212_w2016/team02.git
```

Use maven to compile and build the application
```
mvn package
```

Run the application using:
```
java -jar target/team02-FFFFFF-1.0-SNAPSHOT-jar-with-dependencies.jar
```

To run the application using a test case use:
```
java -jar target/team02-FFFFFF-1.0-SNAPSHOT-jar-with-dependencies.jar -test
```

## Usage Example

View a demonstration of FFFFFF here: 
```
https://www.youtube.com/watch?v=ThZUufAa35c&feature=youtu.be
``` 

## Documentation

Please see the /doc/ folder for the FFFFFF JavaDoc.
