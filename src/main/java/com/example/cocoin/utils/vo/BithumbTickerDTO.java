package com.example.cocoin.utils.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BithumbTickerDTO {
    private String market;
    private String tradeDate;
    private String tradeTime;
    private String tradeDateKst;
    private String tradeTimeKst;
    private String tradeTimestamp;

    private String openingPrice;
    private String highPrice;
    private String lowPrice;
    private String tradePrice;
    private String prevClosingPrice;
    private String change;
    private String changePrice;
    private String changeRate;
    private String signedChangePrice;
    private String signedChangeRate;
    private String tradeVolume;
    private String accTradePrice;
    private String accTradePrice24h;
    private String accTradeVolume;
    private String accTradeVolume24h;
    private String highest52WeekPrice;
    private String highest52WeekDate;
    private String lowest52WeekPrice;
    private String lowest52WeekDate;
    private String timestamp;

    public static BithumbTickerDTO fromBithumbTickerVO(BithumbTickerVO bithumbTickerVO) {
        return new BithumbTickerDTO(
                bithumbTickerVO.getMarket(),
                bithumbTickerVO.getTradeDate(),
                bithumbTickerVO.getTradeTime(),
                bithumbTickerVO.getTradeDateKst(),
                bithumbTickerVO.getTradeTimeKst(),
                bithumbTickerVO.getTradeTimestamp(),
                bithumbTickerVO.getOpeningPrice(),
                bithumbTickerVO.getHighPrice(),
                bithumbTickerVO.getLowPrice(),
                bithumbTickerVO.getTradePrice(),
                bithumbTickerVO.getPrevClosingPrice(),
                bithumbTickerVO.getChange(),
                bithumbTickerVO.getChangePrice(),
                bithumbTickerVO.getChangeRate(),
                bithumbTickerVO.getSignedChangePrice(),
                bithumbTickerVO.getSignedChangeRate(),
                bithumbTickerVO.getTradeVolume(),
                bithumbTickerVO.getAccTradePrice(),
                bithumbTickerVO.getAccTradePrice24h(),
                bithumbTickerVO.getAccTradeVolume(),
                bithumbTickerVO.getAccTradeVolume24h(),
                bithumbTickerVO.getHighest52WeekPrice(),
                bithumbTickerVO.getHighest52WeekDate(),
                bithumbTickerVO.getLowest52WeekPrice(),
                bithumbTickerVO.getLowest52WeekDate(),
                bithumbTickerVO.getTimestamp()
        );
    }
}
