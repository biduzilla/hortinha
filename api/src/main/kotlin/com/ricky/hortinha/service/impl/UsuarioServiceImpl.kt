package com.ricky.hortinha.service.impl

import com.ricky.hortinha.dto.LoginDTO
import com.ricky.hortinha.dto.TokenDTO
import com.ricky.hortinha.exceptions.GenericException
import com.ricky.hortinha.models.Usuario
import com.ricky.hortinha.repository.UsuarioRepository
import com.ricky.hortinha.security.JwtService
import com.ricky.hortinha.service.UsuarioService
import com.ricky.hortinha.utils.I18n
import com.ricky.hortinha.utils.geradorCodigo
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder

class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val i18n: I18n,
    private val passwordEncoder: PasswordEncoder
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
        return usuarioRepository.findById(idUser).orElseThrow {
            throw GenericException(
                msg = i18n.getMessage("email.nao.encotrado"),
                httpStatus = HttpStatus.NOT_FOUND
            )
        }
    }

    override fun update(usuario: Usuario): Usuario {
        val user = findById(usuario.idUsuario)
        BeanUtils.copyProperties(usuario, user)
        return save(user)
    }

    override fun save(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    override fun delete(usuario: Usuario) {
        usuarioRepository.delete(usuario)
    }

    override fun deleteById(idUsuario: String) {
        usuarioRepository.deleteById(idUsuario)
    }

    override fun findByEmail(email: String): Usuario {
        return usuarioRepository.findByEmail(email) ?: throw GenericException(
            msg = i18n.getMessage("email.nao.encotrado"),
            httpStatus = HttpStatus.NOT_FOUND
        )
    }

    override fun gerarCodVerificacao(): Int {
        var cod: Int
        do {
            cod = geradorCodigo()
        } while (usuarioRepository.existsByCodVerificacao(cod))
        return cod
    }

    override fun findByCodVerificacao(cod: Int): Usuario {
        return usuarioRepository.findByCodVerificacao(cod) ?: throw GenericException(
            msg = i18n.getMessage("usuario.nao.encotrado"),
            httpStatus = HttpStatus.NOT_FOUND
        )
    }

    override fun alterarSenha(email: String, senha: String, cod: Int) {
        val user = usuarioRepository.findByEmailAndCodVerificacao(email, cod)
            ?: throw GenericException(
                msg = i18n.getMessage("usuario.nao.encotrado"),
                httpStatus = HttpStatus.NOT_FOUND
            )
        verificarSenha(senha)
        user.codVerificacao = 0
        user.senha = passwordEncoder.encode(senha)
        usuarioRepository.save(user)
    }

    override fun verificarCod(cod: Int, email: String) {
        if (!usuarioRepository.existsByEmailAndCodVerificacao(email, cod)) {
            throw GenericException(
                msg = i18n.getMessage("cod.verificacao.invalido"),
                httpStatus = HttpStatus.NOT_FOUND
            )
        }
    }

    override fun refreshToken(tokenDTO: TokenDTO): TokenDTO {
        val user = findById(tokenDTO.idUser)
        val token = jwtService.generateToken(user)
        return TokenDTO(
            token = token,
            idUser = tokenDTO.idUser,
            nome = tokenDTO.nome
        )
    }

    override fun findUsuariosBySenderId(userId: String): List<Usuario> {
        TODO("Not yet implemented")
    }

    private fun verificarSenha(senha: String) {
        if (senha.toCharArray().size <= 7) {
            throw GenericException(
                msg = i18n.getMessage("error.senha.curta"),
                httpStatus = HttpStatus.BAD_REQUEST
            )
        }
    }
}