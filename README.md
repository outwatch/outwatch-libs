# OutWatch-libs - OutWatch Helpers for javascript libraries [![Build Status](https://travis-ci.org/OutWatch/outwatch-libs.svg?branch=master)](https://travis-ci.org/OutWatch/outwatch-libs)

This library provides helpers/wrapper for javascript libraries that can be used with [OutWatch](https://github.com/outwatch/outwatch).

## Getting started

We have prepared some wrappers for javascript libraries:
```scala
libraryDependencies += "com.github.outwatch.outwatch-libs" %%% "outwatch-libs-hammerjs" % "master-SNAPSHOT" // for hammer-js
libraryDependencies += "com.github.outwatch.outwatch-libs" %%% "outwatch-libs-clipboardjs" % "master-SNAPSHOT" // for clipboard-js
libraryDependencies += "com.github.outwatch.outwatch-libs" %%% "outwatch-libs-flatpickr" % "master-SNAPSHOT" // for flatpickr
libraryDependencies += "com.github.outwatch.outwatch-libs" %%% "outwatch-libs-chartjs" % "master-SNAPSHOT" // for chart-js
```

## Bugs and Feedback
For bugs, questions and discussions please use [GitHub Issues](https://github.com/OutWatch/outwatch-libs/issues).


## Community
We adopted the [Scala Code of Conduct](https://www.scala-lang.org/conduct/). People are expected to follow it when discussing OutWatch on the Github page, Gitter channel, or other venues.


## Documentation


### hammer-js

You can use [hammer.js](https://github.com/hammerjs/hammer.js) for touch events with outwatch:

```scala

import outwatch.libs.hammerjs.HammerJs

div(
  "Interact with me",

  HammerJs.onTap.foreach { println("Tapped") },
  HammerJs.onPress.foreach { println("Pressed") },
  HammerJs.onSwipeLeft.foreach { println("Swiped left") },
  HammerJs.onSwipeRight.foreach { println("Swiped right") },

  HammerJs.onHammer(eventString).foreach { println("Tapped") } // if the above predefined ones are not enough, see hammer.js for possible eventString
)

```

### clipboard-js

You can use [clipboard.js](https://github.com/zenorocha/clipboard.js) to copy text to the clipboard:

```scala

import outwatch.libs.clipboardjs.ClipboardJs

button(
  ClipboardJs.onClickCopyToClipboard("Copy this text")
)

```

### flatpickr

You can use [flatpickr](https://github.com/flatpickr/flatpickr) for integrating a datetime-picker:

```scala

import outwatch.libs.flatpickr.Flatpickr

Flatpickr.flatpickr("Select Date and Time", withTime = true, initialDate = None).foreach { date =>
  println("Date selected: " + date)
}

```

### chart-js

Use [chart.js](https://github.com/chartjs/Chart.js) to render charts (uses scalablytyped):

```scala
ChartJs.chartCanvas {
  import typings.chartDotJs.chartDotJsMod._

  ChartConfiguration {
    `type` = "bar"
    data = new ChartData {
      labels = js.Array[String | js.Array[String]]("Red", "Blue", "Yellow", "Green", "Purple", "Orange")
      datasets = js.Array(new ChartDataSets {
        label = "# of Votes"
        data = js.Array[js.UndefOr[ChartPoint | Double | Null]](12, 19, 3, 5, 2, 3)
        backgroundColor = js.Array(
          "rgba(255, 99, 132, 0.2)",
          "rgba(54, 162, 235, 0.2)",
          "rgba(255, 206, 86, 0.2)",
          "rgba(75, 192, 192, 0.2)",
          "rgba(153, 102, 255, 0.2)",
          "rgba(255, 159, 64, 0.2)"
        )
        borderColor = js.Array(
          "rgba(255, 99, 132, 1)",
          "rgba(54, 162, 235, 1)",
          "rgba(255, 206, 86, 1)",
          "rgba(75, 192, 192, 1)",
          "rgba(153, 102, 255, 1)",
          "rgba(255, 159, 64, 1)"
        )
        borderWidth = 1
      })
    }
    options = new ChartOptions {
      scales = new ChartScales {
        yAxes = js.Array(new ChartYAxe {
          ticks = new TickOptions {
            beginAtZero = true
          }
        })
      }
    }
  }
},
```

## LICENSE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<https://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
