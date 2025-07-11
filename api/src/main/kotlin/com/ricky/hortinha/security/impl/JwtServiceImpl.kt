package com.ricky.hortinha.security.impl

import com.ricky.hortinha.security.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtServiceImpl : JwtService {
    @Value("\${security.jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${security.jwt.expiration-time}")
    private var jwtExpiration: Long = 0
    override fun extractUsername(token: String): String {
        return extractClaim(
            token = token,
            claimsResolver = Claims::getSubject
        )
    }

    override fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        return claimsResolver(extractAllClaims(token))
    }

    override fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    override fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return buildToken(
            extraClaims = extraClaims,
            userDetails = userDetails,
            jwtExpiration
        )
    }

    override fun getExpirationTime(): Long = jwtExpiration
    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        var userName: String = extractUsername(token)
        return (userName == userDetails.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJwt(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long
    ): String = Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact()

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }
}