package com.mrkaz.data.mapper

abstract class ModelMapper<T, V> {
    abstract fun map(data: T): V
}