package com.vuhm.note_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform