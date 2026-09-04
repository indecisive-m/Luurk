package com.example.luurk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform