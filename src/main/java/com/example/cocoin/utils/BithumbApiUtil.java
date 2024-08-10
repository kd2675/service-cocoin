package com.example.cocoin.utils;

import com.example.cocoin.utils.vo.BithumbCandlesVO;
import com.example.cocoin.utils.vo.BithumbOrderbookVO;
import com.example.cocoin.utils.vo.BithumbTickerVO;

import java.util.List;

public interface BithumbApiUtil {
    BithumbTickerVO ticker(String query);
    List<BithumbCandlesVO> candles(String query);
    BithumbOrderbookVO orderbook(String query);
}
