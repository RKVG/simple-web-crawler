Simple Web Crawler
==================

The Simple Web Crawler is designed to be lightweight, without cumbersome frameworks, and structurally flexible at the same time. It contains several layers with the idea that every layer is loosely coupled with each other so that new functions can be easily added in the future. For example, the crawler currently saves into a HashSet, however, one can easily make it save into a database by providing another implementation of repository interfaces.

Assumptions
-----------
The Simple Web Crawler assumes that a "reachable" web page may have `html` or `htm` file type. This means that `jsp`, `php`, `asp` or other dynamically generated web pages are considered as assets rather than reachable links.

It also assumes that a user may want to crawl both secured and unsecured page under the same domain. For example, when a user enters `https://www.google.com/shopping` the Simple Web Crawler will crawl all links (absolute or relative) on that page with the same domain name `"www.google.com"` no matter if the link contains `http` or `https`.

Run Simple Web Crawler
----------------------
Before running the application, please make sure that you have installed Java 8 on your machine (`java -version`). For details on how to install Java please refer to [How do I install Java?](https://www.java.com/en/download/help/download_options.xml)

**1. Run with default configuration** 

In a console, from the directory with the runnable jar file
```
java -jar simple-web-crawler.jar
```
Type in a URL (**_a valid full URL, including `http://` or `https://`_**), press Enter to execute

**2. Run with customised configuration**

In a console, from the directory with the runnable jar file
```
java -Dconfig=my.properties -jar simple-web-crawler.jar
```

And make sure your property file has settings similar to the following:
```
crawler.mock.user.agent=${your_mock_user_agent_string}
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
| crawler.mock.user.agent      | string  | The UserAgent header to send with each request. It is recommended to set this value because a site might return different responses based on UserAgent header. With this said however, you can always set an empty header by including this property with an empty value. When omitted completely the default value `Mac OS X 10_12_4, Chrome build 57.0.2987.133` will be used. For a list of valid UserAgent values please refer to [UserAgentString.Com](http://www.useragentstring.com/pages/useragentstring.php) |
| crawler.request.timeout      | integer | HTTP connection timeout. The default is **5000** (5 seconds) |
| crawler.follow.redirect      | boolean | Whether the crawler should follow a redirect. The default is **true**. Please note that there can be a significant number of 301 errors if this is set to false |
| crawler.max.visited.urls     | integer | Maximum number of URLs to visit. The crawler will exit when the number of visited URLs has reached this limit. The default is **2000** |
| printer.exclude.errors       | boolean | Whether the printer should exclude pages that are in error. The default is **true** |
| printer.report.errors        | boolean | Whether the printer should print an error report. The default is **true** |

Build Simple Web Crawler
------------------------

You need to make sure that you have installed Java 8, Maven 3 and you are connected to an _**up-to-date** maven repository_ OR _the Internet_
* Under the root directory type `mvn clean package`
* Test coverage report can be found under _target/jacoco/site_
* The default logging level is set to 'DEBUG'. You can change it in _src/resources/simplelogger.properties_
