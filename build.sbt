normalizedName := "some-module"
name := "Some module"

organization in ThisBuild := "com.ctheu"
scalaVersion in ThisBuild := "2.11.11"
version in ThisBuild = "1.0"

scalacOptions in ThisBuild := Seq(
    "-unchecked", "-feature",
    "-deprecation",
    "-encoding", "utf8",
    "-unchecked",
    "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-unused", "-Ywarn-unused-import",
    "-language:postfixOps", "-language:implicitConversions"
    /*,"-Ymacro-debug-lite"*/)


publishMavenStyle := true
updateOptions := updateOptions.value.withCachedResolution(true)

// Classic libs

libraryDependencies in ThisBuild ++= Seq(
    "net.codingwell" %% "scala-guice" % "4.0.0",
    "com.github.kxbmap" %% "configs" % "0.3.0",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "org.apache.commons" % "commons-lang3" % "3.4",
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0",
)

// Tests

lazy val testDependencies = Seq(
    "org.scalactic" %% "scalactic" % "3.0.1" % "test",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % "test",
    "org.mockito" % "mockito-core" % "2.7.1" % "test"
)

testOptions in Test in ThisBuild += Tests.Argument("-oDF")

fork in Test in ThisBuild := true

// Monitoring

val kamonVersion = "0.6.3"

lazy val kamonApi = Seq(
    "io.kamon" %% "kamon-core" % kamonVersion,
    "io.kamon" %% "kamon-scala" % kamonVersion,
)
lazy val kamonImpl = Seq(
    "io.kamon" %% "kamon-fluentd" % kamonVersion,
    "io.kamon" %% "kamon-jmx" % kamonVersion,
    "io.kamon" %% "kamon-akka" % kamonVersion,
    "io.kamon" %% "kamon-play-25" % kamonVersion
)

// If working with awt, can set headless
javaOptions += "-Djava.awt.headless=true"

// Akka

lazy val akkaDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
)

// BuildInfo (for REST exposition)


buildInfoSettings
sourceGenerators in Compile += buildInfo
buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
buildInfoKeys ++= Seq[BuildInfoKey](
  "EXTRA_INFO" -> sys.env.getOrElse("EXTRA_INFO", "N/A"),
  "BUILD_NUMBER" -> sys.env.getOrElse("BUILD_NUMBER", "N/A"),
  "BUILD_ID" -> sys.env.getOrElse("BUILD_ID", "N/A"),
  "NODE_NAME" -> sys.env.getOrElse("NODE_NAME", "N/A"),
  "GIT_BRANCH" -> sys.env.getOrElse("GIT_BRANCH", "N/A"),
  "GIT_COMMIT" -> sys.env.getOrElse("GIT_COMMIT", "N/A"),
  "BUILD_AT" -> {
    val dtf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    dtf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"))
    dtf.format(new java.util.Date())
  })

buildInfoPackage := "com.ctheu"


lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(...)
  .dependsOn("xxx" % "compile->compile;test->test", ...)
  .aggregate(...)
