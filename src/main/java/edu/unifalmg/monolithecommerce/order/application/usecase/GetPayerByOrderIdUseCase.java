package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.GetPayerByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.Payer;
import org.springframework.stereotype.Service;

@Service
public class GetPayerByOrderIdUseCase implements GetPayerByOrderIdPort {

    public Payer execute (OrderId orderid){
        return new Payer("test-payer", "test-payer@email.com");
    }
}
