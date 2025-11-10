package edu.unifalmg.monolithecommerce.order.infratestructure.adapter.in.messengers;


import edu.unifalmg.monolithecommerce.order.application.dto.commands.UpdateOrderStatusCommand;
import edu.unifalmg.monolithecommerce.order.domain.model.enums.OrderStatus;
import edu.unifalmg.monolithecommerce.payment.domain.model.enums.PaymentStatus;
import edu.unifalmg.monolithecommerce.payment.domain.model.events.PaymentChangeStatusEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper {

    default UpdateOrderStatusCommand toCommand (PaymentChangeStatusEvent event){
        return new UpdateOrderStatusCommand(
                event.orderId(),
                this.toOrderStatus(event.paymentStatus()));
    }

    default OrderStatus toOrderStatus(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case WAITING -> OrderStatus.PROCESSING;
            case APPROVED -> OrderStatus.COMPLETED;
            case REJECTED -> OrderStatus.CANCELLED;
        };
    }

}
