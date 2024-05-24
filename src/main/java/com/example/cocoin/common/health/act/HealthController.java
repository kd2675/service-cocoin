package com.example.cocoin.common.health.act;

import com.example.cocoin.common.base.dto.ResponseDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.log.annotation.Log;
import org.example.log.annotation.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class HealthController {

    @GetMapping("/ctf/health")
    @Log
    @Timer
    public ResponseDataDTO<String> health() {
        return ResponseDataDTO.of("OK");
    }

}
