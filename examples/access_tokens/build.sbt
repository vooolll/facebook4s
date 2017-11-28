name := "access_tokens"

version := "0.0.1"

scalaVersion := "2.12.1"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "com.github.vooolll" %% "facebook4s" % "0.2.0-SNAPSHOT"
)