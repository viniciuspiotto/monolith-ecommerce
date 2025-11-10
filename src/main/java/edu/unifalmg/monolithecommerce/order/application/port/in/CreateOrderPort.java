package edu.unifalmg.monolithecommerce.order.application.port.in;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.CreateOrderCommand;

public interface CreateOrderPort {
    OrderDTO execute(CreateOrderCommand cmd);
}
