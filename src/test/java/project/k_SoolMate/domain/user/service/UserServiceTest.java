package project.k_SoolMate.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.address.Address;
import project.k_SoolMate.domain.user.entity.User;
import project.k_SoolMate.domain.user.dto.UserDTO;
import project.k_SoolMate.domain.user.entity.UserStatus;
import project.k_SoolMate.domain.user.repository.UserRepository;
import project.k_SoolMate.domain.user.request.CreateUserRequest;
import project.k_SoolMate.domain.user.request.UpdateUserRequest;
import project.k_SoolMate.exception.user.DuplicateUserIdException;
import project.k_SoolMate.exception.user.NotFoundUserIdException;
import project.k_SoolMate.exception.user.NotMatchPasswd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @DisplayName("유저 생성 후 아이디가 같은지 확인")
    public void 유저생성() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        //when
        UserDTO testMember = createTestMember(request);
        //then
        assertThat(testMember.getLoginId()).isEqualTo("whdrb3353");
        assertThat(testMember.getMemberName()).isEqualTo("김종규");
    }




    @Test
    @DisplayName("비밀번호가 같은지 확인")
    public void 비밀번호_확인_로직() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        User user = findById(testMember.getId());
        //then
        assertThat(bCryptPasswordEncoder.matches("1234", user.getLoginPw())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 변경 확인")
    public void 비밀번호_변경_로직() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        userService.changePasswd(testMember.getId(), "1234", "12345");
        User user = findById(testMember.getId());
        //then
        assertThat(bCryptPasswordEncoder.matches("12345", user.getLoginPw())).isTrue();

    }

    @Test
    @DisplayName("비밀번호_변경_실패")
    public void 비밀번호_변경_실패_로직() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        User user = findById(testMember.getId());
        //then
        assertThatThrownBy(() ->
                userService.changePasswd(testMember.getId(), "틀린비번", "123123"))
                .isInstanceOf(NotMatchPasswd.class)
                .hasMessageContaining("비밀번호가 맞지 않습니다");
    }



    @Test
    @DisplayName("내 정보 조회")
    public void 내_정보_조회_로직() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        UserDTO myInfo = userService.getMyInfo(testMember.getId());
        //then
        assertThat(myInfo.getMemberName()).isEqualTo("김종규");
        assertThat(myInfo.getLoginId()).isEqualTo("whdrb3353");
        assertThat(myInfo.getEmail()).isEqualTo("whdrb3353@naver.com");
    }

    @Test
    @DisplayName("내 정보 변경")
    public void 내_정보_변경_로직() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        UserDTO userDTO = userService.updateMyInfo(testMember.getId(),
                "010-1111-1111",
                "whdrb1111@naver.com",
                new Address("대구","수성구","44441"));
        //then
        assertThat(userDTO.getPhoneNumber()).isEqualTo("010-1111-1111");
        assertThat(userDTO.getEmail()).isEqualTo("whdrb1111@naver.com");
        assertThat(userDTO.getAddress().getCity()).isEqualTo("대구");
    }

    @Test
    @DisplayName("로그인")
    public void 로그인() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        UserDTO loginUser = userService.login(testMember.getLoginId(), "1234");
        //then
        assertThat(loginUser.getLoginId()).isEqualTo(testMember.getLoginId());
        assertThat(loginUser.getMemberName()).isEqualTo(testMember.getMemberName());

    }

    @Test
    @DisplayName("로그인 실패 로직_아이디")
    public void 로그인_실패_로직_아이디() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when

        //then
        assertThatThrownBy(() ->
                userService.login("틀린아이디", "1234"))
                .isInstanceOf(NotFoundUserIdException.class)
                .hasMessageContaining("존재하지 않은 아이디입니다.");
    }

    @Test
    @DisplayName("로그인 실패 로직_비밀번호")
    public void 로그인_실패_로직_비밀번호() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when

        //then
        assertThatThrownBy(() ->
                userService.login("whdrb3353", "999"))
                .isInstanceOf(NotMatchPasswd.class)
                .hasMessageContaining("비밀번호가 맞지 않습니다");
    }

    @Test
    @DisplayName("회원탈퇴")
    public void 회원탈퇴_소프트_탈퇴() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when
        userService.deleteUser(testMember.getId());
        User user = findById(testMember.getId());
        //then
        assertThat(user.getStatus()).isEqualTo(UserStatus.DELETE);
    }

    @Test
    @DisplayName("회원가입_이름중복_테스트")
    public void 회원가입_이름중복_테스트() throws Exception {
        //given
        CreateUserRequest request = getCreateUserRequest();
        UserDTO testMember = createTestMember(request);
        //when

        //then
        assertThatThrownBy(() ->
                userService.createUser(request))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessageContaining("이미 존재하는 아이디 입니다.");
    }

    /**
     * testMember 만드는 로직
     */
    private UserDTO createTestMember(CreateUserRequest request) {
        return userService.createUser(request);
    }

    /**
     * CreateUserRequest 만드는 공통 로직
     */
    private static CreateUserRequest getCreateUserRequest() {
        CreateUserRequest request = new CreateUserRequest(
                "whdrb3353",
                "1234",
                "김종규",
                "010-3582-9211",
                "whdrb3353@naver.com",
                new Address("서울","봉천로","4442")
        );
        return request;
    }

    /**
     * Repository 에서 user 정보 확인 로직
     */
    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 비번"));
    }


}