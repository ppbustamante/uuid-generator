package cl.mixin.uuid.generator

import java.util.UUID

import scala.scalajs.js
import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}
import io.github.nguyenyou.webawesome.laminar.*

import cl.mixin.uuid.theme.ColorScheme

object UUIDGeneratorView {
  def apply(): HtmlElement = {
    val uuid = Var(UUID.randomUUID())
    div(
      h2("UUIDv4", margin := "0"),
      p(
        idAttr := "uuid-text",
        child.text <-- uuid.signal.map(_.toString())
      ),
      CopyButton(
        _.from := "uuid-text",
        _.copyLabel := "Click to copy",
        _.successLabel := "You did it!",
        _.errorLabel := "Whoops, your browser doesn't support this!"
      )(),
      ColorScheme()
    )
  }
}
