package com.example.cocoin.utils;

import com.example.cocoin.utils.vo.BithumbCandlesVO;
import com.example.cocoin.utils.vo.BithumbOrderbookVO;
import com.example.cocoin.utils.vo.BithumbTickerVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BithumbApiUtilImpl implements BithumbApiUtil {
    private static final String NAVER_API_URL = "https://openapi.naver.com";
    private static final String NAVER_API_PATH = "/v1/search/news.json";
    private static final String NAVER_API_CLIENT_ID = "97avHwhY7N2bJ4RysxAx";
    private static final String NAVER_API_CLIENT_SECRET = "74r7XpIXPi";

    private final RestTemplate restTemplate;

    @Override
    public BithumbTickerVO ticker(String query) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.bithumb.com")
                .path("/v1/ticker")
                .queryParam("markets", "KRW-"+query)
                .encode()
                .build()
                .toUri();

        ArrayList forObject = restTemplate.getForObject(uri, ArrayList.class);

        if (forObject != null && !forObject.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            BithumbTickerVO bithumbDataVO = objectMapper.convertValue(Objects.requireNonNull(forObject).get(0), BithumbTickerVO.class);

            return bithumbDataVO;
        }

        return null;
    }
    @Override
    public BithumbCandlesVO candles(String query) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.bithumb.com")
                .path("/v1/candles/minutes/1")
                .queryParam("market", "KRW-"+query)
                .queryParam("count", "1")
                .encode()
                .build()
                .toUri();

        ArrayList forObject = restTemplate.getForObject(uri, ArrayList.class);

        if (forObject != null && !forObject.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            BithumbCandlesVO bithumbDataVO = objectMapper.convertValue(Objects.requireNonNull(forObject).get(0), BithumbCandlesVO.class);

            return bithumbDataVO;
        }

        return null;
    }

    @Override
    public BithumbOrderbookVO orderbook(String query) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.bithumb.com")
                .path("/v1/orderbook")
                .queryParam("markets", "KRW-"+query)
                .encode()
                .build()
                .toUri();

        ArrayList forObject = restTemplate.getForObject(uri, ArrayList.class);

        if (forObject != null && !forObject.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            BithumbOrderbookVO bithumbDataVO = objectMapper.convertValue(Objects.requireNonNull(forObject).get(0), BithumbOrderbookVO.class);

            return bithumbDataVO;
        }

        return null;
    }
}
