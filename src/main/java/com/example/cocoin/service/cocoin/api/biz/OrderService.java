package com.example.cocoin.service.cocoin.api.biz;

import com.example.cocoin.service.cocoin.api.dto.DelOrderDTO;
import com.example.cocoin.service.cocoin.api.dto.InsMarketDTO;
import com.example.cocoin.service.cocoin.api.dto.InsOrderDTO;
import com.example.cocoin.service.cocoin.database.rep.jpa.order.OrderDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface OrderService {
    OrderDTO order(InsOrderDTO insOrderDTO, UserDetails userDetails);
    void cancel(DelOrderDTO delOrderDTO, UserDetails userDetails);
    void marketOrder(InsMarketDTO insMarketDTO, UserDetails userDetails);
}
