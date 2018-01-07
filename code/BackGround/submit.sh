scp /home/v/Project/Idea/Chat/code/BackGround/target/chat.war cloud_v:/home/v/apache-tomcat-9.0.0.M22/webapps/chat.war

ssh cloud_v "/home/v/apache-tomcat-9.0.0.M22/bin/shutdown.sh; /home/v/apache-tomcat-9.0.0.M22/bin/startup.sh;
"
