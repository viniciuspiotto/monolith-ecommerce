package edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.mapper;

import edu.unifalmg.monolithecommerce.payment.application.dto.commands.ProcessPaymentNotificationCommand;
import edu.unifalmg.monolithecommerce.payment.infraestructure.adapter.in.requests.MercadoPagoConfigRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {
    default ProcessPaymentNotificationCommand toCommand(MercadoPagoConfigRequest mercadoPagoConfigRequest){
        return new ProcessPaymentNotificationCommand(mercadoPagoConfigRequest.data().id(), mercadoPagoConfigRequest.type());
    }
}
