package com.ricky.hortinha.models

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "USUARIO")
@SQLDelete(sql = "UPDATE Usuario SET flagExcluido = true WHERE idUsuario=?")
@SQLRestriction("flagExcluido <> true")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID")
    var idUsuario: String = "",

    @Column(name = "NOME", length = 50)
    var nome: String = "",

    @Column(name = "SENHA")
    var senha: String = "",

    @Column(name = "EMAIL", length = 50)
    var email: String = "",

    @Column(name = "CODVERIFICACAO")
    var codVerificacao: Int = 0,
) : BaseModel(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String = senha

    override fun getUsername(): String = email

}
