package com.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;

public class Main {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("SparkApp")
                .getOrCreate();

        String path = "C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Spark\\movie_metadata.csv";
        Dataset<Row> df = spark.read().option("header", "true").csv(path).limit(1000000 / 28).repartition(10);
        df
                .groupBy("director_name")
                .count()
                .withColumnRenamed("count", "count_in_group")
                .filter("director_name is not NULL")
                .write()
                .option("header", true)
                .mode(SaveMode.Overwrite)
                .csv("C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Spark\\result");
















//        df
//                .groupBy("director_name")
//                .count()
//                .filter("director_name is not NULL")
//                .write()
//                .option("header",true)
//                .csv("C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Spark\\result");

//        int oneMillionLimit = (int) (1000000 / Arrays.stream(df.columns()).count());
//        JavaSparkContext sparkContext = new JavaSparkContext(spark.sparkContext());
//        JavaRDD<String> rddFromFile = spark.sparkContext().textFile("C:/tmp/files/text01.txt")
        //String path = new File("movie_metadata.csv").getAbsolutePath();
//        Dataset<Row> df = spark.read().option("header", "true").csv(path);
        //int oneMillionLimit = (int) (1000000 / Arrays.stream(df.columns()).count());
        //Dataset<String> ds = spark.createDataset(df, Encoders.STRING());
//        df
//                .groupBy("director_name")
//                .count()
//                .filter("director_name is not NULL")
//                .javaRDD();
        //JavaRDD<String> sr = spark.read().csv(path);
//                .write()
//                .text("C:/Users/Ivan_Pylypiv/IdeaProjects/Library/Spark/result.txt");


//                .write()
//                .option("header",true)
//                .csv("C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Spark/result");

//                .write()
//                .format("csv")
//                .mode(SaveMode.Overwrite)
//                .option("header", true)
//                .save("/result");

        //        SparkConf sparkConf = new SparkConf()
//                .setMaster("local")
//                .setAppName("SparkApp");
//
//        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
//
//        JavaRDD<String> javaRDD = javaSparkContext.textFile("C:\\Users\\Ivan_Pylypiv\\IdeaProjects\\Library\\Spark\\movie_metadata.csv");

    }
}