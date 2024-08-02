package com.example.cocoin.utils.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BithumbCandlesVO {
    @JsonProperty("market")
    private String market;
    @JsonProperty("candle_date_time_utc")
    private LocalDateTime candleDateTimeUtc;
    @JsonProperty("candle_date_time_kst")
    private LocalDateTime candleDateTimeKst;
    @JsonProperty("opening_price")
    private String openingPrice;
    @JsonProperty("high_price")
    private String highPrice;
    @JsonProperty("low_price")
    private String lowPrice;
    @JsonProperty("trade_price")
    private String tradePrice;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("candle_acc_trade_price")
    private String candleAccTradePrice;
    @JsonProperty("candle_acc_trade_volume")
    private String candleAccTradeVolume;
    @JsonProperty("unit")
    private String unit;
}
