package project.k_SoolMate.domain.user.db;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.item.sool.entity.Sool;
import project.k_SoolMate.domain.item.sool.repository.SoolRepository;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.entity.UserRole;
import project.k_SoolMate.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserInitData {

    private final UserRepository userRepository;
    private final SoolRepository soolRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
        User admin = User.admin(
                "admin",
                "admin1234",
                "관리자",
                bCryptPasswordEncoder
        );
        userRepository.save(admin);

        User user = User.createUser(
                "whdrb3353",
                "1234",
                "김종규",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442"),
                bCryptPasswordEncoder
        );
        userRepository.save(user);

        Sool sool1 = Sool.createSool(

                "소주",
                "맜있는 소주",
                50,
                20000,
                5,
                "오리진소주",
                "브랜드소주"
        );

        Sool sool2 = Sool.createSool(
                "막걸리",
                "정말맛있습니다",
                30,
                20000,
                10,
                "origin",
                "brand");
        soolRepository.save(sool1);
        soolRepository.save(sool2);

    }

}
