package com.ricky.hortinha.controller

import com.ricky.hortinha.dto.LoginDTO
import com.ricky.hortinha.dto.TokenDTO
import com.ricky.hortinha.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@CrossOrigin
class AuthController(
    private val usuarioService: UsuarioService
) {

    @Operation(
        summary = "Realizar login",
        description = "API para realizar o login do usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO): TokenDTO {
        return usuarioService.login(loginDTO)
    }

    @Operation(
        summary = "Atualizar token",
        description = "API para atualizar o token de acesso do usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Token atualizado"),
        ]
    )
    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody token: TokenDTO): TokenDTO {
        return usuarioService.refreshToken(token)
    }
}