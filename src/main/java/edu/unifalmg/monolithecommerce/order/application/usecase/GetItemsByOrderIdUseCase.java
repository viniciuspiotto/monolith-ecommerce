package edu.unifalmg.monolithecommerce.order.application.usecase;

import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.GetItemsByOrderIdPort;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.Item;
import edu.unifalmg.monolithecommerce.order.infratestructure.adapter.out.api.OrderId;
import edu.unifalmg.monolithecommerce.shared.domain.model.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class GetItemsByOrderIdUseCase implements GetItemsByOrderIdPort {

    public List<Item> execute (OrderId orderId) {

        Money money1 = new Money(BigDecimal.valueOf(50.00));
        Item item1 = new Item(UUID.randomUUID(), "Magic Altar 3d Prop", money1);

        Money money2 = new Money(BigDecimal.valueOf(80.00));
        Item item2 = new Item(UUID.randomUUID(), "Magic Stag 3d Prop", money2);

        return List.of(item1, item2);

    }

}
