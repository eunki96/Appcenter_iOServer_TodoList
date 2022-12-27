package com.appcenter.todolistapi_02.controller;

import com.appcenter.todolistapi_02.dto.result.ResultDto;
import com.appcenter.todolistapi_02.dto.result.SignInResultDto;
import com.appcenter.todolistapi_02.dto.result.SignUpResultDto;
import com.appcenter.todolistapi_02.dto.member.MemberResponseDto;
import com.appcenter.todolistapi_02.dto.member.MemberSignInRequestDto;
import com.appcenter.todolistapi_02.dto.member.MemberSignUpRequestDto;
import com.appcenter.todolistapi_02.dto.member.MemberUpdateRequestDto;
import com.appcenter.todolistapi_02.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Member")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    //Sign-Up
    @ApiOperation("회원가입")
    @PostMapping("/sign-up")
    public SignUpResultDto signUp(@RequestBody @Validated MemberSignUpRequestDto memberSignUpRequestDto){
        log.info("[signUp] 회원가입 시작합니다. id : {}, password : ****, name : {}, role : {}", memberSignUpRequestDto.getMemberId(), memberSignUpRequestDto.getMemberId(), memberSignUpRequestDto.getRole());
        SignUpResultDto signUpResultDto = memberService.signUp(memberSignUpRequestDto);

        if (signUpResultDto.getCode() == 0)
            log.info("[signUp] 회원가입을 완료했습니다. id : {}", memberSignUpRequestDto.getMemberId());
        return signUpResultDto;
    }

    //Sign-In
    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    public SignUpResultDto signIn(@RequestBody @Validated MemberSignInRequestDto memberSignInRequestDto) throws RuntimeException{
        log.info("[signIn] 로그인을 시도합니다. id : {}, pw : ****", memberSignInRequestDto.getMemberId());
        SignUpResultDto signUpResultDto = memberService.signIn(memberSignInRequestDto);

        if (signUpResultDto.getCode() == 0) {
            SignInResultDto signInResultDto = (SignInResultDto) memberService.signIn(memberSignInRequestDto);
            log.info("[signIn] 로그인되었습니다. id : {}, token : {}", memberSignInRequestDto.getMemberId(), signInResultDto.getToken());
            return signInResultDto;
        }
        return signUpResultDto;
    }



    @ApiOperation("회원 전체 조회")
    @GetMapping
    public List<MemberResponseDto> readAll(){
        return memberService.readAll();
    }

    @ApiOperation("회원 단일 조회")
    @GetMapping("/{id}")
    public MemberResponseDto readMember(@PathVariable Long id){
        return memberService.readMember(id);
    }

    @ApiOperation("회원 수정")
    @PatchMapping("/{id}")
    public ResultDto updateMember(@PathVariable Long id, @RequestBody @Validated MemberUpdateRequestDto memberUpdateRequestDto){
        return memberService.updateMember(id, memberUpdateRequestDto);
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping("/{id}")
    public ResultDto deleteMember(@PathVariable Long id){
        return memberService.deleteMember(id);
    }

}
