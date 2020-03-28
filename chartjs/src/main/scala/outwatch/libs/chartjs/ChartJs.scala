package outwatch.libs.chartjs

import org.scalajs.dom

import typings.chartDotJs.chartDotJsMod.ChartConfiguration
import outwatch._
import outwatch.dsl._

trait ChartJs {
  def chartCanvas(configuration: ChartConfiguration): VNode = {
    canvas(
      managedElement { elem =>
        val context = elem.asInstanceOf[dom.html.Canvas].getContext("2d").asInstanceOf[typings.std.CanvasRenderingContext2D]
        val chart = new typings.chartDotJs.chartDotJsMod.^(context, configuration)

        cancelable(() => chart.destroy())
      }
    )
  }
}

object ChartJs extends ChartJs
