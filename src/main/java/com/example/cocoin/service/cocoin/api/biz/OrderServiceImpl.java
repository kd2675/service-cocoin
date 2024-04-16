package com.example.cocoin.service.cocoin.api.biz;

import com.example.cocoin.common.base.vo.Code;
import com.example.cocoin.common.exception.GeneralException;
import com.example.cocoin.service.auth.database.rep.jpa.user.UserRepository;
import com.example.cocoin.service.cocoin.api.dto.DelOrderDTO;
import com.example.cocoin.service.cocoin.api.dto.InsMarketDTO;
import com.example.cocoin.service.cocoin.api.dto.InsOrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.market.MarketEntity;
import com.example.cocoin.service.cocoin.database.rep.jpa.market.MarketRepository;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderEntity;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderRepository;
import com.example.cocoin.service.coin.database.rep.jpa.coin.CoinEntity;
import com.example.cocoin.service.coin.database.rep.jpa.coin.CoinREP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.database.auth.database.rep.jpa.user.UserEntity;
import org.example.database.auth.database.rep.jpa.wallet.WalletEntity;
import org.example.log.annotation.Log;
import org.example.log.annotation.LogOrder;
import org.example.log.annotation.Timer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MarketRepository marketRepository;

    private final CoinREP coinREP;

    @Override
    @Transactional
    @LogOrder
    public OrderDTO order(InsOrderDTO insOrderDTO, UserEntity userEntity) {
        if ("b".equals(insOrderDTO.getOrderSlct())) {
            this.orderBuy(insOrderDTO, userEntity);
        } else if ("s".equals(insOrderDTO.getOrderSlct())) {
            this.orderSell(insOrderDTO, userEntity);
        } else {
            throw new GeneralException(Code.VALIDATION_ERROR);
        }

        OrderEntity orderEntity = OrderEntity.builder()
                .orderSlct(insOrderDTO.getOrderSlct())
                .coinSlct(insOrderDTO.getCoinSlct())
                .marginSlct(insOrderDTO.getMarginSlct())
                .margin(insOrderDTO.getMargin())
                .price(insOrderDTO.getPrice())
                .cnt(insOrderDTO.getCnt())
                .userEntity(userEntity)
                .build();
        OrderEntity save = orderRepository.save(orderEntity);

        return OrderDTO.of(save);
    }

    private void orderBuy(InsOrderDTO insOrderDTO, UserEntity userEntity) {
        orderRepository.findByUserEntityAndOrderSlctAndCoinSlctAndMarginSlct(
                        userEntity,
                        insOrderDTO.getOrderSlct(),
                        insOrderDTO.getCoinSlct(),
                        insOrderDTO.getMarginSlct()
                ).stream()
                .findAny()
                .ifPresent(
                        v -> {
                            if (Integer.compare(v.getMargin(), insOrderDTO.getMargin()) != 0) {
                                throw new GeneralException(Code.BAD_REQUEST);
                            }
                        }
                );

        marketRepository.findByUserEntityInAndCoinSlctInAndMarginSlctIn(
                Collections.singletonList(userEntity),
                Collections.singletonList(insOrderDTO.getCoinSlct()),
                Collections.singletonList(insOrderDTO.getMarginSlct())
        ).ifPresent(
                v -> {
                    if (Integer.compare(v.getMargin(), insOrderDTO.getMargin()) != 0) {
                        throw new GeneralException(Code.BAD_REQUEST);
                    }
                }
        );

        double fullPrice = insOrderDTO.getPrice() * insOrderDTO.getCnt();
        BigDecimal pointDec = new BigDecimal(userEntity.getWalletEntity().getPoint());
        BigDecimal fullPriceDec = new BigDecimal(fullPrice);
        if (pointDec.compareTo(fullPriceDec) < 0) {
            throw new GeneralException(Code.NOT_ENOUGH_POINT);
        }

        WalletEntity walletEntity = userEntity.getWalletEntity();
        walletEntity.buy(fullPrice);
        userEntity.setWalletEntity(walletEntity);
        userRepository.save(userEntity);
    }

    private void orderSell(InsOrderDTO insOrderDTO, UserEntity userEntity) {
        Double orderCnt = orderRepository.findByUserEntityAndOrderSlctAndCoinSlctAndMarginSlct(
                        userEntity,
                        insOrderDTO.getOrderSlct(),
                        insOrderDTO.getCoinSlct(),
                        insOrderDTO.getMarginSlct()
                ).stream()
                .map(OrderEntity::getCnt)
                .reduce(Double::sum)
                .orElse(0D);

        MarketEntity marketEntity = marketRepository.findByUserEntityInAndCoinSlctInAndMarginSlctIn(
                Collections.singletonList(userEntity),
                Collections.singletonList(insOrderDTO.getCoinSlct()),
                Collections.singletonList(insOrderDTO.getMarginSlct())
        ).orElseThrow(() -> new GeneralException(Code.BAD_REQUEST));

        if (Double.compare(orderCnt + insOrderDTO.getCnt(), marketEntity.getCnt()) > 0) {
            throw new GeneralException(Code.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    @LogOrder
    public void cancel(DelOrderDTO delOrderDTO, UserEntity userEntity) {

        OrderEntity orderEntity = orderRepository.findByIdIn(
                Collections.singletonList(
                        delOrderDTO.getId()
                )
        ).orElseThrow(
                () -> new GeneralException(Code.NO_SEARCH_ORDER)
        );

        if (!Objects.equals(userEntity.getId(), orderEntity.getUserEntity().getId())) {
            throw new GeneralException(Code.BAD_REQUEST);
        }

        orderRepository.delete(orderEntity);

        double fullPrice = orderEntity.getPrice() * orderEntity.getCnt();

        WalletEntity walletEntity = userEntity.getWalletEntity();
        walletEntity.sell(fullPrice);
        userEntity.setWalletEntity(walletEntity);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void marketOrder(InsMarketDTO insMarketDTO, UserEntity userEntity) {
        CoinEntity coinEntity = coinREP.findTopByOrderByIdDesc();
        double coinPrice = Double.parseDouble(coinEntity.getClosingPrice());

        double fullPrice = coinPrice * insMarketDTO.getCnt();

        if ("b".equals(insMarketDTO.getOrderSlct()) && userEntity.getWalletEntity().getPoint() < fullPrice) {
            throw new GeneralException(Code.NOT_ENOUGH_POINT);
        }

        marketRepository.findByUserEntityInAndCoinSlctInAndMarginSlctIn(
                Collections.singletonList(userEntity),
                Collections.singletonList(insMarketDTO.getCoinSlct()),
                Collections.singletonList(insMarketDTO.getMarginSlct())
        ).ifPresentOrElse(
                marketEntity -> {
                    if ("b".equals(insMarketDTO.getOrderSlct())) {
                        marketAdjust(insMarketDTO, userEntity, marketEntity, coinPrice);
                    } else if ("s".equals(insMarketDTO.getOrderSlct())) {
                        marketSell(insMarketDTO, userEntity, marketEntity, coinPrice);
                    }
                },
                () -> {
                    if ("b".equals(insMarketDTO.getOrderSlct())) {
                        marketBuy(insMarketDTO, userEntity, coinPrice);
                    } else if ("s".equals(insMarketDTO.getOrderSlct())) {
                        throw new GeneralException(Code.BAD_REQUEST);
                    }
                }
        );
    }

    private void marketAdjust(InsMarketDTO insMarketDTO, UserEntity userEntity, MarketEntity marketEntity, Double coinPrice) {
        if (Integer.compare(insMarketDTO.getMargin(), marketEntity.getMargin()) != 0) {
            throw new GeneralException(Code.BAD_REQUEST);
        }

        Integer margin = marketEntity.getMargin();
        Double price = marketEntity.getPrice();
        Double cnt = marketEntity.getCnt();
        Double cleanPrice = marketEntity.getCleanPrice();

        double oldFullPrice = price * cnt;
        double orderFullPrice = coinPrice * insMarketDTO.getCnt();

        double newFullPrice = oldFullPrice + orderFullPrice;
        double newCnt = cnt + insMarketDTO.getCnt();
        double newPrice = newFullPrice / newCnt;

        double newCleanPrice;
        String marginSlct = marketEntity.getMarginSlct();
        if ("l".equals(marginSlct)) {
            newCleanPrice = newPrice - newPrice * (((double) 100 / margin) / 100);
        } else if ("s".equals(marginSlct)) {
            newCleanPrice = newPrice + newPrice * (((double) 100 / margin) / 100);
        } else {
            throw new GeneralException(Code.BAD_REQUEST);
        }

        marketEntity.marketAdjust(newPrice, newCnt, newCleanPrice);

        marketRepository.save(marketEntity);

        WalletEntity walletEntity = userEntity.getWalletEntity();
        walletEntity.buy(orderFullPrice);
        userEntity.setWalletEntity(walletEntity);
        userRepository.save(userEntity);
    }

    private void marketSell(InsMarketDTO insMarketDTO, UserEntity userEntity, MarketEntity marketEntity, Double coinPrice) {
        if (Integer.compare(insMarketDTO.getMargin(), marketEntity.getMargin()) != 0) {
            throw new GeneralException(Code.BAD_REQUEST);
        }

        Integer margin = marketEntity.getMargin();
        Double price = marketEntity.getPrice();
        Double cnt = marketEntity.getCnt();
        String marginSlct = marketEntity.getMarginSlct();

        double orderFullPrice = coinPrice * insMarketDTO.getCnt();

        if (Double.compare(insMarketDTO.getCnt(), marketEntity.getCnt()) > 0) {
            throw new GeneralException(Code.BAD_REQUEST);
        } else if (Double.compare(insMarketDTO.getCnt(), marketEntity.getCnt()) == 0) {
            marketRepository.delete(marketEntity);
        } else {
            marketEntity.sell(cnt - insMarketDTO.getCnt());
            marketRepository.save(marketEntity);
        }

        Double resultPrice;
        if ("l".equals(marginSlct)) {
            resultPrice = (marketEntity.getPrice() * marketEntity.getCnt()) + ((coinPrice - marketEntity.getPrice()) * marketEntity.getMargin() * insMarketDTO.getCnt());
        } else if ("s".equals(marginSlct)) {
            resultPrice = (marketEntity.getPrice() * marketEntity.getCnt()) + ((marketEntity.getPrice() - coinPrice) * marketEntity.getMargin() * insMarketDTO.getCnt());
        } else {
            throw new GeneralException(Code.BAD_REQUEST);
        }

        WalletEntity walletEntity = userEntity.getWalletEntity();
        walletEntity.sell(resultPrice);
        userEntity.setWalletEntity(walletEntity);
        userRepository.save(userEntity);
    }

    private void marketBuy(InsMarketDTO insMarketDTO, UserEntity userEntity, Double coinPrice) {
        double newCleanPrice;
        String marginSlct = insMarketDTO.getMarginSlct();
        if ("l".equals(marginSlct)) {
            newCleanPrice = coinPrice - coinPrice * (((double) 100 / insMarketDTO.getMargin()) / 100);
        } else if ("s".equals(marginSlct)) {
            newCleanPrice = coinPrice + coinPrice * (((double) 100 / insMarketDTO.getMargin()) / 100);
        } else {
            throw new GeneralException(Code.BAD_REQUEST);
        }

        MarketEntity marketEntity = MarketEntity.builder()
                .coinSlct(insMarketDTO.getCoinSlct())
                .marginSlct(insMarketDTO.getMarginSlct())
                .margin(insMarketDTO.getMargin())
                .price(coinPrice)
                .cnt(insMarketDTO.getCnt())
                .cleanPrice(newCleanPrice)
                .userEntity(userEntity)
                .build();

        marketRepository.save(marketEntity);

        WalletEntity walletEntity = userEntity.getWalletEntity();
        walletEntity.buy(coinPrice * insMarketDTO.getCnt());
        userEntity.setWalletEntity(walletEntity);
        userRepository.save(userEntity);
    }
}
