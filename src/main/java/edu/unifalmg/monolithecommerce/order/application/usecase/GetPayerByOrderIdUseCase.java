package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.GetPayerByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.Payer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPayerByOrderIdUseCase implements GetPayerByOrderIdPort {

    private OrderRepositoryPort orderRepositoryPort;

    public Payer execute (OrderId orderid){
        return new Payer("test-payer", "test-payer@email.com");
    }
}
