package example

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.document
import scala.util.Random

case class WindowDimensions(width: Int, height: Int)
case class Point(x: Int, y: Int) {
  def +(p: Point) = Point(x + p.x, y + p.y)
  def /(d: Int) = Point(x / d, y / d)
}

object ScalaJSExample {
  def main(args: Array[String]): Unit = {
    document.addEventListener(
      "DOMContentLoaded",
      { (e: dom.Event) =>
        val viewportWidth = List(dom.window.innerWidth.toInt, document.documentElement.clientWidth).max
        val viewportHeight = List(dom.window.innerHeight.toInt, document.documentElement.clientHeight).max
        val dimensions = WindowDimensions(viewportWidth, viewportHeight)
        setupCanvas(dimensions)
        drawTrianges(dimensions)
      }
    )
  }

  private def drawTrianges(dimensions: WindowDimensions): Unit = {

    val canvas = document
      .getElementById("canvas")
      .asInstanceOf[html.Canvas]
    
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    var count = 0
    var p = Point(0, 0)
    val corners = Seq(Point(dimensions.width, dimensions.height), Point(0, dimensions.height), Point(dimensions.width / 2, 0))

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, dimensions.width, dimensions.height)
    }

    def run = for (i <- 0 until 10) {
      if (count % 3000 == 0) clear()
      count += 1
      p = (p + corners(Random.nextInt(3))) / 2

      val height = 512.0 / (dimensions.height + p.y)
      val r = (p.x * height).toInt
      val g = ((dimensions.width - p.x) * height).toInt
      val b = p.y
      ctx.fillStyle = s"rgb($g, $r, $b)"

      ctx.fillRect(p.x, p.y, 1, 1)
    }

    dom.window.setInterval(() => run, 50)

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
