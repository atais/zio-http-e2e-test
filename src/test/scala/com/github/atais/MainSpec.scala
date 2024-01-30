package com.github.atais
import zio.test._

object MainSpec extends FakeServer {

  override def spec: Spec[Any, Any] = suite("Main") {
    test("should properly work for sample files") {

      assertTrue(true)
    }
      .provide(
      )
  }

}
