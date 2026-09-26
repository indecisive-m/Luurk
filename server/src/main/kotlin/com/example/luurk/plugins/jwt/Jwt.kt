package com.example.luurk.plugins.jwt

import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64


fun Application.configureJwt(): JwtConfig {

    val config = environment.config

    val issuer = config.property("ktor.jwt.issuer").getString()

    val luurkRealm = config.property("ktor.jwt.realm").getString()

    val privateKeyString = config.property("ktor.jwt.privateKey").getString()

    val audience = config.property("ktor.jwt.audience").getString()


    val publicKeyString = config.property("ktor.jwt.publicKey").getString()
    
    val decodedPublicKey = Base64.getDecoder().decode(publicKeyString)
    val keySpecX509 = X509EncodedKeySpec(decodedPublicKey)
    val publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpecX509)
    val verifyingAlgorithm = Algorithm.RSA256(
        publicKey as RSAPublicKey,
    )
    val decodedPrivateKey = Base64.getDecoder()
        .decode(privateKeyString)
    val keySpecPKCS8 = PKCS8EncodedKeySpec(decodedPrivateKey)
    val privateKey = KeyFactory.getInstance("RSA")
        .generatePrivate(keySpecPKCS8)

    val signingAlgorithm = Algorithm.RSA256(
        publicKey as RSAPublicKey,
        privateKey as RSAPrivateKey
    )
    val jwtConfig = JwtConfig(
        issuer = issuer,
        realm = luurkRealm,
        audience = audience,
        signingAlgorithm = signingAlgorithm,
    )

    install(Authentication) {
        jwt("auth-jwt") {
            realm = luurkRealm

            verifier(issuer = issuer, audience = audience, algorithm = verifyingAlgorithm) {
                acceptLeeway(3)
            }

            validate { credential ->
                val payload = credential.payload

                val claim = payload.getClaim("id")
                if (claim.asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                val text = "Token is not valid or has expired"
                val status = HttpStatusCode.Unauthorized
                call.respond(status, text)
            }
        }
    }
    return jwtConfig
}