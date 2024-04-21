package com.veselovvv.movies20.core

interface Object<R, M> {
    fun map(mapper: M): R
}