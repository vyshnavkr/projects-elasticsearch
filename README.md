# projects-elasticsearch
This repository contains several projects on ELK stack

//create mysql database
CREATE DATABASE elasticsearch;

2019-02-22 09:00:26
//create mysql table
USE elasticsearch;
CREATE TABLE user(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), bio VARCHAR(100), father_name VARCHAR(20), creation_date_time datetime);


//insert dummy values into mysql table
INSERT INTO user (name, bio, father_name, creation_date_time) VALUES ("Vyshnav", "I am software engineer. I work on Java, Spring, AWS, ELK.", "Ramesh", '2019-02-22 09:00:26');
INSERT INTO user (name, bio, father_name, creation_date_time) VALUES ("Vysakh", "I am a data scientist. I work on machine learning, deep learning and Artificial Intelligence", "Ramesh", '2019-02-22 09:00:27');
INSERT INTO user (name, bio, father_name, creation_date_time) VALUES ("Vignesh", "I am a student. I work on nothing", "Ramesh", '2019-02-22 10:00:27');


//configure logstash
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


//run elasticsearch


//run logstash using -> bin\logstash -f logstash-config-project.conf


//code backend in springboot

	//pom
	new maven project -> choose 'create a simple project'
	pom -> parent, dependencies, properties, build
	right click -> maven -> update

	//application.properties
	look at code
	
	//model-controller-service-repository
	look at code
