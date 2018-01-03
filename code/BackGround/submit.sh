scp /home/v/Project/Idea/Chat/code/BackGround/target/chat.war cloud_v:/usr/apache-tomcat-9.0.0.M17/webapps/chat.war

ssh cloud_v "/usr/apache-tomcat-9.0.0.M17/bin/shutdown.sh; /usr/apache-tomcat-9.0.0.M17/bin/startup.sh;
"
