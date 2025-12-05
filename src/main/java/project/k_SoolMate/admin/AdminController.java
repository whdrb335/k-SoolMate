package project.k_SoolMate.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.request.CreateSoolRequest;
import project.k_SoolMate.domain.item.sool.request.UpdateSoolRequest;
import project.k_SoolMate.domain.item.sool.service.SoolService;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final SoolService soolService;
    private final UserService userService;

    /**
     * 전체 술 조회
     */
    @GetMapping("/items/sool")
    public Result<List<SoolDTO>> getAllSool() {
        List<SoolDTO> all = soolService.getAll();
        return new Result<>(all);
    }

    /**
     * 술 단건 조회
     */
    @GetMapping("/items/sool/{id}")
    public Result<SoolDTO> getSool(@PathVariable("id") Long soolId) {
        SoolDTO soolInfo = soolService.getSoolInfo(soolId);
        return new Result<>(soolInfo);
    }

    /**
     * 술 생성
     */
    @PostMapping("/items/sool")
    public Result<SoolDTO> createSool(@Validated @RequestBody CreateSoolRequest request) {
        SoolDTO sool = soolService.createSool(request);
        return new Result<>(sool);
    }

    /**
     * 술 업데이트
     */
    @PatchMapping("/items/sool/{id}")
    public Result<SoolDTO> updateSool(@PathVariable("id") Long id, @Validated @RequestBody UpdateSoolRequest request) {
        SoolDTO updateSool = soolService.updateSool(id, request);
        return new Result<>(updateSool);
    }

    /**
     * 술 삭제(softDelete)
     */
    @DeleteMapping("/items/sool/{id}")
    public Result<SoolDTO> deleteSool(@PathVariable("id") Long id) {
        SoolDTO soolDTO = soolService.deleteSool(id);
        return new Result<>(soolDTO);
    }

    /**
     * 전체 회원 조회
     */
    @GetMapping("/user/")
    public Result<List<UserDTO>> getAllUser() {
        List<UserDTO> allUser = userService.getAllUser();
        return new Result<>(allUser);
    }

    /**
     * 회원 한명 조회
     */
    @GetMapping("/user/{id}")
    public Result<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO myInfo = userService.getMyInfo(id);
        return new Result<>(myInfo);
    }


}
