package com.eazybank.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component("AuditawareImpl")
public class AuditServiceImpl implements AuditorAware<String> {

    public Optional<String>getCurrentAuditor(){
        return Optional.of("ACCOUNT_MS");
    }
}
