name := """stock-query-server"""
organization := "com.pzque"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.12"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "org.apache.hbase" % "hbase-server" % "2.0.3",
  "org.apache.hbase" % "hbase-client" % "2.0.3",
  "org.apache.hbase" % "hbase-common" % "2.0.3",
  "org.apache.hadoop" % "hadoop-common" % "2.7.7"
)

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.5.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.pzque.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.pzque.binders._"
