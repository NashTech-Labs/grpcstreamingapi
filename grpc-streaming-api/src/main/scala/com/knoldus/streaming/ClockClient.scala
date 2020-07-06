package com.knoldus.streaming
import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}
import proto.hello.{ClockGrpc, TimeResponse}
import proto.hello.ClockGrpc.ClockBlockingStub

  object ClockClient  {
    def apply(host: String, port: Int): ClockClient = {
      val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
      val blockingStub = ClockGrpc.blockingStub(channel)
      new ClockClient(channel, blockingStub)
    }

    def main(args: Array[String]): Unit = {
      val client = ClockClient("localhost", 50051)
      try {
        val user = args.headOption.getOrElse(ClockClient.toString)
        client.greet(user)
      } finally {
        client.shutdown()
      }
    }
  }

  class ClockClient private(
                             private val channel: ManagedChannel,
                             private val blockingStub: ClockBlockingStub
                           ) {
    private[this] val logger = Logger.getLogger(classOf[ClockClient].getName)


    def shutdown(): Unit = {
      channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
    }

    def greet(name: String): Unit = {
      logger.info("Will try to greet " + name + " ...")
      val request = new proto.hello.TimeRequest
      try {
        val blockingClockResponse: Iterator[TimeResponse] = blockingStub.getTime(request)
        for (t <- blockingClockResponse) {
          println(s"[blocking client] received: $t")
        }
      }
      catch {
        case e: StatusRuntimeException =>
          logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
      }
    }
  }

