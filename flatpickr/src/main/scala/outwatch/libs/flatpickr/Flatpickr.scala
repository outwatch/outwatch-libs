package outwatch.libs.flatpickr

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import outwatch._
import outwatch.dsl._
import colibri._

trait Flatpickr {
  def flatpickr(placeholder: String, withTime: Boolean, initialDate: Option[js.Date], isInline: Boolean = false, openTrigger: Observable[Boolean] = Observable.empty): EmitterBuilder[js.Date, VDomModifier] = EmitterBuilder.ofModifier { sink =>
    input(
      dsl.placeholder := placeholder,
      managedElement { elem =>
        val instance = facade.Flatpickr(elem, new facade.FlatpickrOptions {
          enableTime = withTime
          altInput = true
          inline = isInline
          defaultDate = initialDate.orUndefined
          onClose = { (dateArr, _, _) =>
            if (dateArr.nonEmpty) sink.onNext(dateArr.head)
          }: js.Function3[js.Array[js.Date], String, js.Any, Unit]
        })

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
