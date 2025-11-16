package edu.unifalmg.monolithecommerce.order.application.mapper;

import edu.unifalmg.monolithecommerce.order.application.dto.OrderDTO;
import edu.unifalmg.monolithecommerce.order.domain.model.Order;
import edu.unifalmg.monolithecommerce.order.infratestructure.api.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "orderId.orderId", target = "orderId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderDTO toDTO(Order order);

    OrderDTO.OrderItemDTO toItemDTO(OrderItem item);
    List<OrderDTO.OrderItemDTO> toItemDTOList(List<OrderItem> items);

}
