package com.ricky.hortinha.service

import com.ricky.hortinha.dto.LoginDTO
import com.ricky.hortinha.dto.TokenDTO
import com.ricky.hortinha.models.Usuario

interface UsuarioService {
    fun login(login: LoginDTO): TokenDTO
    fun findById(idUser: String): Usuario
    fun update(usuario: Usuario): Usuario
    fun save(usuario: Usuario): Usuario
    fun delete(usuario: Usuario)
    fun deleteById(idUsuario: String)
    fun findByEmail(email: String): Usuario
    fun gerarCodVerificacao(): Int
    fun findByCodVerificacao(cod: Int): Usuario
    fun alterarSenha(email: String, senha: String, cod: Int)
    fun verificarCod(cod: Int, email: String)
    fun refreshToken(tokenDTO: TokenDTO): TokenDTO
    fun findUsuariosBySenderId(userId: String): List<Usuario>
    fun enviarEmailSenha(email: String)
}