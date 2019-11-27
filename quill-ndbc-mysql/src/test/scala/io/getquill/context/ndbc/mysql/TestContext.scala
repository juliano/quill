package io.getquill.context.ndbc.mysql

import io.getquill.context.sql.{ TestDecoders, TestEncoders }
import io.getquill.{ Literal, NdbcMysqlContext, TestEntities }
import io.trane.future.scala.{ Await, Future }

import scala.concurrent.duration.Duration

class TestContext extends NdbcMysqlContext(Literal, "testMysqlDB")
  with TestEntities
  with TestEncoders
  with TestDecoders {

  def get[T](f: Future[T]): T = Await.result(f, Duration.Inf)
}