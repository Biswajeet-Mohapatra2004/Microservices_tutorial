package com.microservices.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    //here we are passing string type inside the auditorAware class because it is a generic class and
    // we want to write the custom logic for createdBy and updatedBy annotation

    /**
     * for now, we are not going to inject the user's context
     * we will simply return a hardcoded values for now
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNT_SYSTEM");
    }
}
