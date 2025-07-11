package com.ricky.hortinha.service.impl

import com.ricky.hortinha.dto.LoginDTO
import com.ricky.hortinha.dto.TokenDTO
import com.ricky.hortinha.models.Usuario
import com.ricky.hortinha.service.UsuarioService
import org.springframework.security.core.userdetails.UserDetails

class IUsuarioService:UsuarioService {
    override fun login(login: LoginDTO): TokenDTO {
        TODO("Not yet implemented")
    }

    override fun findById(idUser: String): Usuario {
        TODO("Not yet implemented")
    }

    override fun update(usuario: Usuario): Usuario {
        TODO("Not yet implemented")
    }

    override fun save(usuario: Usuario, verificar: Boolean): Usuario {
        TODO("Not yet implemented")
    }

    override fun delete(usuario: Usuario) {
        TODO("Not yet implemented")
    }

    override fun deleteById(idUsuario: String) {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String): Usuario {
        TODO("Not yet implemented")
    }

    override fun gerarCodVerificacao(): Int {
        TODO("Not yet implemented")
    }

    override fun findByCodVerificacao(cod: Int): Usuario {
        TODO("Not yet implemented")
    }

    override fun alterarSenha(email: String, senha: String, cod: Int) {
        TODO("Not yet implemented")
    }

    override fun verificarCod(cod: Int, email: String) {
        TODO("Not yet implemented")
    }

    override fun refreshToken(tokenDTO: TokenDTO): TokenDTO {
        TODO("Not yet implemented")
    }

    override fun findUsuariosBySenderId(userId: String): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }
}