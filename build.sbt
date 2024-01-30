ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val ZioVersion = "2.0.21"

lazy val root = (project in file("."))
  .settings(
    name := "zio-http-e2e-test",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-http" % "3.0.0-RC4",
      "dev.zio" %% "zio-test-sbt" % ZioVersion % Test
    )
  )
