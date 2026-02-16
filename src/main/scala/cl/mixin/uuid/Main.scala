package cl.mixin.uuid

import scala.scalajs.js
import scala.scalajs.js.annotation.*
import org.scalajs.dom

import com.raquo.laminar.api.L.{*, given}
import cl.mixin.uuid.generator.UUIDGeneratorView

// import javascriptLogo from "/javascript.svg"
@js.native @JSImport("/javascript.svg", JSImport.Default)
val javascriptLogo: String = js.native

@JSImport(
  "@awesome.me/webawesome/dist/styles/webawesome.css",
  JSImport.Namespace
)
@js.native
object Stylesheet extends js.Object
val _ = Stylesheet

@main
def Main(): Unit = {
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    UUIDGeneratorView()
  )
}
