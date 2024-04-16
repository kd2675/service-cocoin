package com.example.cocoin.service.cocoin.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class InsOrderDTO {
    //b, s
    @NotNull
    private String orderSlct;
    //BTC
    @NotNull
    private String coinSlct;
    //l, s
    @NotNull
    private String marginSlct;
    @NotNull
    private Integer margin;
    @NotNull
    private Double cnt;
    @NotNull
    private Double price;
}
