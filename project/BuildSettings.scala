import sbt.Keys._
import sbt.{Def, _}

object BuildSettings {

  val projectSettings: Seq[Def.Setting[_]] = Defaults.coreDefaultSettings ++
    Seq(
      organization in ThisBuild := "com.knoldus",
      scalaVersion in ThisBuild := "2.12.6",
      version := "0.0.5",
      fork in Test := true,
      parallelExecution in Test := true,
      concurrentRestrictions in Global += Tags.limit(Tags.Test, 2),
      resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      resolvers += "bintray algd" at "http://dl.bintray.com/content/algd/maven"
    )

  //scalastyle:off
  def BaseProject(name: String): Project =
    Project(name, file(s"$name"))
      .settings(projectSettings: _*)
      .settings(Defaults.itSettings)
  //scalastyle:on

}
