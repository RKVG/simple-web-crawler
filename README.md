Simple Web Crawler
==================

Write something here?

Run Simple Web Crawler
----------------------

**1. Run with default configuration** 

In a console from where the jar file has been saved to
```
java -jar simple-web-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Type in a URL (**_a valid full URL, including `http://` or `https://`_**), press Enter to execute

**2. Run with customised configuration**

In a console from where the jar file has been saved to
```
java -Dconfig=my.properties -jar simple-web-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar
```

And make sure your property file has settings similar to the following:
```
crawler.request.timeout=5000
crawler.follow.redirect=true
crawler.max.visited.urls=2000
printer.exclude.errors=true
printer.report.errors=true
```
Type in a URL (**_a valid full URL, including `http://` or `https://`_**), press Enter to execute 

**Available configurations** 

| Name                         | Type    | Description |
|:-----------------------------|:-------:|:------------|
| crawler.request.timeout      | integer | HTTP connection timeout. The default is **5000** (5 seconds) |
| crawler.follow.redirect      | boolean | If the crawler needs to follow a redirect. The default is **true**. Please note that there can be a significant number of 301 errors if this is set to false |
| crawler.max.visited.urls     | integer | Maximum number of URLs to visit. The crawler will exit if it has visited more URLs than this set limit. The default is **2000** |
| printer.exclude.errors       | boolean | If the printer needs to exclude pages that are in error. The default is **true** |
| printer.report.errors        | boolean | If the printer needs to print an error report. The default is **true** |

Build Simple Web Crawler
------------------------

You need to make sure you have install Java 8+, Maven 3 and you are connected to an up-to-date maven repository OR internet
* Under the root directory type `mvn clean install`
* Test coverage report can be found under _target/jacoco/site_
* The default logging level is set to 'DEBUG'. You can change it in _src/resources/simplelogger.properties_
