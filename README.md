# WorkTime
This simple project had a goal to help me to get more acquainted with performing sql server database operations through java. 

The main purpose of the app is to save, once a day an information about what time app has been started. I always have troubles with remembering when I have started my job. Therefore, when app is added to autostart, it adds an applicable record. Record consits of Id(identity(1,1)), start_date and start_time.  

Project structure: 

![2022-11-14_08h52_37](https://user-images.githubusercontent.com/99602564/201604613-6cfd13a2-9c83-4c79-ac76-81fe2073c5ee.png)



Prerequisites:
- connection-data.txt file has to be filled with correct data
- sql user must have 'dbcreator' role 
- 3 ENV variables have to be added manually: <br/> 
ZZMUDZIO_SQL_BRW_NAME = [here name of sql browser service], <br/>
ZZMUDZIO_SQL_NAME = [here name of sql server instance], <br/>
ZZMUDZIO_SQL_CONN_DATA = [here the path to the sql-connection-data.txt file](the file is added to this repo, so just fill it with correct data and put it's path into environmental variable). These variables are necessary because application, when added to the autostart, during execution is waiting for services to start. 

First run: 

<code>java -cp .\target\workTime-jar-with-dependencies.jar pl.worktime.Main install</code> It is responsible for creating a database and a mandatory table. 

<hr>

<code>java -cp .\target\workTime-jar-with-dependencies.jar pl.worktime.Main insert</code> It is responsible for inserting a current time and date into a database. This command(with proper jar path) should be added to an autostart bat file. If the date is already present in a database, the applicable response will be displayed. 


<code>java -cp .\target\workTime-jar-with-dependencies.jar pl.worktime.Main </code> This command just displays a start_time for current day. 


