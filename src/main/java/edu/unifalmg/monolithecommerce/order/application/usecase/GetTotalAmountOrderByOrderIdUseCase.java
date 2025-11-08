package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.GetTotalAmountOrderByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.OrderId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GetTotalAmountOrderByOrderIdUseCase implements GetTotalAmountOrderByOrderIdPort {
    public Money execute(OrderId orderid){
        return new Money(BigDecimal.valueOf(130.00));
    }
}
