name := "access_tokens"

version := "0.0.1"

scalaVersion := "2.12.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= {
  val facebook4sVersion = "0.2.9"
  val typesafeV = "1.3.1"

  Seq(
    "com.github.vooolll" %% "facebook4s" % facebook4sVersion,
    "com.typesafe"       %  "config"     % typesafeV
  )
}

fork := true

javaOptions in ThisBuild ++= Seq(sys.env.getOrElse("TRAVIS_OPTION_EXAMPLE", "-Dconfig.file=src/main/resources/.facebook-dev.conf"))