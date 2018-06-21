name := "access_tokens"

version := "0.0.1"

scalaVersion := "2.12.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= {
  val facebook4sVersion = "0.2.9"

  Seq(
    "com.github.vooolll" %% "facebook4s" % version
  )
}