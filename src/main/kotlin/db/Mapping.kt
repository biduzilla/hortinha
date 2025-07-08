package com.ricky.db

import com.ricky.models.BaseModel
import com.ricky.models.Usuario
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.*

abstract class AuditableTable(name: String, columnName: String = "ID") : UUIDTable(name, columnName) {
    val createdAt = datetime("CREATED_AT").defaultExpression(CurrentDateTime)
    val createdBy = varchar("CREATED_BY", 200).nullable()
    val updatedAt = datetime("UPDATED_AT").nullable()
    val updatedBy = varchar("UPDATED_BY", 200).nullable()
    val deleted = bool("DELETED").default(false)
}

abstract class AuditableDAO<T : BaseModel>(
    id: EntityID<UUID>,
    private val table: AuditableTable
) : UUIDEntity(id) {

    private var createdAt by table.createdAt
    private var createdBy by table.createdBy
    private var updatedAt by table.updatedAt
    private var updatedBy by table.updatedBy
    private var deleted by table.deleted

    protected fun applyAuditFields(model: T) {
        model.apply {
            createdAt = this@AuditableDAO.createdAt
            createdBy = this@AuditableDAO.createdBy
            updatedAt = this@AuditableDAO.updatedAt
            updatedBy = this@AuditableDAO.updatedBy
            deleted = this@AuditableDAO.deleted
        }
    }
}

object UsuarioTable : AuditableTable("USUARIO", "ID_USUARIO") {
    val nome = varchar("NOME", 50)
    val email = varchar("EMAIL", 20)
    val senha = varchar("SENHA", 200)
    val codVerificacao = integer("COD_VERIFICACAO").nullable()
}

class UsuarioDAO(id: EntityID<UUID>) : AuditableDAO<Usuario>(id, UsuarioTable) {
    companion object : UUIDEntityClass<UsuarioDAO>(UsuarioTable)

    private var nome by UsuarioTable.nome
    private var email by UsuarioTable.email
    private var senha by UsuarioTable.senha

    fun toUsuario(): Usuario = Usuario(
        idUsuario = id.value,
        nome = nome,
        email = email,
        senha = senha
    ).also { applyAuditFields(it) }
}