import ammonite.ops._

val exampleBuidSbtFile = read.lines! pwd/'examples/'api_examples/"build.sbt"
val targetBuildSbtFile = read.lines! pwd/"build.sbt"

val examplVersionLine = exampleBuidSbtFile.find(_.contains("facebook4sVersion ="))
  .getOrElse(throw new RuntimeException("Failed to find facebook4s example version"))
val targetVersionLine = targetBuildSbtFile.find(_.contains("version :="))
  .getOrElse(throw new RuntimeException("Failed to find facebook4s target version"))

val targetVersion = targetVersionLine.dropWhile(_ != '=')

assert(examplVersionLine.contains(targetVersion),
  s"Example version do not match release version, $examplVersionLine doesn't contain $targetVersion")

println(s"Example version is $targetVersion")

