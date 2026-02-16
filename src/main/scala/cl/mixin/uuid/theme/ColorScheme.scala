package cl.mixin.uuid.theme

import org.scalajs.dom
import scala.scalajs.js
import com.raquo.airstream.web.WebStorageVar
import com.raquo.laminar.api.L.*
import io.github.nguyenyou.webawesome.laminar.*

object ColorScheme {

  private val themeVar = WebStorageVar
    .localStorage(
      key = "theme",
      syncOwner = Some(unsafeWindowOwner)
    )
    .text(default = "light")

  def isSystemDarkMode(): Boolean = {
    dom.window.matchMedia("(prefers-color-scheme: dark)").matches
  }

  def setMode(mode: ThemeMode): Unit = {
    mode match {
      case ThemeMode.Light  => themeVar.set("light")
      case ThemeMode.Dark   => themeVar.set("dark")
      case ThemeMode.System => themeVar.set("system")
    }
  }

  def getMode(): ThemeMode = {
    themeVar.now() match {
      case "light"  => ThemeMode.Light
      case "dark"   => ThemeMode.Dark
      case "system" =>
        if (isSystemDarkMode()) ThemeMode.Dark else ThemeMode.Light
      case _ => ThemeMode.Light
    }
  }

  def updateTheme(): Unit = {
    val containerNode = dom.document.querySelector("html")
    getMode() match {
      case ThemeMode.Light =>
        containerNode.classList.remove("wa-dark")
      case ThemeMode.Dark   => containerNode.classList.add("wa-dark")
      case ThemeMode.System =>
        if (isSystemDarkMode()) containerNode.classList.add("wa-dark")
        else containerNode.classList.remove("wa-dark")
    }
  }

  def apply(): HtmlElement = {
    updateTheme()
    val checked = Var(getMode() == ThemeMode.Dark)
    div(
      className := "theme-switch-container",
      Icon(_.name := "sun")(),
      Switch(
        _.name := "theme-input",
        _.checked <-- checked,
        _.onInput.mapToChecked --> { value =>
          {
            checked.set(value)
            if (value) {
              setMode(ThemeMode.Dark)
            } else {
              setMode(ThemeMode.Light)
            }
            updateTheme()
          }
        }
      )(),
      Icon(_.name := "moon")()
    )
  }

}
