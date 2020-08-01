import Options._

inThisBuild(Seq(
  version := "0.1.0-SNAPSHOT",

  organization := "io.github.outwatch",

  scalaVersion := crossScalaVersions.value.last,

  crossScalaVersions := Seq("2.12.10", "2.13.1"),

  licenses += ("Apache 2", url("https://www.apache.org/licenses/LICENSE-2.0.txt")),

	  homepage := Some(url("https://outwatch.github.io/")),

		  scmInfo := Some(ScmInfo(
			  url("https://github.com/OutWatch/outwatch-libs"),
				  "scm:git:git@github.com:OutWatch/outwatch-libs.git",
				  Some("scm:git:git@github.com:OutWatch/outwatch-libs.git"))
			  )
		  ))

lazy val commonSettings = Seq(
	addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
	addCompilerPlugin("com.github.ghik" % "silencer-plugin" % "1.6.0" cross CrossVersion.full),

  useYarn := true,

  resolvers ++=
    ("jitpack" at "https://jitpack.io") ::
    Nil,

  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.2.0" % Test,
    "com.github.ghik" % "silencer-lib" % "1.6.0" % Provided cross CrossVersion.full,
    "com.github.outwatch.outwatch" %%% "outwatch" % "61deece"		//"f3a15f2b" original
  ),

  scalacOptions ++= CrossVersion.partialVersion(scalaVersion.value).map(v =>
    allOptionsForVersion(s"${v._1}.${v._2}", true)
  ).getOrElse(Nil),

  scalacOptions in (Compile, console) ~= (_.diff(badConsoleFlags)),

)

lazy val librarySettings = commonSettings ++ Seq(

  scalacOptions += {
    val local = baseDirectory.value.toURI
    val remote = s"https://raw.githubusercontent.com/OutWatch/outwatch-libs/${git.gitHeadCommit.value.get}/"
    s"-P:scalajs:mapSourceURI:$local->$remote"
  },

  publishMavenStyle := true,

  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },

  pomExtra :=
    <developers>
        <developer>
        <id>jk</id>
        <name>Johannes Karoff</name>
        <url>https://github.com/cornerman</url>
        </developer>
    </developers>,

  pomIncludeRepository := { _ => false }
)

lazy val outwatchLibsHammerJs = project
  .in(file("hammerjs"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(librarySettings)
  .settings(
    name := "OutWatch-libs-HammerJs",
    normalizedName := "outwatch-libs-hammerjs",

    npmDependencies in Compile ++=
        "hammerjs" -> "2.0.8" ::
        "propagating-hammerjs" -> "1.4.6" ::
        Nil
  )

lazy val outwatchLibsClipboardJs = project
  .in(file("clipboardjs"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(librarySettings)
  .settings(
    name := "OutWatch-libs-ClipboardJs",
    normalizedName := "outwatch-libs-clipboardjs",

    npmDependencies in Compile ++=
        "clipboard" -> "2.0.4" ::
        Nil
  )

lazy val outwatchLibsFlatpickr = project
  .in(file("flatpickr"))
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(librarySettings)
  .settings(
    name := "OutWatch-libs-Flatpickr",
    normalizedName := "outwatch-libs-flatpickr",

    npmDependencies in Compile ++=
        "flatpickr" -> "4.6.3" ::
        Nil
  )

lazy val root = project
  .in(file("."))
  .settings(
    name := "outwatch-libs-root",
    skip in publish := true,
  )
  .aggregate(outwatchLibsHammerJs, outwatchLibsClipboardJs, outwatchLibsFlatpickr)
