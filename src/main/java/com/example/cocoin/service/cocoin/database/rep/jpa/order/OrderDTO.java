package com.example.cocoin.service.cocoin.database.rep.jpa.order;

import com.example.cocoin.service.auth.database.rep.jpa.user.UserDTO;
import com.example.cocoin.common.database.rep.jpa.CommonDateDTO;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderDTO extends CommonDateDTO {
    private Long id;
    private String orderSlct;
    private String coinSlct;
    private Integer margin;
    private Double price;
    private Double cnt;
    private UserDTO userDTO;

    public static OrderDTO of(OrderEntity orderEntity) {
        return new OrderDTO(
                orderEntity.getId(),
                orderEntity.getOrderSlct(),
                orderEntity.getCoinSlct(),
                orderEntity.getMargin(),
                orderEntity.getPrice(),
                orderEntity.getCnt(),
                UserDTO.of(orderEntity.getUserEntity())
        );
    }

    private OrderDTO() {
    }

    private OrderDTO(Long id, String orderSlct, String coinSlct, Integer margin, Double price, Double cnt, UserDTO userDTO) {
        this.id = id;
        this.orderSlct = orderSlct;
        this.coinSlct = coinSlct;
        this.margin = margin;
        this.price = price;
        this.cnt = cnt;
        this.userDTO = userDTO;
    }
}
