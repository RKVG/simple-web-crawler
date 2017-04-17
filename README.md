Simple Web Crawler
==================

The Simple Web Crawler is designed to be lightweight, without cumbersome frameworks, but also architecturally flexible at the same time. It is separated into several layers with the idea that its function can be easily expanded in the future. For example, the crawler currently saves data into a HashSet, however, one can easily make it save its result into a database by providing another implementation of the same set of repository interfaces.

Assumptions
-----------
The Simple Web Crawler assumes that "reachable" web page may have `html` or `htm` file type which means that `jsp`, `php`, `asp` or other dynamically generated web pages are considered as assets rather than reachable links.

It also assumes that a user may want to crawl both secured and unsecured page under the same domain. For example, when a user enters `https://www.google.com/shopping` the Simple Web Crawler will crawl all links (absolute or relative) on that page with the same domain name `"www.google.com"` no matter if the link is using `http` or `https`.

Run Simple Web Crawler
----------------------
Before running the application, please make sure you have a working version of Java installed on your machine. For details on how to install Java please refer to [How do I install Java?](https://www.java.com/en/download/help/download_options.xml)

**1. Run with default configuration** 

In a console from where the jar file has been downloaded to
```
java -jar simple-web-crawler-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Type in a URL (**_a valid full URL, including `http://` or `https://`_**), press Enter to execute

**2. Run with customised configuration**

In a console from where the jar file has been downloaded to
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

You need to make sure you have installed Java 8+, Maven 3 and you are connected to an *up-to-date maven repository* OR *Internet*
* Under the root directory type `mvn clean package`
* Test coverage report can be found under _target/jacoco/site_
* The default logging level is set to 'DEBUG'. You can change it in _src/resources/simplelogger.properties_
