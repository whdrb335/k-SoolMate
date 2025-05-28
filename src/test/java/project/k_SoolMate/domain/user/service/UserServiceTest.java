package project.k_SoolMate.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.user.User;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.repository.UserRepository;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
import project.k_SoolMate.exception.user.NotFoundUserException;
import project.k_SoolMate.exception.user.NotMatchPasswd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * tdd로 분리해서 /given /when /then으로 나눠서 적으면 효과적으로 구분
     */
    @Test
    @DisplayName("유저 생성 후 유저 이름_유저아이디가 같은지 확인")
    public void 유저생성() throws Exception {
        //given
        UserDTO testMember = createTestMember("whdrb3353", "123", "김종규", "01035829211", "whdrb3353@naver.com", new Address("서울", "봉천로", "4442"));
        //when

        //then
        assertThat(testMember.getMemberName()).isEqualTo("김종규");
        assertThat(testMember.getLoginId()).isEqualTo("whdrb3353");
    }

    @Test
    @DisplayName("유저 비밀번호 확인 로직")
    public void 유저_비밀번호_확인_로직() throws Exception {
        //given
        UserDTO testMember = createTestMember("whdrb3353", "123", "김종규", "01035829211", "whdrb3353@naver.com", new Address("서울", "봉천로", "4442"));
        //when
        User user = getUserById(testMember);
        //then
        assertThat(bCryptPasswordEncoder.matches("123",user.getLoginPw())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 변경 로직")
    public void 비밀번호_변경() throws Exception {
        //given
        UserDTO testMember = createTestMember("whdrb3353", "123", "김종규", "01035829211", "whdrb3353@naver.com", new Address("서울", "봉천로", "4442"));
        //when
        User user = getUserById(testMember);
        user.changePasswd("123","12345", bCryptPasswordEncoder);
        //then
        assertThat(bCryptPasswordEncoder.matches("12345", user.getLoginPw())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 변경 실패 로직")
    public void 비밀번호_변경_실패() throws Exception {
        //given
        UserDTO testMember = createTestMember("whdrb3353", "123", "김종규", "01035829211", "whdrb3353@naver.com", new Address("서울", "봉천로", "4442"));
        //when
        User user = getUserById(testMember);
        //then
        assertThatThrownBy(() -> user.changePasswd("wrongPasswd", "newPasswd", bCryptPasswordEncoder))
                .isInstanceOf(NotMatchPasswd.class)
                .hasMessageContaining("비밀번호가 맞지 않습니다", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("내 정보 조회")
    public void 내정보_조회() throws Exception {
        //given
        UserDTO testMember = createTestMember("whdrb3353", "123", "김종규", "01035829211", "whdrb3353@naver.com", new Address("서울", "봉천로", "4442"));
        //when
        User user = getUserById(testMember);
        //then
        assertThat(user.getLoginId()).isEqualTo(testMember.getLoginId());
        assertThat(user.getName()).isEqualTo(testMember.getMemberName());
        assertThat(user.getPhoneNumber()).isEqualTo(testMember.getPhoneNumber());
    }

    @Test
    @DisplayName("내 정보 업데이트")
    public void 내_정보_변경() throws Exception {
        //given
        UserDTO testMember = createTestMember("whdrb3353", "123", "김종규", "01035829211", "whdrb3353@naver.com", new Address("서울", "봉천로", "4442"));
        User user = getUserById(testMember);
        //when
        user.updateUser("01088783354", "quddyd@naver.com", new Address("대구", "수성구", "44204"));
        //then
        assertThat(user.getPhoneNumber()).isEqualTo("01088783354");
        assertThat(user.getEmail()).isEqualTo("quddyd@naver.com");
    }

    private UserDTO createTestMember(String loginId, String loginPw, String memberName, String phoneNumber, String email, Address address) {
        CreateUserRequest request = new CreateUserRequest(loginId,loginPw,memberName, phoneNumber,email,address);
        return userService.createUser(request);
    }

    private User getUserById(UserDTO testMember) {
        return userRepository.findById(testMember.getId())
                .orElseThrow(() -> new NotFoundUserException("해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }
}