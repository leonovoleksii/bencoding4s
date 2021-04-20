name := "bencoding4s"

ThisBuild / organization := "io.github.leonovoleksii"
ThisBuild / organizationName := "Leonov Oleksii"
ThisBuild / licenses := List(("MIT", url("http://opensource.org/licenses/MIT")))
ThisBuild / homepage := Some(url("https://github.com/leonovoleksii/bencoding4s"))
ThisBuild / developers := List(Developer("leonovoleksii", "Oleksii Leonov", "leonov.oleksii73@gmail.com", url("https://github.com/leonovoleksii")))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/leonovoleksii/bencoding4s"),
    "git@github.com:leonovoleksii/bencoding4s.git"
  )
)

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies += "org.typelevel" %% "cats-parse" % "0.3.2"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % "test"

ThisBuild / pomIncludeRepository := { _ => false }
publishTo := Some("Sonatype Snapshots Nexus" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
ThisBuild / publishMavenStyle := true