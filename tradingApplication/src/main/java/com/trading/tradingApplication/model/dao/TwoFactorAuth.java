package com.trading.tradingApplication.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trading.tradingApplication.domain.VerificationType;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable  // Allows embedding in JPA entities
@JsonIgnoreProperties(ignoreUnknown = true)  // Prevents serialization issues
public class TwoFactorAuth {
    private Boolean isEnabled = false;
    private VerificationType sendTo;
}
