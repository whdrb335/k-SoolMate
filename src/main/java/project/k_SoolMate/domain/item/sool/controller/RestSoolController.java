package project.k_SoolMate.domain.item.sool.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.service.SoolService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item/sool")
@Tag(name = "Sool API", description = "술 단건 조회 · 전체 조회 API")
public class RestSoolController {

    private final SoolService soolService;

    /**
     * 술 단건 조회
     */
    @Operation(
            summary = "술 단건 조회",
            description = "ID로 특정 술의 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public Result<SoolDTO> getSool(@PathVariable("id") Long id) {
        SoolDTO soolInfo = soolService.getSoolInfo(id);
        return new Result<>(soolInfo);
    }

    /**
     * 술 전체 조회
     */
    @Operation(
            summary = "술 전체 조회",
            description = "등록된 모든 술의 리스트를 조회합니다."
    )
    @GetMapping
    public Result<List<SoolDTO>> getAllSool() {
        List<SoolDTO> all = soolService.getAll();
        return new Result<>(all);
    }
}
