name := "access_tokens"

version := "0.0.1"

scalaVersion := "2.12.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "com.github.vooolll" %% "facebook4s" % "0.1.4"
)