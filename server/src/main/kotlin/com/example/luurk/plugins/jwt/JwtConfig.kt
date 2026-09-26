package com.example.luurk.plugins.jwt

import com.auth0.jwt.algorithms.Algorithm

data class JwtConfig(
    val issuer: String,
    val realm: String,
    val audience: String,
    val signingAlgorithm: Algorithm,
)
