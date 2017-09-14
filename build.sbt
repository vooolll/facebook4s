name := "facebook4s"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  val akkaV = "2.4.19"
  val playV = "2.6.3"
  val akkaHttpV = "10.0.9"
  val scalaTestV = "3.0.1"
  val typesafeV = "1.3.1"
  val mockitoV = "1.8.5"
  val catsV = "1.0.0-MF"
  Seq(
    "org.typelevel" %% "cats-core" % catsV,
    "com.typesafe" % "config" % typesafeV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.play" %% "play-json" % playV,
    "de.heikoseeberger" %% "akka-http-play-json" % "1.18.0",
    "org.f100ded.scala-url-builder" %% "scala-url-builder" % "0.9.0",
    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "org.mockito" % "mockito-core" % mockitoV % "test"
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

fork := true

javaOptions in Test ++= Seq(sys.env.getOrElse("TRAVIS_OPTION", "-Dconfig.file=src/test/resources/.facebook-dev.conf"))

scalacOptions in ThisBuild ++= Seq("-language:postfixOps",
  "-language:implicitConversions",
  "-language:existentials",
  "-feature",
  "-deprecation")
