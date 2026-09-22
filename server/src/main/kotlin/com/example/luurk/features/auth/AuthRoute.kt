package com.example.luurk.features.auth

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.luurk.features.user.UserDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date
import java.util.concurrent.TimeUnit

suspend fun Application.authRoutes() {

    val authService by inject<AuthService>()

    val jwtConfig = environment.config

    val privateKeyString = jwtConfig
        .property("jwt.privateKey").getString()

    val issuer = jwtConfig.property("jwt.issuer").getString()

    val audience = jwtConfig
        .property("jwt.audience").getString()

    val myRealm = jwtConfig.property("jwt.realm").getString()

    val jwkProvider = JwkProviderBuilder(issuer)
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()


    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(jwkProvider, issuer) {
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

    routing {

        route("/login") {
            post {
                val user = call.receive<UserDto>()

                when (val response = authService.login(user)) {
                    LoginResult.SUCCESS -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.OK
                    )

                    LoginResult.INVALID_CREDENTIALS -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.Unauthorized
                    )
                }

                val keyId = "luurk-dev-key-1"
                val publicKey = jwkProvider.get(keyId).publicKey
                val decoded = Base64.getDecoder()
                    .decode(privateKeyString)
                val keySpecPKCS8 = PKCS8EncodedKeySpec(decoded)
                val privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(keySpecPKCS8)
                val algorithm = Algorithm.RSA256(
                    publicKey as RSAPublicKey,
                    privateKey as RSAPrivateKey
                )
                val expiresAt = System.currentTimeMillis() + 60000
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("id", user.id)
                    .withExpiresAt(Date(expiresAt))
                    .sign(algorithm)
                call.respond(hashMapOf("token" to token))


            }
        }


        route("/signup") {
            post {
                val user = call.receive<UserDto>()

                when (val response = authService.signup(user)) {
                    SignupResult.CREATED -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.Created
                    )

                    SignupResult.EMAIL_ALREADY_EXISTS -> call.respondText(
                        response.toString(),
                        status = HttpStatusCode.Conflict
                    )
                }


            }
        }
    }
}