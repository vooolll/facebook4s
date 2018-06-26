name := "facebook4s"

version := "0.3.0-SNAPSHOT"

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

resolvers += Resolver.sonatypeRepo("releases")

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

organization := "com.github.vooolll"

organizationHomepage := Some(url("https://github.com/vooolll"))

description := "An asynchronous non-blocking Scala client for Facebook Graph API (Facebook api REST)"

autoAPIMappings := true
apiMappings ++= {
  val classpath = (fullClasspath in Compile).value
  def findJar(name: String): File = {
    val regex = ("/" + name + "[^/]*.jar$").r
    classpath.find { jar => regex.findFirstIn(jar.data.toString).nonEmpty }.get.data // fail hard if not found
  }


  Map(
    findJar("scala-library") -> url("http://scala-lang.org/api/" + "2.12.1" + "/"),
    findJar("config") -> url("https://typesafehub.github.io/config/latest/api/")
  )
}

pomExtra :=
  <url>https://github.com/vooolll/facebook4s</url>
  <scm>
    <url>https://github.com/vooolll/facebook4s</url>
    <connection>scm:git:git@github.com:vooolll/facebook4s.git</connection>
  </scm>
  <developers>
    <developer>
      <id>slow_harry</id>
      <name>Valeryi Baibossynov</name>
      <url>https://github.com/vooolll</url>
    </developer>
  </developers>

libraryDependencies ++= {
  val akkaHttpV = "10.1.1"
  val scalaTestV = "3.0.1"
  val typesafeV = "1.3.1"
  val mockitoV = "1.8.5"
  val scalaLoggingV = "3.7.2"
  val akkaJsonSupportV = "1.20.1"
  val logbackClassicV = "1.2.3"
  val uriBuilderV = "0.9.0"
  val circeV = "0.9.3"
  val commonsLang = "3.7"

  val testDependencies = Seq(
    "org.scalatest"  %% "scalatest"       % scalaTestV,
    "ch.qos.logback" %  "logback-classic" % logbackClassicV,
    "org.mockito"    %  "mockito-core"    % mockitoV,
    "io.circe"       %% "circe-parser"    % circeV).map(_ % Test)

  val dependencies = Seq(
    "com.typesafe.akka"              %% "akka-http"           % akkaHttpV,
    "com.typesafe.scala-logging"     %% "scala-logging"       % scalaLoggingV,
    "de.heikoseeberger"              %% "akka-http-circe"     % akkaJsonSupportV,
    "org.f100ded.scala-url-builder"  %% "scala-url-builder"   % uriBuilderV,
    "io.circe"                       %% "circe-core"          % circeV,
    "io.circe"                       %% "circe-generic"       % circeV,
    "com.typesafe"                   %  "config"              % typesafeV,
    "org.apache.commons"             %  "commons-lang3"       % commonsLang
  )

  dependencies ++ testDependencies
}

licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-Xlint",
  "-feature",
  "-language:postfixOps",
  "-unchecked",
  "-deprecation")

fork := true

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-W", "120", "60")

javaOptions in Test ++= Seq(sys.env.getOrElse("TRAVIS_OPTION", "-Dconfig.file=src/test/resources/.facebook-dev.conf"))

scalacOptions in ThisBuild ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:existentials",
  "-feature",
  "-deprecation")
