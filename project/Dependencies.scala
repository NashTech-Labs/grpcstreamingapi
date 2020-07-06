import sbt._

object Dependencies {

  def compiledDependencies(deps: ModuleID*): Seq[ModuleID] = deps.map(_ % Compile)


  val scalaTestVersion = "3.0.5"


  val scalapb = "com.thesamet.scalapb" %% "compilerplugin" % "0.10.1"
  /**
   * Compile libraries
   */

  val typesafeConfig = "com.typesafe" % "config" % "1.3.3"
}
