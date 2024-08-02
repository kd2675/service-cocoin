package com.example.cocoin.utils.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BithumbOrderbookUnitsVO {
    @JsonProperty("ask_price")
    private String askPrice;
    @JsonProperty("bid_price")
    private String bidPrice;
    @JsonProperty("ask_size")
    private String askSize;
    @JsonProperty("bid_size")
    private String bidSize;
}
