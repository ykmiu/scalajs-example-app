ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "0.1-SNAPSHOT"

lazy val example = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "Example",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0",
    scalaJSUseMainModuleInitializer := true
  )
