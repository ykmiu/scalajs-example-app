package example

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.document
import scala.util.Random

case class WindowDimensions(width: Int, height: Int)

object ScalaJSExample {
  def main(args: Array[String]): Unit = {
    document.addEventListener(
      "DOMContentLoaded",
      { (e: dom.Event) =>
        val viewportWidth = List(
          dom.window.innerWidth.toInt,
          document.documentElement.clientWidth
        ).max
        val viewportHeight = List(
          dom.window.innerHeight.toInt,
          document.documentElement.clientHeight
        ).max
        val dimensions = WindowDimensions(viewportWidth, viewportHeight)
        setupCanvas(dimensions)
        setupRenderer(dimensions)
      }
    )
  }

  private def setupRenderer(dimensions: WindowDimensions): Unit = {

    val canvas = document
      .getElementById("canvas")
      .asInstanceOf[html.Canvas]

    val renderer =
      canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.width = canvas.parentElement.clientWidth
    canvas.height = canvas.parentElement.clientHeight
    renderer.fillStyle = "#f8f8f8"
    renderer.fillRect(0, 0, canvas.width, canvas.height)

    renderer.fillStyle = "black"
    var clickedDown = false

    canvas.onmousedown = (e: dom.MouseEvent) => clickedDown = true
    canvas.onmouseup = (e: dom.MouseEvent) => clickedDown = false
    canvas.onmousemove = { (e: dom.MouseEvent) =>
      val rect = canvas.getBoundingClientRect()
      if (clickedDown)
        renderer.fillRect(
          e.clientX - rect.left,
          e.clientY - rect.top,
          10,
          10
        )
    }
  }

  private def setupCanvas(dimensions: WindowDimensions): Unit = {
    val div = document.createElement("div")
    document.body.appendChild(div)

    val canvas = document.createElement("canvas")
    canvas.setAttribute("id", "canvas")
    canvas.setAttribute("style", "display: block")
    canvas.setAttribute("width", dimensions.width.toString())
    canvas.setAttribute("height", dimensions.height.toString())
    div.appendChild(canvas)
  }
}
