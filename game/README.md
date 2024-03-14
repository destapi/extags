# Creating new H2 DB database

1. Make sure you download the h2-<version>.jar file that matches your database version.
2. open a H2 DB shell and follow the prompts

```bash
$ java -cp app/.bin/h2-2.2.224.jar org.h2.tools.Shell

Welcome to H2 Shell 2.2.224 (2023-09-17)
Exit with Ctrl+C
[Enter]   jdbc:h2:tcp://localhost/~/data/mydb
URL       jdbc:h2:~/.data/app-db            
[Enter]   org.h2.Driver
Driver
[Enter]   sa
User
Password  
Type the same password again to confirm database creation.
Password
Connected
Commands are case insensitive; SQL statements end with ';'
help or ?      Display this help
list           Toggle result list / stack trace mode
maxwidth       Set maximum column width (default is 100)
autocommit     Enable or disable autocommit
history        Show the last 20 statements
quit or exit   Close the connection and exit

sql> exit
Connection closed
```

3. Connecting to a database using the file protocol locks the database to the thread that first established contact, and any other 
attempts to connect to the same database will produce an error. For this reason, it's better to start a TCP server which will connect 
to the database and then allow multiple clients to access the database concurrently.

> View start options for db server
```bash
java -cp game/.bin/h2-2.2.224.jar org.h2.tools.Server -?
```

> Start a db server instance  
> When running without options, -tcp, -web, -browser and -pg are started.
```bash
java -cp game/.bin/h2-2.2.224.jar org.h2.tools.Server
```

4. Run your sql script in the web console which opens up

