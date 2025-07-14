package com.ricky.hortinha.controller

import com.ricky.hortinha.dto.LoginDTO
import com.ricky.hortinha.dto.TokenDTO
import com.ricky.hortinha.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@CrossOrigin
class AuthController(
    private val usuarioService: UsuarioService
) {
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO):TokenDTO{
        return usuarioService.login(loginDTO)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody token: TokenDTO): TokenDTO {
        return usuarioService.refreshToken(token)
    }
}