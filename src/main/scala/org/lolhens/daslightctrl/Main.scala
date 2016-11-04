package org.lolhens.daslightctrl

import monix.reactive.Observable
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by pierr on 04.11.2016.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val gpioControl = new GpioControl(
      pinCount = 8,
      delay = 100 millis
    )

    val server = new Server(11641)

    server.messages
      .flatMap(message => Observable.fromFuture(gpioControl.scene = message))
      .foreach(_ => ())
      //.foreach(println(_))

    while (true) {
      Thread.sleep(1000)
    }
  }
}
