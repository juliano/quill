package io.getquill.context.ndbc

import java.time.{ LocalDate, LocalDateTime, ZoneOffset }
import java.util.{ Date, UUID }

import io.trane.ndbc.MysqlRow
import io.trane.ndbc.value.Value

trait MysqlDecoders {
  this: NdbcContext[_, _, _, MysqlRow] =>

  type Decoder[T] = BaseDecoder[T]

  protected val zoneOffset: ZoneOffset

  def decoder[T, U](f: MysqlRow => Int => T)(implicit map: T => U): Decoder[U] =
    (index, row) => map(f(row)(index))

  implicit def mappedDecoder[I, O](implicit mapped: MappedEncoding[I, O], d: Decoder[I]): Decoder[O] =
    mappedBaseDecoder(mapped, d)

  implicit def optionDecoder[T](implicit d: Decoder[T]): Decoder[Option[T]] =
    (idx, row) =>
      row.column(idx) match {
        case Value.NULL => None
        case _          => Option(d(idx, row))
      }

  implicit val uuidDecoder: Decoder[UUID] = decoder(_.getUUID)
  implicit val stringDecoder: Decoder[String] = decoder(_.getString)
  implicit val bigDecimalDecoder: Decoder[BigDecimal] = decoder(_.getBigDecimal)
  implicit val booleanDecoder: Decoder[Boolean] = decoder(_.getBoolean)
  implicit val byteDecoder: Decoder[Byte] = decoder(_.getByte)
  implicit val shortDecoder: Decoder[Short] = decoder(_.getShort)
  implicit val intDecoder: Decoder[Int] = decoder(_.getInteger)
  implicit val longDecoder: Decoder[Long] = decoder(_.getLong)
  implicit val floatDecoder: Decoder[Float] = decoder(_.getFloat)
  implicit val doubleDecoder: Decoder[Double] = decoder(_.getDouble)
  implicit val byteArrayDecoder: Decoder[Array[Byte]] = decoder(_.getByteArray)
  implicit val dateDecoder: Decoder[Date] = decoder(v => i => Date.from(v.getLocalDateTime(i).toInstant(zoneOffset)))
  implicit val localDateDecoder: Decoder[LocalDate] = decoder(_.getLocalDate)
  implicit val localDateTimeDecoder: Decoder[LocalDateTime] = decoder(_.getLocalDateTime)
}
