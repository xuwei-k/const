import sbt._,Keys._

object build extends Build{

  val buildSettings = Defaults.defaultSettings ++ Seq(
    scalaVersion := "2.10.1",
    resolvers ++= Seq(
      Opts.resolver.sonatypeReleases
    ),
    organization := "com.github.xuwei-k",
    version := "0.1.0-SNAPSHOT",
    scalacOptions ++= Seq("-deprecation","-language:experimental.macros"),
    libraryDependencies ++= Seq(
    )
  )

  lazy val root = Project(
    "root",
    file(".")
  ).settings(
    buildSettings ++ Seq(
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    ) : _*
  )aggregate(const,sample)

  lazy val sample = Project(
    "sample",
    file("sample"),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= Seq(
      )
    )
  )dependsOn(const)

  lazy val const = Project(
    "const",
    file("const"),
    settings = buildSettings ++ Seq(
      libraryDependencies <++= scalaVersion{ v =>
        Seq(
          "org.scala-lang" % "scala-compiler" % v
        )
      }
    )
  )
}

