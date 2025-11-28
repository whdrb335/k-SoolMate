package project.k_SoolMate.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.user.request.LoginUserRequest;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.repository.UserRepository;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
import project.k_SoolMate.domain.user.request.UpdateUserRequest;
import project.k_SoolMate.exception.user.DuplicateUserIdException;
import project.k_SoolMate.exception.user.NotFoundUserException;
import project.k_SoolMate.exception.user.NotFoundUserIdException;

import java.util.ArrayList;
import java.util.List;

@Service
// 기본적 DB 읽기전용 (최적화)
@Transactional(readOnly = true)
@RequiredArgsConstructor //final 필드 생성자 자동생성
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 생성 빈이 무겁기때문에 service에서 생성해주어서 보내준다.
    
    /**
     * 멤버 생성
     */
    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        //회원 LoginId 중복 메서드
        validateDuplicateUser(request.getLoginId());
        User user = User.createUser(request.getLoginId(), request.getLoginPw(), request.getMemberName(), request.getPhoneNumber(),
                request.getEmail(), request.getAddress(), bCryptPasswordEncoder);
        User saveUser = userRepository.save(user);
        return new UserDTO(saveUser);
    }



    /**
     * 비밀번호 확인 로직
     */
    @Transactional
    public void isMatchPasswd(Long id,String passwd) {
        User user = getUserFindById(id);
        user.isMatchPasswd(passwd, bCryptPasswordEncoder);
    }

    /**
     * 비밀번호 변경 로직
     */
    @Transactional
    public void changePasswd(Long id, String oldPasswd, String newPasswd) {
        User user = getUserFindById(id);
        user.changePasswd(oldPasswd, newPasswd, bCryptPasswordEncoder);
    }

    /**
     * 내 정보 조회
     */
    public UserDTO getMyInfo(Long id) {
        User user = getUserFindById(id);
        return new UserDTO(user);
    }

    /**
     * 유저 전체 조회
     */
    public List<UserDTO> getAllUser() {
        List<UserDTO> list = userRepository.findAll().stream().map(UserDTO::new).toList();
        return new ArrayList<>(list);
    }

    /**
     * 내 정보 변경
     */
    @Transactional
    public UserDTO updateMyInfo(Long id, UpdateUserRequest request) {
        User user = getUserFindById(id);
        user.updateUser(request.getPhoneNumber(), request.getEmail(), request.getAddress());
        return new UserDTO(user);
    }

    /**
     * 로그인
     */
    @Transactional
    public UserDTO login(String loginId, String loginPw) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new NotFoundUserIdException("존재하지 않은 아이디입니다.", HttpStatus.NOT_FOUND));
        user.isMatchPasswd(loginPw,bCryptPasswordEncoder);
        return new UserDTO(user);
    }

    /**
     * 회원 삭제(softDelete)
     */
    @Transactional
    public UserDTO deleteUser(Long id) {
        User user = getUserFindById(id);
        user.deleteMember();
        return new UserDTO(user);
    }

    //==중복 메서드==//
    private User getUserFindById(Long id) {
       return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    //==회원 로그인 ID 중복 메서드==//
    private void validateDuplicateUser(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicateUserIdException("이미 존재하는 아이디 입니다.", HttpStatus.CONFLICT);
        }
    }

}
