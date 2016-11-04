package org.lolhens.daslightctrl

import java.net.{ServerSocket, Socket}

import monix.reactive.Observable

/**
  * Created by pierr on 04.11.2016.
  */
class Server(val port: Int) {
  private def messageSocket = {
    val serverSocket = new ServerSocket(port)

    {
      for (
        socket <- Observable.fromIterator(new Iterator[Socket] {
          override def hasNext: Boolean = true

          override def next(): Socket = serverSocket.accept()
        });
        inputStream = socket.getInputStream;
        message <- Observable.fromIterator(new Iterator[Int] {
          var closed = false

          override def hasNext: Boolean = !closed

          override def next(): Int = {
            val byte = inputStream.read()
            if (byte == -1) closed = true
            byte
          }
        })
      ) yield message
    }.doOnError(_ => serverSocket.close())
  }

  def messages: Observable[Int] = messageSocket.onErrorRestartUnlimited
}
