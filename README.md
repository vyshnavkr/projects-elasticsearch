# projects-elasticsearch
This repository contains several projects on ELK stack

Step 0. Download and install Elasticsearch, Logstash and Kibana from their respective sites. <br/>
  (Note: Make sure the directory in which these are installed contains no white space. eg: C://Program<white space>             Files)
	
PART 1: Real Time Syncing between Mysql and Elasticsearch

Step 1. Create Mysql database:
```	
	CREATE DATABASE elasticsearch;
```

2. Create Mysql table:
```
	USE elasticsearch;
	CREATE TABLE user(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), bio VARCHAR(100), father_name VARCHAR(20), creation_date_time datetime);
```


Step 3. Insert dummy values into Mysql table:
```
	INSERT INTO user (name, bio, father_name, creation_date_time) VALUES ("Vyshnav", "I am software engineer. I work on Java, Spring, AWS, ELK.", "Ramesh", '2019-02-22 09:00:26');
	INSERT INTO user (name, bio, father_name, creation_date_time) VALUES ("Vysakh", "I am a data scientist. I work on machine learning, deep learning and Artificial Intelligence", "Ramesh", '2019-02-22 09:00:27');
	INSERT INTO user (name, bio, father_name, creation_date_time) VALUES ("Vignesh", "I am a student. I work on nothing", "Ramesh", '2019-02-22 10:00:27');
```

Step 4. Configure Logstash: <br/>
  Create a file called 'logstash-config-project.conf' with the below code. Save it inside logstash installation folder.

```
	input {
		jdbc {
			jdbc_driver_library => "C:\Users\Vyshnav\Downloads\jars\mysql-connector-java-8.0.11.jar"
			jdbc_driver_class => "com.mysql.jdbc.Driver"
			jdbc_connection_string => "jdbc:mysql://localhost:3306/elasticsearch"
			jdbc_user => "root"
			jdbc_password => "root"
			statement => "select * from user where creation_date_time>:sql_last_value"
			use_column_value => true
			tracking_column => "creation_date_time"
			tracking_column_type => "timestamp"
			schedule => "* * * * * *"
		}
	}
	output {
		elasticsearch {
			hosts => "localhost:9200"
			index => "userexample_index"
			document_type => "userexample"
			document_id => "%{id}"
			user => "elastic"
			password => "changeme"
		}
		stdout {
			codec => rubydebug
		}
	}
```

Step 5. Run Elasticsearch server:<br/>
  Run the 'elasticsearch.bat' present inside 'bin' folder of elasticsearch installation folder.


Step 6. Run Logstash server: <br/>
  Open cmd from the parent of 'bin' folder of logstash installation folder. Type the following and press ENTER:
```	
	bin\logstash -f logstash-config-project.conf
```


PART 2: Searching data via SpringBoot-Postman or Kibana

Step 7. Code backend in Spring Boot:
```
	//Maven project
	Create new maven project 
	Choose 'create a simple project'
	
	//pom
	look at source code
	(right click -> maven -> update)

	//application.properties
	look at source code
	
	//model-controller-service-repository
	look at source code
	
	//run microservice
	in cmd type 'mvn spring-boot:run' and press enter
```	
	
Step 8. Check APIs in Postman 

Step 9. Run Kibana server in http://localhost:5601 and use the following queries:
```
	//kibana queries (copy paste all of them together under 'developer tools' section)
	
	GET _search
	{
	  "from" : 0, "size" : 100,
	  "query": {
	    "match_all": {}
	  }
	}
	
	GET userexample_index/userexample/_search
	{
	  "from" : 0, "size" : 100,
	  "query": {
	    "match_all": {}
	  }
	}


	GET userexample_index/userexample/_search
	{
	  "from" : 0, "size" : 100,
	  "query": {
	    "match": {
	      "bio":{
		"query": "artfcial intellgnc",
		"fuzziness": "2"
	      }
	    }
	  }
	}
```
