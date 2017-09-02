name := "facebook4s"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  val akka = "2.4.19"
  val akkaHttpV = "10.0.9"
  val scalaTestV = "3.0.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akka,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
  )
}

licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-Xlint",
  "-deprecation",
  "-Xfatal-warnings",
  "-feature",
  "-language:postfixOps",
  "-unchecked"
)

scalacOptions in ThisBuild ++= Seq("-language:postfixOps",
  "-language:implicitConversions",
  "-language:existentials",
  "-feature",
  "-deprecation")
