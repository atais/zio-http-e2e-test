package com.github.atais

import okhttp3.{OkHttpClient, Request}
import zio._
import zio.http._
import zio.test._

object MainSpec2 extends ZIOSpecDefault {

  val layer =
    ZLayer.scoped {
      for {
        _ <- ZIO.debug("starting server")
        _ <- ZIO.addFinalizer(ZIO.debug("stopped server"))
        _ <- Server
          .serve(Routes(Method.GET / "test" -> handler {
            Response.text("test")
          }).toHttpApp @@ Middleware.debug)
          .provide(Server.default)
          .forkScoped
        _ <- ZIO.debug("started server")
        _ <- ZIO.addFinalizer(ZIO.debug("stopping server"))
      } yield ()
    }

  override def spec =
    suite("Main 2") {
      test("should work") {

        val request = new Request.Builder()
          .url("http://localhost:8080/test")
          .build()

        val call = new OkHttpClient().newCall(request)
        val response = call.execute()

        assertTrue(response.code() == 200)
      } @@ TestAspect.flaky
    }.provideLayerShared(layer)

}
