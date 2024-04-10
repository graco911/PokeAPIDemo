package com.gracodev.pokeapidemo.utils

class Helpers {
    companion object {
        fun String.capitalizeFirstLetter(): String {
            if (isEmpty()) return this
            return substring(0, 1).toUpperCase() + substring(1)
        }
    }
}