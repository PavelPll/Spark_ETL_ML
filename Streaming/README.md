# OBJECTIVE: Create and Execute Spark Streaming pipeline (data batch processing) on Spark cluster.
For technical side how to run the code please see the file How_to_run.txt.
The project contains the following steps:
* Generation of artificial continuous flow of data (./data_flow). Two options are available (a) or (b): 
(a) Generate dataflow on master using pandas(python) together with its periodical transfer to HDFS.
(b) Generate data flow directly on Spark cluster in the format of HDFS using pyspark(python). 
* Reading data in batches in real time, preprocessing, continuous output batches on screen (./src/main/streamApp.scala). 
* Insert data into a table of a SQL database in real time (./src/main/toSQLstreaming.scala and ./SQL_server_installation.txt). 