package com.example.cocoin.utils.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BithumbOrderbookDTO {
    private String market;
    private String timestamp;
    private String totalAskSize;
    private String totalBidSize;
    private List<BithumbOrderbookUnitsDTO> orderbookUnits;

    public static BithumbOrderbookDTO fromBithumbOrderbookVO(BithumbOrderbookVO bithumbOrderbookVO){
        return new BithumbOrderbookDTO(
                bithumbOrderbookVO.getMarket(),
                bithumbOrderbookVO.getTimestamp(),
                bithumbOrderbookVO.getTotalAskSize(),
                bithumbOrderbookVO.getTotalBidSize(),
                bithumbOrderbookVO.getOrderbookUnits().stream().map(
                        BithumbOrderbookUnitsDTO::fromBithumbOrderbookUnitsDTO
                ).toList()
        );
    }
}
