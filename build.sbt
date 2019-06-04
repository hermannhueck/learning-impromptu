
val projectName = "learning-impromptu"
val githubId = "hermannhueck"
val githubHome = s"https://github.com/$githubId"
val projectUrl = s"$githubHome/$projectName"

inThisBuild(
  Seq(
    organization := "io.hueck",
    organizationName := "Hueck",
    description := "A small code sample showing the usage of the 'impromptu' library.",
    homepage := Some(url(projectUrl)),
    startYear := Some(2019),
    licenses := Vector(("MIT", url("https://opensource.org/licenses/MIT"))),
    scmInfo := Some(ScmInfo(url(projectUrl), s"$projectUrl.git")),
    developers := List(
      Developer(id = githubId, name = "Hermann Hueck", email = "", url = url(githubHome))
    ),

    name := projectName,
    version := "0.1.0",

    scalaVersion := "2.13.0-RC3",

    scalacOptions ++= Seq(
      "-encoding", "UTF-8",     // source files are in UTF-8
      "-deprecation",           // warn about use of deprecated APIs
      "-unchecked",             // warn about unchecked type parameters
      "-feature",               // warn about misused language features
      "-Xfatal-warnings",       // turn compiler warnings into errors
    ),
  )
)
