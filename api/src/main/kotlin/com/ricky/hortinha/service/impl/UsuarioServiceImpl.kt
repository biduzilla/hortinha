package com.ricky.hortinha.service.impl

import com.ricky.hortinha.dto.LoginDTO
import com.ricky.hortinha.dto.TokenDTO
import com.ricky.hortinha.exceptions.GenericException
import com.ricky.hortinha.models.Usuario
import com.ricky.hortinha.repository.UsuarioRepository
import com.ricky.hortinha.security.JwtService
import com.ricky.hortinha.service.UsuarioService
import com.ricky.hortinha.utils.I18n
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val i18n: I18n
) : UsuarioService {
    override fun login(login: LoginDTO): TokenDTO {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    login.login,
                    login.senha
                )
            )

            val usuario = findByEmail(login.login)
            val token = jwtService.generateToken(
                userDetails = usuario
            )

            return TokenDTO(
                token = token,
                idUser = usuario.idUsuario,
                nome = usuario.nome
            )
        } catch (e: AuthenticationException) {
            throw GenericException(
                msg = i18n.getMessage("error.login.invalido"),
                httpStatus = HttpStatus.FORBIDDEN
            )
        }
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
        return usuarioRepository.findByEmail(email) ?: throw GenericException(
            msg = i18n.getMessage("email.nao.encotrado"),
            httpStatus = HttpStatus.NOT_FOUND
        )
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