package com.example.cocoin.service.cocoin.api.biz;

import com.example.cocoin.service.cocoin.api.dto.DelOrderDTO;
import com.example.cocoin.service.cocoin.api.dto.InsOrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import org.example.database.database.auth.entity.UserEntity;

public interface OrderService {
    OrderDTO order(InsOrderDTO insOrderDTO, UserEntity userEntity);
    void cancel(DelOrderDTO delOrderDTO, UserEntity userEntity);
//    void marketOrder(InsMarketDTO insMarketDTO, UserEntity userEntity);
}
