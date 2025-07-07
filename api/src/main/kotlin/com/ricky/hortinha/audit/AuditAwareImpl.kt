package com.ricky.hortinha.audit

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component("auditAwareImpl")
class AuditAwareImpl : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
//        val authentication = SecurityContextHolder.getContext().authentication
//        val username = authentication?.name
//        return Optional.ofNullable(username)
        return Optional.ofNullable("username")
    }
}