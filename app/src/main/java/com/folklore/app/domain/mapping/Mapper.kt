package com.folklore.app.domain.mapping

abstract class Mapper<T1, T2> {

    abstract fun mapTo(value: T1): T2

    fun mapCollection(values: List<T1>) = values.map { t1 -> mapTo(t1) }

    open fun reverseTransform(value: T2): T1 = throw UnsupportedOperationException()
}
