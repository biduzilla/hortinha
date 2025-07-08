package com.ricky.db

import com.ricky.models.Usuario
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.*

object UsuarioTable : UUIDTable("USUARIO", "ID_USUARIO") {
    val nome = varchar("NOME", 50)
    val email = varchar("EMAIL", 20)
    val senha = varchar("SENHA", 200)
    val codVerificacao = integer("COD_VERIFICACAO").nullable()
    val createdAt = datetime("CREATED_AT").defaultExpression(CurrentDateTime)
    val createdBy = varchar("CREATE_BY", 200).nullable()
    val updatedAt = datetime("UPDATE_AT").nullable()
    val updatedBy = varchar("UPDATE_BY", 200).nullable()
    val deleted = bool("DELETED").default(false)
}

class UsuarioDAO(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UsuarioDAO>(UsuarioTable)

    private var nome by UsuarioTable.nome
    private var email by UsuarioTable.email
    private var senha by UsuarioTable.senha
    private var createdAt by UsuarioTable.createdAt
    private var createdBy by UsuarioTable.createdBy
    private var updatedAt by UsuarioTable.updatedAt
    private var updatedBy by UsuarioTable.updatedBy
    private var deleted by UsuarioTable.deleted

    fun toUsuario(): Usuario = Usuario(
        idUsuario = id.value,
        nome = nome,
        email = email,
        senha = senha
    ).also { usuario ->
        usuario.createdAt = createdAt
        usuario.createdBy = createdBy
        usuario.updatedAt = updatedAt
        usuario.updatedBy = updatedBy
        usuario.flagExcluido = deleted
    }
}