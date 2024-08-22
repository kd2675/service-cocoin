package com.example.cocoin.utils.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BithumbOrderbookUnitsDTO {
    private String askPrice;
    private String bidPrice;
    private String askSize;
    private String bidSize;

    public static BithumbOrderbookUnitsDTO fromBithumbOrderbookUnitsDTO(BithumbOrderbookUnitsVO bithumbOrderbookUnitsVO) {
        return new BithumbOrderbookUnitsDTO(
                bithumbOrderbookUnitsVO.getAskPrice(),
                bithumbOrderbookUnitsVO.getBidPrice(),
                bithumbOrderbookUnitsVO.getAskSize(),
                bithumbOrderbookUnitsVO.getBidSize()
        );
    }
}
