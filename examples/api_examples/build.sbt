name := "api_examples"

version := "0.0.1"

crossScalaVersions := Seq("2.12.1", "2.11.1")

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= {
  val facebook4sVersion = "0.3.0"
  val typesafeV = "1.3.1"

  Seq(
    "com.github.vooolll" %% "facebook4s" % facebook4sVersion,
    "com.typesafe"       %  "config"     % typesafeV
  )
}

fork := true

javaOptions in ThisBuild ++= Seq(sys.env.getOrElse("TRAVIS_OPTION_EXAMPLE", "-Dconfig.file=src/main/resources/.facebook-dev.conf"))