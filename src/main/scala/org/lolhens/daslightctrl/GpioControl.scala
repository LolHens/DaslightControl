package org.lolhens.daslightctrl


import com.pi4j.io.gpio.{GpioFactory, PinState, RaspiPin}
import com.pi4j.system.SystemInfo.BoardType

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import monix.execution.Scheduler.Implicits.global

/**
  * Created by pierr on 04.11.2016.
  */
class GpioControl(val pinOffset: Int = 0,
                  val pinCount: Int,
                  val delay: FiniteDuration) {
  private val gpio = GpioFactory.getInstance()
  private val pins = (pinOffset until pinCount).map { i =>
    gpio.provisionDigitalOutputPin(RaspiPin.allPins(null: BoardType).apply(i), PinState.HIGH)
  }

  private var _scene = 0

  def scene: Int = _scene

  def scene_=(scene: Int): Future[Unit] = {
    _scene = scene

    val active = pins.zipWithIndex.filter {
      case (pin, i) => (scene & (1 << i)) != 0
    }.map(_._1)

    Future {
      active.foreach { pin => pin.setState(PinState.LOW) }
      Thread.sleep(delay.toMillis)
      pins.foreach { pin => pin.setState(PinState.HIGH) }
    }
  }

  def close() = gpio.shutdown()
}
