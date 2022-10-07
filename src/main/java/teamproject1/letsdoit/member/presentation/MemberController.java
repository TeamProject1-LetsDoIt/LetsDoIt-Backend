package teamproject1.letsdoit.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorResponse;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.member.presentation.dto.FindUserByEmailReq;

@Tag(name = "Users", description = "Users API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "유저 정보 확인", description = "현재 접속된 유저정보를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 확인 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Member.class))}),
            @ApiResponse(responseCode = "400", description = "유저 확인 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/me")
    public ResponseEntity<?> me(
            @Parameter(description = "Schema => FindUserReq", required = true) @Validated @RequestBody FindUserByEmailReq findUserByEmailReq
    ) {
        return memberService.me(findUserByEmailReq.getEmail());
    }
}
