package project.k_SoolMate.domain.user.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.repository.UserRepository;

@Service
// 기본적 DB 읽기전용 (최적화)
@Transactional(readOnly = true)
@RequiredArgsConstructor //final 필드 생성자 자동생성
public class UserService {

    private final UserRepository userRepository;

    /**
     * 멤버 생성
     */
    @Transactional
    public UserDTO createUser()

}
