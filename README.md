#####################################################
README for Multi-Source Solr Search
#####################################################

#############
Requirements
#############

1. 	Solr 4.5.1
2. 	Three cores configured, named:
		a) Wikipedia
		b) Wikinews
		c) Wikivoyage

3. 	MySql 5.5
4. 	Apache Tomcat 7
5. 	Apache Httpd 2.2
6. 	PHP 5
7. 	Python 2.7
8. 	Java 1.7
9. 	Javascript enabled browser
10.	Eclipse


#######################
Installation
#######################


1.	Download the wikipedia dumps from the site:
	
http://dumps.wikimedia.org/backup-index.html

2.	Download three COMPLETE dumps for Wikivoyage, Wikinews and Wikipedia. (Download:'All pages, current versions only')
3.	Copy the following files in the same directory as the dumps:
		a) code/processor/wikivoyage.py
		b) code/processor/wikipedia.py
		c) code/processor/wikinews.py
		d) code/processor/avglength.py
		e) code/processor/places.txt

You need to make google map account on " https://code.google.com/apis/console/?noredirect&pli=1#project:359121677264 "

4.	Open the python scripts using your editor and specify the exact name of your dump.
5.	Run wikivoyage.py, wikinews.py and  wikipedia.py scripts.
6.	Scripts will produce xml files which have documents related to countries and major cities.
7.	Run the avglength.py to get stats about your dump. You can use these stats to remove small documents by filtering by median or average length.
8.	Open the code/solr/SolrMultiCore.zip in eclipse. Jar file also exists but won't run as the properties.config file cannot be packaged within jar. 
9.	Import into new project.
10.	Change the files/properties.config file in Eclipse to show the path of the XML files.
11.	Install Solr webapp in Apache Tomcat running on port 8080.
12.	In your Solr cores /conf directory add the following files:
		a) code/processor/schema.xml
		b) code/processor/solrconfig.xml
		c) code/processor/places.txt
		d) code/processor/querysynonyms.txt
		e) code/processor/stopwords.txt
		f) code/processor/synonyms.txt

13.	If prompted to replace the existing file. Replace them with theses.
14.	Modify the schema.xml to contain the name of the core.
15.	Do this for all three cores.
16.	Run the Java code.
17.	Documents are now indexed.
18.	Place the following files in root directory of your Apache Httpd server running on port 80.
		a) code/processor/phpsqlajax_dbinfo.php (specify your MySql password here)
		b) code/processor/phpsqlajax_genxml.php
		c) code/processor/phpsqlajax_map_v3.html

19.	Place the code/processor/SearchEngine Folder in Apache Tomcat directory.
20.	Deploy the SearchEngine webapp in Apache Tomcat.
21.	In MySql create GoogleMaps database.
22.	Create markers table as specified.
	
	mysql> create table `markers` (
	    -> `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            -> `lat` FLOAT(10, 6) NOT NULL,
            -> `lng` FLOAT(10, 6) NOT NULL,
            -> `type` VARCHAR(15) NOT NULL
	    -> ) ENGINE = MYISAM;

	a) to use database type :
		mysql > Use [databasename]
	b) to load 'city&country' file in databse type:
		LOAD DATA INFILE "cityList.csv" INTO TABLE {TABLENAME} LOCATIONS COLUMNS TERMINATED BY ',' LINES TERMINATED BY '\n';

23.	Import the city&country.csv file into markers table in MySql.

24. 	Paste all .phy file for map in director /var/lib/mysql/..
25. 	Open browser and type the following address.

http://localhost:8080/SearchEngine/finalcopy/index.html

25.	If installation is successful you will be greeted by the multi-source search page. 
