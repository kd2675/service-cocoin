package com.example.cocoin.utils;

import com.example.cocoin.utils.vo.BithumbCandlesVO;
import com.example.cocoin.utils.vo.BithumbOrderbookVO;
import com.example.cocoin.utils.vo.BithumbTickerVO;

public interface BithumbApiUtil {
    BithumbTickerVO ticker(String query);
    BithumbCandlesVO candles(String query);
    BithumbOrderbookVO orderbook(String query);
}
