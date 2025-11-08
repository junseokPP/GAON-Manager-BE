package com.back.gaon.member.controller;

import com.back.gaon.member.dto.MemberCreateRequest;
import com.back.gaon.member.dto.MemberResponse;
import com.back.gaon.member.dto.MemberUpdateRequest;
import com.back.gaon.member.entity.Member;
import com.back.gaon.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember (@RequestBody @Valid MemberCreateRequest request){
        MemberResponse createdMember = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers(){
        return ResponseEntity.ok().body(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@RequestParam Long id){
        return ResponseEntity.ok().body(memberService.getMember(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id,
                                                 @RequestBody @Valid MemberUpdateRequest request) {
        return ResponseEntity.ok(memberService.updateMember(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

}
