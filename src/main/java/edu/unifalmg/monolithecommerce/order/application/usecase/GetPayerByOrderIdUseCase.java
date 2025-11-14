package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.iam.infraestructure.api.UserInformationDTO;
import edu.unifalmg.monolithecommerce.order.application.port.out.IamServicePort;
import edu.unifalmg.monolithecommerce.order.application.port.out.OrderRepositoryPort;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.GetPayerByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderId;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.Payer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetPayerByOrderIdUseCase implements GetPayerByOrderIdPort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final IamServicePort iamServicePort;

    @Override
    public Payer execute (OrderId orderid){
        Optional<Order> order = orderRepositoryPort.findById(orderid.orderId());
        if(order.isEmpty()){
            throw new IllegalArgumentException("Order with this id not found");
        }
        UserInformationDTO userInformationDTO = iamServicePort.getUserInformationById(order.get().getCustomerId());
        return new Payer(userInformationDTO.name(), userInformationDTO.email());
    }
}
