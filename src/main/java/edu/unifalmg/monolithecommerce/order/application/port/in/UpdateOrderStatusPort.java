package edu.unifalmg.monolithecommerce.order.application.port.in;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.application.dto.commands.UpdateOrderStatusCommand;

public interface UpdateOrderStatusPort {
    OrderDTO execute(UpdateOrderStatusCommand cmd);
}
