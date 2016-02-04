enablePlugins(PlayScala)

organization := "com.thoughtworks.microbuilder.tutorial"

name := "organization-bff"

scalaVersion in ThisBuild := "2.11.7"

routesGenerator := InjectedRoutesGenerator

libraryDependencies += "com.thoughtworks.each" %% "each" % "0.5.1"

libraryDependencies += "com.thoughtworks.microbuilder" %% "microbuilder-play" % "4.0.0"

libraryDependencies += "com.thoughtworks.microbuilder.tutorial" %% "github-sdk" % "1.0.2"

libraryDependencies += "com.thoughtworks.microbuilder.tutorial" %% "organization-bff-sdk" % "1.0.0"
