package project.k_SoolMate.domain.item.sool.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.request.CreateSoolRequest;
import project.k_SoolMate.domain.item.sool.request.UpdateSoolRequest;
import project.k_SoolMate.domain.item.sool.service.SoolService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item/sool")
public class RestSoolController {

    private final SoolService soolService;

    /**
     * 술 생성
     */
    @PostMapping("/create")
    public Result<SoolDTO> createSool(@Validated @RequestBody CreateSoolRequest request) {
        SoolDTO sool = soolService.createSool(request);
        return new Result<>(sool);
    }

    /**
     * 술 업데이트
     */
    @PatchMapping("/{id}")
    public Result<SoolDTO> updateSool(@PathVariable("id") Long id, @Validated @RequestBody UpdateSoolRequest request) {
        SoolDTO updateSool = soolService.updateSool(id, request);
        return new Result<>(updateSool);
    }

    /**
     * 술 단건 조회
     */
    @GetMapping("/{id}")
    public Result<SoolDTO> getSool(@PathVariable("id") Long id) {
        SoolDTO soolInfo = soolService.getSoolInfo(id);
        return new Result<>(soolInfo);
    }

    /**
     * 술 전체조회
     */
    @GetMapping()
    public Result<List<SoolDTO>> getAllSool() {
        List<SoolDTO> all = soolService.getAll();
        return new Result<>(all);
    }

    /**
     * 술 삭제(softDelete)
     */
    @DeleteMapping("/{id}")
    public Result<SoolDTO> deleteSool(@PathVariable("id") Long id) {
        SoolDTO soolDTO = soolService.deleteSool(id);
        return new Result<>(soolDTO);
    }
}
