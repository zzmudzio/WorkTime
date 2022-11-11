# WorkTime
This simple project had a goal to help me to get more acquainted with performing sql server database operations through java. 

The main purpose of the app is to save, once a day an information about what time app has been started. I always have troubles with remembering when I have started my job. Therefore, when app is added to autostart, it adds an applicable record. Record consits of Id(identity(1,1)), start_date and start_time.  

Project structure: 

![2022-11-10_09h05_48](https://user-images.githubusercontent.com/99602564/201035206-998e0cdd-aa1c-47d0-922e-a965bef0be8d.png)

Prerequisites:
- connection-data.txt file has to be filled with correct data
- sql user must have 'dbcreator' role 
- 3 ENV variables have to be added manually: ZZMUDZIO_SQL_BRW_NAME = [here name of sql browser service], ZZMUDZIO_SQL_NAME = [here name of sql server instance],  
ZZMUDZIO_SQL_CONN_DATA = [here the path to the sql-connection-data.txt file](the file is added to this repo, so just fill it with correct data and put it's path into environmental variable). These variables are necessary because application, when added to the autostart, during execution is waiting for services to start. 

First run: 

<code>java -cp .\target\workTime-jar-with-dependencies.jar pl.worktime.Main install</code> It is responsible for creating a database and a mandatory table. 

<hr>

<code>java -cp .\target\workTime-jar-with-dependencies.jar pl.worktime.Main insert</code> It is responsible for inserting a current time and date into a database. This command(with proper jar path) should be added to an autostart bat file. If the date is already present in a database, the applicable response will be displayed. 


<code>java -cp .\target\workTime-jar-with-dependencies.jar pl.worktime.Main </code> This command just displays a start_time for current day. 


