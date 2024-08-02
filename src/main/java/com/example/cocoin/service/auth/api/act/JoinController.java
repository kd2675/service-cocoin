package com.example.cocoin.service.auth.api.act;

import com.example.cocoin.service.auth.api.biz.JoinService;
import com.example.cocoin.service.auth.api.dto.JoinParamDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.response.base.dto.ResponseDTO;
import org.example.core.response.base.dto.ResponseDataDTO;
import org.example.core.response.base.vo.Code;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cocoin/api/auth")
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/ctf/join/user")
    public ResponseDTO join(@Valid @RequestBody JoinParamDTO joinParamDTO, BindingResult bindingResult) {
        if (joinService.valid(joinParamDTO, bindingResult)) {
            return ResponseDTO.of(false, Code.VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }

        joinService.joinUser(joinParamDTO);

        return ResponseDTO.of(true, Code.OK);
    }

    @PostMapping("/ctf/join/admin")
    public ResponseDTO joinAdmin(@Valid @RequestBody JoinParamDTO joinParamDTO, BindingResult bindingResult) {
        if (joinService.valid(joinParamDTO, bindingResult)) {
            return ResponseDTO.of(false, Code.VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }

        joinService.joinAdmin(joinParamDTO);

        return ResponseDTO.of(true, Code.OK);
    }

    @PostMapping("/ctf/join/social")
    public ResponseDTO joinSocial(@Valid @RequestBody JoinParamDTO joinParamDTO, BindingResult bindingResult) {
        if (joinService.valid(joinParamDTO, bindingResult)) {
            return ResponseDTO.of(false, Code.VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }

        joinService.joinSocial(joinParamDTO);

        return ResponseDTO.of(true, Code.OK);
    }

    @GetMapping("/ctf/join/selEmailCheck")
    public ResponseDataDTO joinIdCheck(@RequestParam(name = "userEmail") String email) {
        return ResponseDataDTO.of(joinService.duplEmail(email));
    }

    @GetMapping("/ctf/join/selNickCheck")
    public ResponseDataDTO joinNickCheck(@RequestParam(name = "userNick") String nick) {
        return ResponseDataDTO.of(joinService.duplNick(nick));
    }
}
