package com.github.atais

import zio.http._
import zio.test.ZIOSpec
import zio.{Fiber, ZIO, ZLayer}

import FakeServer.HttpServer

object FakeServer {
  type HttpServer = Fiber.Runtime[Throwable, Nothing]
}

trait FakeServer extends ZIOSpec[HttpServer] {

  protected lazy val serverLayer = ZLayer.scoped(
    ZIO.acquireRelease {
      ZIO.logInfo(s"Starting server") *>
        Server
          .serve(Routes(Method.GET / "test" -> handler {
            Response.text("test")
          }).toHttpApp)
          .provide(Server.default)
          .fork
    } { server =>
      ZIO.logInfo(s"Stopping server") *>
        server.interruptFork
    }
  )

  override def bootstrap: ZLayer[Any, Throwable, HttpServer] =
    serverLayer
}
