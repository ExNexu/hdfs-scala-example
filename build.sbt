name := "hdfs-scala-example"

organization := "bleibinhaus"

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % "1.2.1",
  "org.scalatest" % "scalatest_2.10" % "1.9.2" % "test"
)

initialCommands := "import bleibinhaus.hdfsscalaexample._"

