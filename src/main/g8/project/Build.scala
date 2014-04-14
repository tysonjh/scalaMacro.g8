import sbt._
import Keys._

object BuildSettings {
  val paradiseVersion = "$g8_paradise_version$"
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "$g8_organization$",
    version := "$g8_project_version$",
    scalacOptions ++= Seq(),
    scalaVersion := "$g8_scala_version$",
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
  )
}

object MyBuild extends Build {
  import BuildSettings._

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings ++ Seq(
      run <<= run in Compile in core
    )
  ) aggregate(macros, core)

  lazy val macros: Project = Project(
    "macros",
    file("macros"),
    settings = buildSettings ++ Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
      libraryDependencies ++= (
        if (scalaVersion.value.startsWith("2.10")) List("org.scalamacros" % "quasiquotes" % paradiseVersion cross CrossVersion.full)
        else Nil
      ),
      initialCommands in console := """
      |import reflect.runtime.universe
      |import universe._
      |import reflect.runtime.currentMirror
      |import tools.reflect.ToolBox
      |val toolbox = currentMirror.mkToolBox()
      """.stripMargin
    )
  )

  lazy val core: Project = Project(
    "core",
    file("core"),
    settings = buildSettings
  ) dependsOn(macros)
}
