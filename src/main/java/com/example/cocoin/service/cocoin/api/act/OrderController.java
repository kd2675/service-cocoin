package com.example.cocoin.service.cocoin.api.act;

import com.example.cocoin.common.base.dto.ResponseDTO;
import com.example.cocoin.common.base.dto.ResponseDataDTO;
import com.example.cocoin.common.base.vo.Code;
import com.example.cocoin.service.cocoin.api.biz.OrderService;
import com.example.cocoin.service.cocoin.api.dto.DelOrderDTO;
import com.example.cocoin.service.cocoin.api.dto.InsMarketDTO;
import com.example.cocoin.service.cocoin.api.dto.InsOrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cocoin")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseDTO order(@Valid @RequestBody InsOrderDTO insOrderDTO, UserDetails userDetails) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("test");
//        }

        OrderDTO order = orderService.order(insOrderDTO, userDetails);

        return ResponseDataDTO.of(order);
    }
    @PostMapping("/cancel")
    public ResponseDTO cancel(@RequestBody DelOrderDTO delOrderDTO, UserDetails userDetails) {
        orderService.cancel(delOrderDTO, userDetails);

        return ResponseDTO.of(true, Code.OK);
    }
    @PostMapping("/marketOrder")
    public ResponseDTO marketOrder(@RequestBody InsMarketDTO insMarketDTO, UserDetails userDetails) {
        orderService.marketOrder(insMarketDTO, userDetails);

        return ResponseDTO.of(true, Code.OK);
    }
}
