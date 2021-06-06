name := """Bauernmilch3"""
organization := "htw"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)


herokuConfigVars in Compile := Map(
  "DATABASE_URL" -> "postgres://cyqiamooqdlrqg:cb955604f32d7ddb2226a70f106fd9cb7c96c663144b5d10fd9594fd960c73bf@ec2-54-160-96-70.compute-1.amazonaws.com:5432/dbkcetqh1hoj6g",
)


herokuJdkVersion in Compile := "14"
herokuAppName in Compile := "justmilk"

scalaVersion := "2.13.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.6"
//von playDoku
//libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0"


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
)

//libraryDependencies ++= Seq(
//  "org.pac4j" %% "play-pac4j" % "11.0.0-PLAY2.8",
//  "org.pac4j" %% "pac4j-oidc" % "5.0.0"
//)





// Adds additional packages into Twirl
//TwirlKeys.templateImports += "htw.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "htw.binders._"
