package project.k_SoolMate.domain.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
import project.k_SoolMate.domain.user.request.UpdatePasswdRequest;
import project.k_SoolMate.domain.user.request.isMatchPasswdRequest;
import project.k_SoolMate.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class RestUserController {

    private final UserService userService;

    /**
     * 회원 생성
     */
    @PostMapping("/save")
    public Result<UserDTO> createUser(@Validated @RequestBody CreateUserRequest request, HttpServletRequest httpServletRequest) {
        UserDTO user = userService.createUser(request);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("LOGIN_USER",user);
        return new Result<>(user);
    }

    /**
     * 비밀번호 확인
     */
    @PostMapping("/isMatchPasswd/{id}")
    public void isMatchPasswd(@PathVariable("id") Long id, @Validated @RequestBody isMatchPasswdRequest request) {
        userService.isMatchPasswd(id, request.getPasswd());
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/updatePasswd/{id}")
    public void updatePasswd(@PathVariable("id") Long id, @Validated @RequestBody UpdatePasswdRequest request) {
        userService.changePasswd(id,request.getOldPasswd(), request.getNewPasswd());
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/myInfo/{id}")
    public Result<UserDTO> myInfo(@PathVariable("id") Long id) {
        UserDTO myInfo = userService.getMyInfo(id);
        return new Result<>(myInfo);
    }


}
