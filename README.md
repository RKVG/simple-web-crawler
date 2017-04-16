Simple Web Crawler
==================

Write something here?

**Run with default configuration**
```
java -jar simple-web-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar
```

**Run with customised configuration**
```
java -Dconfig=my.properties -jar simple-web-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar
```

And make sure your property file has settings similar to the following:
```
app.request.timeout=5000
app.follow-redirect=true
```