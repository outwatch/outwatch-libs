package outwatch.libs.flatpickr

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import outwatch._
import outwatch.dsl._
import colibri._

trait Flatpickr {
  def flatpickr(placeholder: String, withTime: Boolean = false, initialDate: Option[js.Date] = None, format: Option[String] = None, isInline: Boolean = false, openTrigger: Observable[Boolean] = Observable.empty): EmitterBuilder[js.Date, VDomModifier] =
    flatpickrWithOptions(placeholder, new facade.FlatpickrOptions {
      enableTime = withTime
      altInput = true
      inline = isInline
      defaultDate = initialDate.orUndefined
      dateFormat = format.orUndefined
    }, openTrigger)

  def flatpickrWithOptions(placeholder: String, options: facade.FlatpickrOptions, openTrigger: Observable[Boolean] = Observable.empty): EmitterBuilder[js.Date, VDomModifier] = EmitterBuilder.ofModifier { sink =>
    input(
      dsl.placeholder := placeholder,
      managedElement { elem =>
        val oldOnClose = options.onClose
        options.onClose = { (dateArr, a, b) =>
          oldOnClose.foreach(_(dateArr, a, b))
          if (dateArr.nonEmpty) sink.onNext(dateArr.head)
        }: js.Function3[js.Array[js.Date], String, js.Any, Unit]

        val instance = facade.Flatpickr(elem, options)

        Cancelable.composite(
          Cancelable(instance.destroy),
          openTrigger.foreach {
            case true  => instance.open()
            case false => instance.close()
          }
        )
      }
    )
  }
}

object Flatpickr extends Flatpickr
