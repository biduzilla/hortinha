package com.ricky.db

import com.ricky.models.BaseModel
import com.ricky.models.Usuario
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

abstract class BaseTable(name: String, columnName: String = "ID") : UUIDTable(name, columnName) {
    val createdAt = datetime("CREATED_AT").defaultExpression(CurrentDateTime)
    val createdBy = varchar("CREATED_BY", 200).default("anonymous")
    val updatedAt = datetime("UPDATED_AT").nullable()
    val updatedBy = varchar("UPDATED_BY", 200).nullable()
    val deleted = bool("DELETED").default(false)
}

abstract class BaseDAO<T : BaseModel>(
    id: EntityID<UUID>,
    table: BaseTable
) : UUIDEntity(id) {

    private var createdAt by table.createdAt
    private var createdBy by table.createdBy
    private var updatedAt by table.updatedAt
    private var updatedBy by table.updatedBy
    private var deleted by table.deleted

    fun applyAuditFields(model: T) {
        model.apply {
            createdAt = this@BaseDAO.createdAt
            createdBy = this@BaseDAO.createdBy
            updatedAt = this@BaseDAO.updatedAt?.takeIf { this@BaseDAO.updatedAt != null }
            updatedBy = this@BaseDAO.updatedBy?.takeIf { this@BaseDAO.updatedBy != null }
            deleted = this@BaseDAO.deleted
        }
    }
}

object UsuarioTable : BaseTable("USUARIO", "ID_USUARIO") {
    val nome = varchar("NOME", 50)
    val email = varchar("EMAIL", 20)
    val senha = varchar("SENHA", 200)
    val codVerificacao = integer("COD_VERIFICACAO").nullable()
}

class UsuarioDAO(id: EntityID<UUID>) : BaseDAO<Usuario>(id, UsuarioTable) {
    companion object : UUIDEntityClass<UsuarioDAO>(UsuarioTable)

    var nome by UsuarioTable.nome
    var email by UsuarioTable.email
    var senha by UsuarioTable.senha
    var codVerificacao by UsuarioTable.codVerificacao


    fun toUsuario(): Usuario = Usuario(
        idUsuario = id.value,
        nome = nome,
        email = email,
        senha = senha
    ).also { applyAuditFields(it) }
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)