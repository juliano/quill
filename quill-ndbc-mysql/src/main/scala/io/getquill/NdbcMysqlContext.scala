package io.getquill

import com.typesafe.config.Config

import io.getquill.context.ndbc._
import io.getquill.util.LoadConfig
import io.trane.ndbc._

class NdbcMysqlContext[N <: NamingStrategy](naming: N, dataSource: DataSource[PreparedStatement, Row])
  extends NdbcContext[MySQLDialect, N, PreparedStatement, Row](MySQLDialect, naming, dataSource)
  with StandardEncoders
  with StandardDecoders {

  def this(naming: N, config: NdbcContextConfig) = this(naming, config.dataSource)
  def this(naming: N, config: Config) = this(naming, NdbcContextConfig(config))
  def this(naming: N, configPrefix: String) = this(naming, LoadConfig(configPrefix))

  protected def createPreparedStatement(sql: String) = MysqlPreparedStatement.create(sql)

  override protected val effect = NdbcContextEffect
}
