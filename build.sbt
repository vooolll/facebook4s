name := "spracebook"

scalaVersion := "2.11.11"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  val akka = "2.5.4"
  val akkaHttpV = "10.0.9"
  val scalaTestV = "3.0.1"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % akka,
    "com.typesafe.akka" % "akka-actor_2.11" % akka,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "org.scalatest" % "scalatest_2.11" % scalaTestV % "test"
  )
}

licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))