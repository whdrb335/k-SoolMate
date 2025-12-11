package project.k_SoolMate.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.common.jwt.JwtTokenProvider;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.request.*;
import project.k_SoolMate.domain.user.response.LoginResponse;
import project.k_SoolMate.domain.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User API", description = "회원가입 · 로그인 · JWT 기반 회원 관리 API")
public class RestUserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // ===============================
    // 회원 가입
    // ===============================
    @Operation(summary = "회원 가입", description = "새로운 회원을 생성합니다.")
    @PostMapping("/save")
    public Result<UserDTO> createUser(@Validated @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return new Result<>(user);
    }

    // ===============================
    // 로그인 (JWT 발급)
    // ===============================
    @Operation(summary = "로그인", description = "로그인 후 AccessToken + RefreshToken을 발급합니다.")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginUserRequest request) {

        // 1) 로그인 검증
        UserDTO loginUser = userService.login(request.getLoginId(), request.getLoginPw());

        // 2) 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(loginUser.getId(), loginUser.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        // 3) RefreshToken 저장
        userService.updateRefreshToken(loginUser.getId(), refreshToken);

        // 4) 반환
        LoginResponse response = new LoginResponse(
                loginUser.getId(),
                loginUser.getLoginId(),
                loginUser.getRole().name(),
                accessToken,
                refreshToken
        );

        return new Result<>(response);
    }

    // ===============================
    // 비밀번호 확인
    // ===============================
    @Operation(summary = "비밀번호 확인", description = "현재 로그인한 사용자의 비밀번호가 맞는지 확인합니다.")
    @PostMapping("/isMatchPasswd")
    public Result<String> isMatchPasswd(
            @Validated @RequestBody isMatchPasswdRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        userService.isMatchPasswd(userId, request.getPassword());

        return new Result<>("일치합니다.");
    }

    // ===============================
    // 비밀번호 변경
    // ===============================
    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호 검증 후 새 비밀번호로 변경합니다.")
    @PatchMapping("/updatePasswd")
    public Result<String> updatePasswd(
            @Validated @RequestBody UpdatePasswdRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        userService.changePasswd(userId, request.getOldPasswd(), request.getNewPasswd());

        return new Result<>("변경되었습니다.");
    }

    // ===============================
    // 내 정보 수정
    // ===============================
    @Operation(summary = "회원 정보 수정", description = "전화번호, 이메일, 주소를 변경합니다.")
    @PatchMapping("/updateMyInfo")
    public Result<UserDTO> updateMyInfo(
            @Validated @RequestBody UpdateUserRequest request,
            HttpServletRequest httpServletRequest
    ) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");

        UserDTO userDTO = userService.updateMyInfo(
                userId,
                request.getPhoneNumber(),
                request.getEmail(),
                request.getAddress()
        );

        return new Result<>(userDTO);
    }

    // ===============================
    // 내 정보 조회
    // ===============================
    @Operation(summary = "내 정보 조회", description = "JWT 인증 정보를 기반으로 내 정보를 조회합니다.")
    @GetMapping("/myInfo")
    public Result<UserDTO> myInfo(HttpServletRequest httpServletRequest) {

        Long userId = (Long) httpServletRequest.getAttribute("userId");

        UserDTO myInfo = userService.getMyInfo(userId);
        return new Result<>(myInfo);
    }

    // ===============================
    // 모든 회원 조회 (ADMIN)
    // ===============================
    @Operation(summary = "전체 회원 조회 (ADMIN 전용)", description = "관리자가 모든 회원 정보를 조회합니다.")
    @GetMapping("/getAllUser")
    public Result<List<UserDTO>> getAllUser() {
        List<UserDTO> allUser = userService.getAllUser();
        return new Result<>(allUser);
    }

    // ===============================
    // 회원 삭제 (Soft Delete)
    // ===============================
    @Operation(summary = "회원 삭제(Soft Delete)", description = "사용자를 소프트 삭제 처리합니다.")
    @DeleteMapping("/delete")
    public Result<String> deleteUser(HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        userService.deleteUser(userId);

        return new Result<>("삭제되었습니다.");
    }
}
