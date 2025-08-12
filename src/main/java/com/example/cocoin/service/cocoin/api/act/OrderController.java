package com.example.cocoin.service.cocoin.api.act;

import com.example.cocoin.service.cocoin.api.biz.OrderService;
import com.example.cocoin.service.cocoin.api.dto.DelOrderDTO;
import com.example.cocoin.service.cocoin.api.dto.InsOrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import com.example.cocoin.utils.BithumbApiUtil;
import com.example.cocoin.utils.vo.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.core.response.base.dto.ResponseDTO;
import org.example.core.response.base.dto.ResponseDataDTO;
import org.example.core.response.base.vo.Code;
import org.example.database.database.auth.entity.UserEntity;
import org.example.log.annotation.Timer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocoin")
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

    @Timer
    @GetMapping("/ctf/chart/ticker")
    public ResponseDTO ticker() {

        BithumbTickerVO btc = bithumbApiUtil.ticker("BTC");
//        List<BithumbCandlesVO> candles = bithumbApiUtil.candles("BTC");
//        BithumbOrderbookVO orderbook = bithumbApiUtil.orderbook("BTC");

        return ResponseDataDTO.of(BithumbTickerDTO.fromBithumbTickerVO(btc));
    }

    @Timer
    @GetMapping("/ctf/chart/candles")
    public ResponseDTO candles() {

//        BithumbTickerVO btc = bithumbApiUtil.ticker("BTC");
        List<BithumbCandlesVO> candles = bithumbApiUtil.candles("BTC");
//        BithumbOrderbookVO orderbook = bithumbApiUtil.orderbook("BTC");

        return ResponseDataDTO.of(candles);
    }

    @Timer
    @GetMapping("/ctf/chart/bid")
    public ResponseDTO bid() {
        BithumbOrderbookVO orderbook = bithumbApiUtil.orderbook("BTC");

        return ResponseDataDTO.of(BithumbOrderbookDTO.fromBithumbOrderbookVO(orderbook));
    }

//    @PostMapping("/marketOrder")
//    public ResponseDTO marketOrder(@RequestBody InsMarketDTO insMarketDTO, UserEntity userEntity) {
//        orderService.marketOrder(insMarketDTO, userEntity);
//
//        return ResponseDTO.of(true, Code.OK);
//    }
}
