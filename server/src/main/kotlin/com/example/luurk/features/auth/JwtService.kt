package com.example.luurk.features.auth

import com.auth0.jwt.JWT
import com.example.luurk.plugins.jwt.JwtConfig
import java.util.Date

class JwtService(
    private val jwtConfig: JwtConfig
) {
    fun createToken(id: Long?): String {

        val expiresAt = System.currentTimeMillis() + 3600000
        val token = JWT.create()
            .withAudience(jwtConfig.audience)
            .withIssuer(jwtConfig.issuer)
            .withClaim("id", id)
            .withExpiresAt(Date(expiresAt))
            .sign(jwtConfig.signingAlgorithm)

        return token
    }
}