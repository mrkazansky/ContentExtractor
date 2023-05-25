package com.mrkaz.data.mapper

interface ModelMapper<T, V> {
    fun map(data: T): V
}