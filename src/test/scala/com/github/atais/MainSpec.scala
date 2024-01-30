package com.github.atais

import com.github.atais.FakeServer.HttpServer
import okhttp3.{OkHttpClient, Request}
import zio._
import zio.http._
import zio.test._

//object MainSpec extends FakeServer {
//
//  override def spec = {
//    suite("Main") {
//      test("should work") {
//
//        val request = new Request.Builder()
//          .url("http://localhost:8080/test")
//          .build()
//
//        val call = new OkHttpClient().newCall(request);
//        val response = call.execute();
//
//        assertTrue(response.code() == 200)
//      }
//    } @@ TestAspect.flaky
//  } @@ TestAspect.ignore
//
//}

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
          }).toHttpApp @@ Middleware.debug)
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
