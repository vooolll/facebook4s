name := "spracebook"

scalaVersion := "2.11.11"

resolvers ++= Seq(
  "Spray" at "http://repo.spray.io/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  val spray = "1.0-M7"
  val akka = "2.5.4"
  Seq(
    "io.spray" % "spray-client" % spray,
    "io.spray" % "spray-json_2.9.2" % "1.2.3",
    "com.typesafe.akka" % "akka-actor_2.11" % akka,
    "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
  )
}

licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))