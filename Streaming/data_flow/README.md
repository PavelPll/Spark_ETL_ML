# OBJECTIVE: Generation of artificial continuous flow of data
Two alternative options are available: 
* Generate dataflow on master using pandas(python) together with its periodical transfer to HDFS. RUN: **python data_flow.py**
* Generate data flow directly on Spark cluster in HDFS format using pyspark(python). RUN: **python data_flow_with_pyspark.py**
> [!NOTE]
> For technical side how to run the code please see the file **How_to_run_python.txt**.               
> The data file (**./Automobile_price_data_Raw_.csv**) I took from [here](https://github.com/MicrosoftLearning/Principles-of-Machine-Learning-Python/blob/master/Module7/Automobile%20price%20data%20_Raw_.csv).

