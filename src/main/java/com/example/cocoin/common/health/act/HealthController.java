package com.example.cocoin.common.health.act;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.response.base.dto.ResponseDTO;
import org.example.core.response.base.dto.ResponseDataDTO;
import org.example.core.response.base.exception.GeneralException;
import org.example.core.response.base.vo.Code;
import org.example.log.annotation.Log;
import org.example.log.annotation.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cocoin")
public class HealthController {

    @GetMapping("/ctf/health")
    @Log
    @Timer
    public ResponseDTO health() {
        throw new GeneralException(Code.TEST_1);
//        return ResponseDTO.of(true, Code.OK);
    }

}
