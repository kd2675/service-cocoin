package com.example.cocoin.common.health.act;

import com.example.cocoin.log.annotation.Timer;
import com.example.cocoin.log.annotation.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String health() {
        return "OK";
    }

}
