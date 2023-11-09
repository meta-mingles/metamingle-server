package com.mingles.metamingle.member.query.application.controller;

import com.mingles.metamingle.common.ApiResponse;
import com.mingles.metamingle.member.query.application.dto.request.LoginRequest;
import com.mingles.metamingle.member.query.application.dto.response.LoginResponse;
import com.mingles.metamingle.member.query.application.service.MemberQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 Command API")
@RestController("/member")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryService memberQueryService;


    @GetMapping("/member/login")
    public ResponseEntity<ApiResponse> Login(@RequestBody LoginRequest request) {

        LoginResponse response = memberQueryService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("성공적으로 로그인되었습니다." , response)
        );
    }

    @GetMapping("/member/test")
    public ResponseEntity<ApiResponse> Login(@RequestHeader String Authentication) {

        Long response = memberQueryService.getMemberInfo(Authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("성공적으로 로그인되었습니다." , response)
        );
    }
}