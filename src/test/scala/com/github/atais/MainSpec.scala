package com.github.atais
import okhttp3.{OkHttpClient, Request}
import zio.test._

object MainSpec extends FakeServer {

  override def spec =
    suite("Main") {
      test("should work") {

        val request = new Request.Builder()
          .url("http://localhost:8080/test")
          .build()

        val call = new OkHttpClient().newCall(request);
        val response = call.execute();

        assertTrue(response.code() == 200)
      }
    }

}
