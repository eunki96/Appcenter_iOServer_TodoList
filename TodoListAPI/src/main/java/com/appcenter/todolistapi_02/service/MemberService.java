package com.appcenter.todolistapi_02.service;

import com.appcenter.todolistapi_02.common.StatusCode;
import com.appcenter.todolistapi_02.domain.Member;
import com.appcenter.todolistapi_02.domain.Todo;
import com.appcenter.todolistapi_02.dto.result.ResultDto;
import com.appcenter.todolistapi_02.dto.result.SignInResultDto;
import com.appcenter.todolistapi_02.dto.result.SignUpResultDto;
import com.appcenter.todolistapi_02.dto.member.MemberResponseDto;
import com.appcenter.todolistapi_02.dto.member.MemberSignInRequestDto;
import com.appcenter.todolistapi_02.dto.member.MemberSignUpRequestDto;
import com.appcenter.todolistapi_02.dto.member.MemberUpdateRequestDto;
import com.appcenter.todolistapi_02.dto.todo.TodoResponseDto;
import com.appcenter.todolistapi_02.exception.CustomException;
import com.appcenter.todolistapi_02.repository.MemberRepository;
import com.appcenter.todolistapi_02.security.JwtTokenProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    //Sign-Up
    public SignUpResultDto signUp(MemberSignUpRequestDto memberSignUpRequestDto){

        SignUpResultDto signUpResultDto = new SignUpResultDto();

        log.info("[회원가입 - 아이디 중복 검사]");
        if (validateMemberId(memberSignUpRequestDto)) {
            signUpResultDto.setCode(StatusCode.ID_CONFLICT.getHttpStatus().value());
            signUpResultDto.setMsg(StatusCode.ID_CONFLICT.getMessage());
            return signUpResultDto;
        }
        log.info("[아이디 중복 검사 통과]");

        Member member;

        if (memberSignUpRequestDto.getRole().equalsIgnoreCase("admin")) {
            member = Member.builder()
                    .memberId(memberSignUpRequestDto.getMemberId())
                    .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                    .name(memberSignUpRequestDto.getName())
                    .email(memberSignUpRequestDto.getEmail())
                    .age(memberSignUpRequestDto.getAge())
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            member = Member.builder()
                    .memberId(memberSignUpRequestDto.getMemberId())
                    .password(passwordEncoder.encode(memberSignUpRequestDto.getPassword()))
                    .name(memberSignUpRequestDto.getName())
                    .email(memberSignUpRequestDto.getEmail())
                    .age(memberSignUpRequestDto.getAge())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        Member registeredMember = memberRepository.save(member);



        if(! registeredMember.getName().isEmpty()){
            signUpResultDto.setCode(StatusCode.CREATED.getHttpStatus().value());
            signUpResultDto.setMsg(StatusCode.CREATED.getMessage());
        } else {
            signUpResultDto.setCode(StatusCode.INTERNAL_SERVER_ERROR.getHttpStatus().value());
            signUpResultDto.setMsg(StatusCode.INTERNAL_SERVER_ERROR.getMessage());
        }

        return signUpResultDto;
    }

    private boolean validateMemberId(MemberSignUpRequestDto memberSignUpRequestDto){
        return memberRepository.existsByMemberId(memberSignUpRequestDto.getMemberId());
    }

    //Sign-In
    public SignUpResultDto signIn(MemberSignInRequestDto memberSignInRequestDto) throws RuntimeException{
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if(! memberRepository.existsByMemberId(memberSignInRequestDto.getMemberId())){
            signUpResultDto.setCode(StatusCode.ID_BAD_REQUEST.getHttpStatus().value());
            signUpResultDto.setMsg(StatusCode.ID_BAD_REQUEST.getMessage());
            return signUpResultDto;
        }
        log.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        Member member = memberRepository.getByMemberId(memberSignInRequestDto.getMemberId()).get();

        log.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(memberSignInRequestDto.getPassword(), member.getPassword())) {
            signUpResultDto.setCode(StatusCode.PASSWORD_BAD_REQUEST.getHttpStatus().value());
            signUpResultDto.setMsg(StatusCode.PASSWORD_BAD_REQUEST.getMessage());
            return signUpResultDto;
        }
        log.info("[getSignInResult] 패스워드 일치");

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(member.getMemberId()), member.getRoles()))
                .build();
        log.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        signInResultDto.setCode(StatusCode.OK.getHttpStatus().value());
        signInResultDto.setMsg((StatusCode.OK.getMessage()));
        return signInResultDto;
    }


    @Transactional
    @JsonIgnore
    public List<MemberResponseDto> readAll(){
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

            //@JsonIgnore을 쓰니 필요 없어졌당..?
            List<Todo> todoList = member.getTodoList();
            for (Todo todo : todoList){
                TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
                todoResponseDtoList.add(todoResponseDto);
            }
            MemberResponseDto memberResponseDto = new MemberResponseDto(member, todoResponseDtoList);
            memberResponseDtoList.add( memberResponseDto );
        }
        return memberResponseDtoList;
    }

    @Transactional
    public MemberResponseDto readMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(StatusCode.MEMBER_NOT_FOUND)
        );
        List<TodoResponseDto> todoResponseDtoForMemberList = new ArrayList<>();
        List<Todo> todoList = member.getTodoList();
        for( Todo todo : todoList){
            TodoResponseDto todoResponseDtoForMember = new TodoResponseDto(todo);
            todoResponseDtoForMemberList.add(todoResponseDtoForMember);
        }
        MemberResponseDto memberResponseDto = new MemberResponseDto(member, todoResponseDtoForMemberList);
        return memberResponseDto;
    }


    public ResultDto updateMember(Long memberId, MemberUpdateRequestDto memberUpdateRequestDto){

        ResultDto resultDto = new ResultDto();

        log.info("[updateMember] 회원 수정을 시작합니다");
        log.info("[id] 입력받은 PathVariable : {}", memberId);
        Member oldMember = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(StatusCode.MEMBER_NOT_FOUND)
        );

        oldMember.setAge(memberUpdateRequestDto.getAge());
        oldMember.setName(memberUpdateRequestDto.getName());

        memberRepository.save(oldMember);
        log.info("[updateMember] 회원 수정을 완료했습니다");

        resultDto.setCode(StatusCode.OK.getHttpStatus().value());
        resultDto.setMsg(StatusCode.OK.getMessage());

        return resultDto;
    }

    public ResultDto deleteMember(Long id){

        ResultDto resultDto = new ResultDto();

        memberRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusCode.MEMBER_NOT_FOUND)
        );
        memberRepository.deleteById(id);

        resultDto.setCode(StatusCode.OK.getHttpStatus().value());
        resultDto.setMsg((StatusCode.OK.getMessage()));

        return resultDto;
    }

}
