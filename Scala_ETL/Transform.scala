
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col}
import Extract._
import toHDFS._

object Transform {

  val spark = SparkSession.builder()
      .appName("movies")
//      .master("local[*]")
      .master("yarn")
      .getOrCreate()

//  val file_path = os.Path(os.pwd + "/energie.csv")
//  val df = file_path_to_df(spark, file_path)
//val df = file_path_to_df(spark, os.Path("/scala/energie.csv"))

  def main(args: Array[String]) {
    //println("Exit code of extraction is:"+json_extraction() )
    //println(to_HDFSF(5))
    //union json to dataframe

    var games0 = spark.read.option("inferSchema","true").option("multiline","true").json("/scala/games/games_1.json")
    for( i <- 2 to 57){
      var games01 = spark.read.option("inferSchema","true").option("multiline","true").json(s"/scala/games/games_${i}.json")
      games0 = games0.union(games01)
    }

    var stats0 = spark.read.option("inferSchema","true").option("multiline","true").json("/scala/stats/stats_1.json")
    for( i <- 2 to 100){
      var stats01 = spark.read.option("inferSchema","true").option("multiline","true").json(s"/scala/stats/stats_${i}.json")
      stats0 = stats0.union(stats01)
      println(i)
    }
  //game preprocessing
  games0.printSchema

  val games1 = games0.select(col("home_team_score"), col("id").as("id_match"), col("home_team.id").as("id_equipe"), col("home_team.name").as("nom_equipe") )

  val equipes = List("Suns", "Hawks", "Lakers", "Bucks")
  val games2 = games1.filter(col("nom_equipe").isin(equipes: _*))
  //stats preprocessing
  val stats2 = stats0.select(col("game.id").as("id_matchS"), col("team.id").as("id_equipeS"), col("pts"), col("reb"), col("ast"), col("blk"))
  //left join to keep all lines of games2 because stats is not comlete
  val df = games2.join(stats2, games2("id_match")===stats2("id_matchS") && games2("id_equipe")===stats2("id_equipeS"), "left_outer").drop("id_matchS").drop("id_equipeS")
  //write to hdfs, not to master
  //file in many parts without coalesce(1)
  df.coalesce(1).write.csv("/data/scala/output1.csv")
  df.count()
  df.show(3)

  }
}