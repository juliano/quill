package io.getquill.dsl

trait LowPriorityPostgresImplicits {
  this: CoreDsl =>

  implicit def mappedEncoder[I, O](implicit mapped: MappedEncoding[I, O], e: BaseEncoder[O]): BaseEncoder[I] =
    mappedBaseEncoder(mapped, e)

  implicit def mappedDecoder[I, O](implicit mapped: MappedEncoding[I, O], d: BaseDecoder[I]): BaseDecoder[O] =
    mappedBaseDecoder(mapped, d)
}