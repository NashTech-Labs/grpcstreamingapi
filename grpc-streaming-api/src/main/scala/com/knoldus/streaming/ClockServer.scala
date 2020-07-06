package com.knoldus.streaming

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{Executors, TimeUnit}
import java.util.logging.Logger

import io.grpc.stub.StreamObserver
import io.grpc.{Server, ServerBuilder}
import proto.hello.{ClockGrpc, TimeResponse}

import scala.concurrent.ExecutionContext

object ClockServer {
  private val logger = Logger.getLogger(classOf[ClockServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new ClockServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 50051
}

class ClockServer(executionContext: ExecutionContext) {
  self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(ClockServer.port).addService(ClockGrpc.bindService(new ClockService(), executionContext)).build.start
    ClockServer.logger.info("Server started, listening on " + ClockServer.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  class ClockService extends ClockGrpc.Clock {
    def getTime(request: proto.hello.TimeRequest, responseObserver: StreamObserver[TimeResponse]): Unit = {
      val scheduler = Executors.newSingleThreadScheduledExecutor()
      val tick = new Runnable {
        val counter = new AtomicInteger(10)
        def run() =
          if (counter.getAndDecrement() >= 0) {
            val currentTime = System.currentTimeMillis()
            responseObserver.onNext(TimeResponse(currentTime))
          } else {
            scheduler.shutdown()
            responseObserver.onCompleted()
          }
      }
      scheduler.scheduleAtFixedRate(tick, 0, 1000, TimeUnit.MILLISECONDS)
    }
  }

}