Web Crawler - Danut Rusu

-------------------------------------------------------------------------------------------
There are 2 ways to run the program:

1. Using a java IDE (e.g.: Intellij)  

	Import the maven project and simply run the program. 
The program uses the args to take the website to crawl, therefore please setup the argument: "http://www.gocardless.com" - for example.
In Intellij you can do this by going to: `Run` -> `Edit configurations` -> In the `Program arguments` field type the website you want to crawl.

2. Using the terminal  

In this case run as follows:  
 `mvn compile`  
 `mvn exec:java -Dexec.mainClass="Crawler" -Dexec.args="http://www.gocardless.com/"`
	
Crawler is the main class and takes as argument the website to crawl.

I ran the program with maven 3.3.9 and openjdk version 1.8.0_121

-------------------------------------------------------------------------------------------

For tests is the same.

Either run the tests individually from the IDE, or using the command line.

For the command line please use:  
`mvn -Dtest=SpiderTest test`  
`mvn -Dtest=WebCrawlerTest test`


