package project.k_SoolMate.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import project.k_SoolMate.common.Result;
import project.k_SoolMate.common.jwt.JwtTokenProvider;
import project.k_SoolMate.common.jwt.RefreshTokenRequest;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.request.*;
import project.k_SoolMate.domain.user.response.LoginResponse;
import project.k_SoolMate.domain.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User API", description = "회원가입 · 로그인 · 인증 · 회원 정보 관리 API")
public class RestUserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // ===============================
    // 회원 가입
    // ===============================
    @Operation(
            summary = "회원 가입",
            description = """
                    새로운 회원을 생성합니다.  
                    - loginId 중복 여부 검증  
                    - 전화번호/이메일 형식 검증  
                    - 생성된 회원 정보를 반환합니다.
                    """
    )
    @PostMapping("/save")
    public Result<UserDTO> createUser(@Validated @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return new Result<>(user);
    }

    // ===============================
    // 로그인 (JWT 발급)
    // ===============================
    @Operation(
            summary = "로그인(JWT 발급)",
            description = """
                    사용자 로그인 후 AccessToken + RefreshToken을 발급합니다.  
                    - 로그인 실패 시 예외 발생  
                    - RefreshToken은 DB에 저장됩니다.
                    """
    )
    @PostMapping("/login")
    public Result<LoginResponse> login(@Validated @RequestBody LoginUserRequest request) {

        UserDTO loginUser = userService.login(request.getLoginId(), request.getLoginPw());

        String accessToken = jwtTokenProvider.createAccessToken(
                loginUser.getId(),
                loginUser.getRole().name()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(loginUser.getId());

        userService.updateRefreshToken(loginUser.getId(), refreshToken);

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
    @Operation(
            summary = "비밀번호 확인",
            description = """
                    현재 로그인한 사용자의 비밀번호가 일치하는지 확인합니다.  
                    - JWT 인증 필요  
                    - 비밀번호 미일치 시 예외 발생
                    """
    )
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
    @Operation(
            summary = "비밀번호 변경",
            description = """
                    사용자 본인의 기존 비밀번호를 검증한 후, 새 비밀번호로 변경합니다.  
                    - oldPasswd 불일치 시 예외 발생  
                    - JWT 인증 필요
                    """
    )
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
    @Operation(
            summary = "내 정보 수정",
            description = """
                    회원 본인의 전화번호, 이메일, 주소를 변경합니다.  
                    - JWT 인증 필요  
                    - Soft-delete된 유저는 수정할 수 없습니다.
                    """
    )
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
    @Operation(
            summary = "내 정보 조회",
            description = """
                    현재 로그인한 회원 정보를 조회합니다.  
                    - JWT 인증 필요  
                    - Soft-delete 시 조회 불가
                    """
    )
    @GetMapping("/myInfo")
    public Result<UserDTO> myInfo(HttpServletRequest httpServletRequest) {

        Long userId = (Long) httpServletRequest.getAttribute("userId");

        UserDTO myInfo = userService.getMyInfo(userId);
        return new Result<>(myInfo);
    }



    // ===============================
    // 회원 삭제 (Soft Delete)
    // ===============================
    @Operation(
            summary = "회원 삭제 (Soft Delete)",
            description = """
                    현재 로그인한 사용자를 soft-delete 처리합니다.  
                    - status = DELETED  
                    - 재로그인 불가  
                    - JWT 인증 필요  
                    """
    )
    @DeleteMapping("/delete")
    public Result<String> deleteUser(HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        userService.deleteUser(userId);

        return new Result<>("삭제되었습니다.");
    }

    // ===============================
    // Refresh Token 재발급
    // ===============================
    @Operation(
            summary = "토큰 재발급(Refresh Token)",
            description = """
                    RefreshToken을 이용하여 새로운 AccessToken + RefreshToken을 발급합니다.  
                    - DB에 저장된 RefreshToken과 비교  
                    - 만료 또는 위조 시 예외 발생  
                    - 새 RefreshToken은 DB에 업데이트됩니다.
                    """
    )
    @PostMapping("/refresh")
    public Result<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {

        String clientRefreshToken = request.getRefreshToken();

        Long userId = jwtTokenProvider.parseClaims(clientRefreshToken)
                .get("userId", Long.class);

        UserDTO user = userService.getMyInfo(userId);

        userService.validateRefreshToken(userId, clientRefreshToken);

        String newAccessToken = jwtTokenProvider.createAccessToken(
                userId,
                user.getRole().name()
        );
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);

        userService.updateRefreshToken(userId, newRefreshToken);

        return new Result<>(
                new LoginResponse(
                        userId,
                        user.getLoginId(),
                        user.getRole().name(),
                        newAccessToken,
                        newRefreshToken
                )
        );
    }

    // ===============================
    // 로그아웃
    // ===============================
    @Operation(
            summary = "로그아웃",
            description = """
                    사용자의 RefreshToken을 삭제하여 로그아웃 처리합니다.  
                    - JWT 인증 필요  
                    - AccessToken은 서버가 강제 만료시키지 않지만 사용 불가 처리되도록 구성할 수 있음
                    """
    )
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateRefreshToken(userId, null);
        return new Result<>("로그아웃 되었습니다.");
    }
}
