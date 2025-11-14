package edu.unifalmg.monolithecommerce.order.application.port.in;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.GetOrdersByCustomerIdCommand;

import java.util.List;

public interface GetOrdersByCustomerIdPort {
    List<OrderDTO> execute(GetOrdersByCustomerIdCommand cmd);
}
