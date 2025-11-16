package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record MercadoPagoConfigRequest(
        String action,
        String api_version,
        MercadoPagoData data,
        String date_created,
        long id,
        boolean live_mode,
        String type,
        long user_id
) {
    public record MercadoPagoData(
            long id
    ) {}
}

