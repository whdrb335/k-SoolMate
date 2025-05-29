package project.k_SoolMate.domain.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.k_SoolMate.common.Result;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
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

}
