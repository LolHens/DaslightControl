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
          var lastByte: Option[Int] = None

          def read() = lastByte.getOrElse {
            val byte = inputStream.read()
            lastByte = Some(byte)
            byte
          }

          override def hasNext: Boolean = read() != -1

          override def next(): Int = {
            val byte = read()
            lastByte = None
            byte
          }
        })
      ) yield message
    }.doOnError(_ => serverSocket.close())
  }

  def messages: Observable[Int] = messageSocket.onErrorRestartUnlimited
}
