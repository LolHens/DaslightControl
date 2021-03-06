name := "DaslightControl"

lazy val settings = Seq(
  version := "0.0.0",

  scalaVersion := "2.11.8",

  resolvers := Seq("Artifactory" at "http://lolhens.no-ip.org/artifactory/libs-release/"),

  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % "2.11.8",
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "org.typelevel" %% "cats" % "0.7.2",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "com.github.mpilquist" %% "simulacrum" % "0.10.0",
    "io.monix" %% "monix" % "2.0.4",
    "io.monix" %% "monix-cats" % "2.0.4",
    "com.typesafe.akka" %% "akka-actor" % "2.4.11",
    "com.typesafe.akka" %% "akka-remote" % "2.4.11",
    "com.typesafe.akka" %% "akka-stream" % "2.4.11",
    "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
    "io.spray" %% "spray-json" % "1.3.2",
    "com.github.fommil" %% "spray-json-shapeless" % "1.2.0",
    "com.pi4j" % "pi4j-core" % "1.1"
  ),

  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.2"),

  mainClass in Compile := Some("org.lolhens.daslightctrl.Main"),

  scalacOptions ++= Seq("-Xmax-classfile-name", "254")
)

lazy val root = project.in(file("."))
  .enablePlugins(
    JavaAppPackaging,
    UniversalPlugin)
  .settings(settings: _*)
