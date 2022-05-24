package com.abedfattal.quranx.core.model

abstract class SerializableModel {

    abstract fun toJson(): String
    final override fun toString(): String = toJson()

}