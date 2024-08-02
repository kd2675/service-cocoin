package com.example.cocoin.utils.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BithumbOrderbookVO {
    @JsonProperty("market")
    private String market;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("total_ask_size")
    private String totalAskSize;
    @JsonProperty("total_bid_size")
    private String totalBidSize;
    @JsonProperty("orderbook_units")
    private List<BithumbOrderbookUnitsVO> orderbookUnits;
}
