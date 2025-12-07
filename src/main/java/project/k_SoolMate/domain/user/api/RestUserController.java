package project.k_SoolMate.domain.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
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
public class RestUserController {

    private final UserService userService;

    /**
     * 회원 생성
     */
    @PostMapping("/save")
    public Result<UserDTO> createUser(@Validated @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return new Result<>(user);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public Result<UserDTO> login(@Validated @RequestBody LoginUserRequest request, HttpServletRequest httpServletRequest) {
        UserDTO loginUser = userService.login(request.getLoginId(), request.getLoginPw());
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("LOGIN_USER", new SessionUserDTO(
                loginUser.getId(), loginUser.getLoginId(), loginUser.getRole()
        ));
        return new Result<>(loginUser);
    }

    /**
     * 로그아웃
     */
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
    @PostMapping("/isMatchPasswd")
    public Result<String> isMatchPasswd(@Validated @RequestBody isMatchPasswdRequest request, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        userService.isMatchPasswd(loginUser.getId(), request.getPasswd());
        return new Result<>("일치합니다.");
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/updatePasswd")
    public Result<String> updatePasswd(@Validated @RequestBody UpdatePasswdRequest request, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        userService.changePasswd(loginUser.getId(), request.getOldPasswd(), request.getNewPasswd());
        return new Result<>("변경되었습니다.");
    }

    /**
     * 유저 업데이트
     */
    @PatchMapping("/updateMyInfo")
    public Result<UserDTO> updateMyInfo(@Validated @RequestBody UpdateUserRequest request, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO updateDTO = (UserDTO) session.getAttribute("LOGIN_USER");
        UserDTO userDTO = userService.updateMyInfo(updateDTO.getId(), request.getPhoneNumber(), request.getEmail(), request.getAddress());
        session.setAttribute("LOGIN_USER", userDTO);
        return new Result<>(userDTO);
    }

    /**
     * 내 정보 조회
     */
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
    @GetMapping("/getAllUser")
    public Result<List<UserDTO>> getAllUser() {
        List<UserDTO> allUser = userService.getAllUser();
        return new Result<>(allUser);
    }
    /**
     * 회원 삭제(softDelete_Status만 변경)
     */
    @DeleteMapping("/delete")
    public Result<String> deleteUser(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        userService.deleteUser(loginUser.getId());
        return new Result<>("삭제되었습니다.");
    }
}
