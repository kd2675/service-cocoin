package com.example.cocoin.service.cocoin.api.act;

import com.example.cocoin.service.cocoin.api.biz.OrderService;
import com.example.cocoin.service.cocoin.api.dto.DelOrderDTO;
import com.example.cocoin.service.cocoin.api.dto.InsMarketDTO;
import com.example.cocoin.service.cocoin.api.dto.InsOrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import com.example.cocoin.utils.BithumbApiUtil;
import com.example.cocoin.utils.vo.BithumbTickerVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.core.response.base.dto.ResponseDTO;
import org.example.core.response.base.dto.ResponseDataDTO;
import org.example.core.response.base.vo.Code;
import org.example.database.auth.database.rep.jpa.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cocoin/api/cocoin")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private final BithumbApiUtil bithumbApiUtil;

    @PostMapping("/order")
    public ResponseDTO order(@Valid @RequestBody InsOrderDTO insOrderDTO, UserEntity userEntity) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("test");
//        }

        OrderDTO order = orderService.order(insOrderDTO, userEntity);

        return ResponseDataDTO.of(order);
    }

    @PostMapping("/cancel")
    public ResponseDTO cancel(@RequestBody DelOrderDTO delOrderDTO, UserEntity userEntity) {
        orderService.cancel(delOrderDTO, userEntity);

        return ResponseDTO.of(true, Code.OK);
    }

    @GetMapping("/chart/ticker")
    public ResponseDTO chartTicker() {

        BithumbTickerVO btc = bithumbApiUtil.ticker("BTC");

        return ResponseDTO.of(true, Code.OK);
    }

//    @PostMapping("/marketOrder")
//    public ResponseDTO marketOrder(@RequestBody InsMarketDTO insMarketDTO, UserEntity userEntity) {
//        orderService.marketOrder(insMarketDTO, userEntity);
//
//        return ResponseDTO.of(true, Code.OK);
//    }
}
