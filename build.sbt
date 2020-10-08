name := "AkkaProgramming"

version := "0.1"
val akkaVersion = "2.6.9"
val akkaHttpVersion = "10.2.1"
val argonautVersion = "6.3.1"
scalaVersion := "2.13.3"
libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  "com.twitter" %% "finagle-netty4-http" % "20.8.1",
  "com.twitter" %% "finagle-base-http" % "20.8.1",
  "com.twitter" %% "finagle-http" % "20.8.1",
  "com.typesafe.play" %% "play-json" % "2.9.1",
  "io.argonaut" %% "argonaut" % argonautVersion,
  "io.argonaut" %% "argonaut" % argonautVersion,
  "io.argonaut" %% "argonaut-scalaz" % argonautVersion,
  "io.argonaut" %% "argonaut-monocle" % argonautVersion,
  "io.argonaut" %% "argonaut-cats" % argonautVersion,
  "io.lemonlabs" %% "scala-uri" % "2.3.1",
  "org.apache.httpcomponents" % "httpclient" % "4.5.12",
  "org.json4s" %% "json4s-jackson" % "3.7.0-M6",
  "org.scalaz" %% "scalaz-core" % "7.4.0-M3",
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)

