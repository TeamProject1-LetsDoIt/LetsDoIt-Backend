package teamproject1.letsdoit.group.presentation;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorResponse;
import teamproject1.letsdoit.group.application.GroupService;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.group.presentation.dto.SaveGroupReq;

@Tag(name = "Groups", description = "Groups API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "그룹 저장", description = "그룹을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 생성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Group.class))}),
            @ApiResponse(responseCode = "400", description = "그룹 생성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/group")
    public ResponseEntity<?> saveGroup(
            @Parameter(description = "Schema => SaveGroupReq", required = true) @Validated @RequestBody SaveGroupReq saveGroupReq
            ) {
        return groupService.saveGroup(saveGroupReq);
    }
}
