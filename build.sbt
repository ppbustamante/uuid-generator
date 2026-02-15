import org.scalajs.linker.interface.ModuleSplitStyle
import scala.sys.process.Process

ThisBuild / version := "0.1.0"
ThisBuild / organization := "cl.mixin"
ThisBuild / scalaVersion := Dependencies.Scala_3

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin) // Enable the Scala.js plugin in this project
  .settings(
    name := "uuid-generator",
    // Tell Scala.js that this is an application with a main method
    scalaJSUseMainModuleInitializer := true,

    /* Configure Scala.js to emit modules in the optimal way to
     * connect to Vite's incremental reload.
     * - emit ECMAScript modules
     * - emit as many small modules as possible for classes in the "livechart" package
     * - emit as few (large) modules as possible for all other classes
     *   (in particular, for the standard library)
     */
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(
          ModuleSplitStyle.SmallModulesFor(List("cl.mixin.uuid"))
        )
    },

    /* Depend on the scalajs-dom library.
     * It provides static types for the browser DOM APIs.
     */
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % Dependencies.ScalaJsDom,
    // Depend on Laminar
    libraryDependencies += "com.raquo" %%% "laminar" % Dependencies.Laminar
  )
  .settings(commonSettings)
  .settings(noPublish)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    // "-feature",
    "-language:implicitConversions"
  )
)

lazy val noPublish = Seq(
  publishLocal / skip := true,
  publish / skip := true
)
