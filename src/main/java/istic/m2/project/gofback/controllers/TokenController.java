package istic.m2.project.gofback.controllers;

import istic.m2.project.gofback.controllers.dto.ResponseDto;
import istic.m2.project.gofback.controllers.dto.TokenRefreshInDto;
import istic.m2.project.gofback.controllers.dto.TokenRefreshOutDto;
import istic.m2.project.gofback.exceptions.BusinessException;
import istic.m2.project.gofback.services.RefreshJwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/token")
@RequiredArgsConstructor
public class TokenController {
    private final RefreshJwtTokenService refreshJwtTokenService;

    @PostMapping(path = "refreshToken")
    public ResponseEntity<ResponseDto<TokenRefreshOutDto>> refreshToken(@Valid @RequestBody TokenRefreshInDto tokenRefreshInDto) throws BusinessException {

        return ResponseEntity.ok(new ResponseDto<>(refreshJwtTokenService.refreshToken(tokenRefreshInDto.refreshToken())));
    }

}
