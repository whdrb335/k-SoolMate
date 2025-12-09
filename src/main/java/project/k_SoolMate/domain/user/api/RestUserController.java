package project.k_SoolMate.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.common.interceptor.SessionUserDTO;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.request.*;
import project.k_SoolMate.domain.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User API", description = "회원가입 · 로그인 · 회원정보 관리 API")
public class RestUserController {

    private final UserService userService;

    /**
     * 회원 생성
     */
    @Operation(
            summary = "회원 가입",
            description = "새로운 회원을 생성합니다."
    )
    @PostMapping("/save")
    public Result<UserDTO> createUser(@Validated @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return new Result<>(user);
    }

    /**
     * 로그인
     */
    @Operation(
            summary = "로그인",
            description = "로그인 후 세션에 회원 정보를 저장합니다."
    )
    @PostMapping("/login")
    public Result<UserDTO> login(
            @Validated @RequestBody LoginUserRequest request,
            HttpServletRequest httpServletRequest) {

        UserDTO loginUser = userService.login(request.getLoginId(), request.getLoginPw());

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("LOGIN_USER", new SessionUserDTO(
                loginUser.getId(),
                loginUser.getLoginId(),
                loginUser.getRole()
        ));

        return new Result<>(loginUser);
    }

    /**
     * 로그아웃
     */
    @Operation(
            summary = "로그아웃",
            description = "현재 세션을 무효화합니다."
    )
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            session.invalidate();
        }
        return new Result<>("로그아웃 되었습니다.");
    }

    /**
     * 비밀번호 확인
     */
    @Operation(
            summary = "비밀번호 확인",
            description = "현재 로그인한 사용자의 비밀번호가 맞는지 확인합니다."
    )
    @PostMapping("/isMatchPasswd")
    public Result<String> isMatchPasswd(
            @Validated @RequestBody isMatchPasswdRequest request,
            HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        userService.isMatchPasswd(loginUser.getId(), request.getPasswd());

        return new Result<>("일치합니다.");
    }

    /**
     * 비밀번호 변경
     */
    @Operation(
            summary = "비밀번호 변경",
            description = "기존 비밀번호 검증 후 새 비밀번호로 변경합니다."
    )
    @PatchMapping("/updatePasswd")
    public Result<String> updatePasswd(
            @Validated @RequestBody UpdatePasswdRequest request,
            HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        userService.changePasswd(loginUser.getId(), request.getOldPasswd(), request.getNewPasswd());

        return new Result<>("변경되었습니다.");
    }

    /**
     * 유저 업데이트
     */
    @Operation(
            summary = "회원 정보 수정",
            description = "로그인한 사용자의 전화번호, 이메일, 주소를 수정합니다."
    )
    @PatchMapping("/updateMyInfo")
    public Result<UserDTO> updateMyInfo(
            @Validated @RequestBody UpdateUserRequest request,
            HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        UserDTO updateDTO = (UserDTO) session.getAttribute("LOGIN_USER");

        UserDTO userDTO = userService.updateMyInfo(
                updateDTO.getId(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getAddress()
        );

        session.setAttribute("LOGIN_USER", userDTO);

        return new Result<>(userDTO);
    }

    /**
     * 내 정보 조회
     */
    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 회원의 상세 정보를 조회합니다."
    )
    @GetMapping("/myInfo")
    public Result<UserDTO> myInfo(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        UserDTO myInfo = userService.getMyInfo(loginUser.getId());
        return new Result<>(myInfo);
    }

    /**
     * 전체 회원 조회
     */
    @Operation(
            summary = "전체 회원 조회 (ADMIN 전용)",
            description = "관리자가 모든 회원 정보를 조회합니다."
    )
    @GetMapping("/getAllUser")
    public Result<List<UserDTO>> getAllUser() {
        List<UserDTO> allUser = userService.getAllUser();
        return new Result<>(allUser);
    }

    /**
     * 회원 삭제
     */
    @Operation(
            summary = "회원 삭제(Soft Delete)",
            description = "현재 로그인한 사용자를 소프트 삭제 처리합니다."
    )
    @DeleteMapping("/delete")
    public Result<String> deleteUser(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

        userService.deleteUser(loginUser.getId());

        return new Result<>("삭제되었습니다.");
    }

}
