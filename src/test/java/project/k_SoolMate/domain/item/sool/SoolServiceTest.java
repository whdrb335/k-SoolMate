package project.k_SoolMate.domain.item.sool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.item.ItemStatus;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.entity.Sool;
import project.k_SoolMate.domain.item.sool.repository.SoolRepository;
import project.k_SoolMate.domain.item.sool.request.CreateSoolRequest;
import project.k_SoolMate.domain.item.sool.request.UpdateSoolRequest;
import project.k_SoolMate.domain.item.sool.service.SoolService;
import project.k_SoolMate.exception.item.DuplicateSoolNameException;
import project.k_SoolMate.exception.item.NotEnoughStockException;
import project.k_SoolMate.exception.item.NotFoundSoolException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class SoolServiceTest {
    @Autowired
    SoolService soolService;
    @Autowired
    SoolRepository soolRepository;

    /**
     * 술 생성
     */
    @Test
    @DisplayName("술 생성")
    public void 술_생성() throws Exception {
        //given
        CreateSoolRequest createSool = getCreateSoolRequest();
        //when
        SoolDTO soolDTO = soolService.createSool(createSool);
        Sool sool = getSoolById(soolDTO);
        //then
        assertThat(sool.getName()).isEqualTo("막걸리");
        assertThat(sool.getAlcoholPercent()).isEqualTo(30);
    }

    /**
     * 술 생성(예외오류_술이름겹침)
     */
    @Test
    @DisplayName("술 생성 오류(술 이름 중복)")
    public void 술_생성_오류() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO sool = soolService.createSool(createSoolRequest);
        //then
        assertThatThrownBy(() -> soolService.createSool(createSoolRequest))
                .isInstanceOf(DuplicateSoolNameException.class)
                .hasMessageContaining("이미 존재하는 술 이름 입니다.", HttpStatus.CONFLICT);
    }
    /**
     * 술 업데이트
     */
    @Test
    @DisplayName("술 정보 업데이트")
    public void 술_정보_업데이트() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO soolDTO = soolService.createSool(createSoolRequest);
        UpdateSoolRequest updateSoolRequest = new UpdateSoolRequest(
                "소주",
                "맜있는 소주",
                50,
                20000,
                5,
                "오리진소주",
                "브랜드소주");
        SoolDTO updateSoolDTO = soolService.updateSool(soolDTO.getId(), updateSoolRequest);
        Sool sool = getSoolById(updateSoolDTO);
        //then
        assertThat(sool.getName()).isEqualTo("소주");
        assertThat(sool.getPrice()).isEqualTo(20000);
    }

    /**
     * 술 단건 조회
     */
    @Test
    @DisplayName("술 정보 조회하기")
    public void 술_정보_단건조회() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO sool = soolService.createSool(createSoolRequest);
        SoolDTO soolInfo = soolService.getSoolInfo(sool.getId());
        //then
        assertThat(soolInfo.getName()).isEqualTo("막걸리");
        assertThat(soolInfo.getAlcoholPercent()).isEqualTo(30);
    }

    /**
     * 술 단건 조회(예외오류_없는 술)
     */
    @Test
    @DisplayName("술 정보 조회하기(없는 술)")
    public void 술_정보_단건조회_예외() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO sool = soolService.createSool(createSoolRequest);
        SoolDTO soolInfo = soolService.getSoolInfo(sool.getId());
        //then
        assertThatThrownBy(
                () -> soolService.getSoolInfo(50L))
                .isInstanceOf(NotFoundSoolException.class)
                .hasMessageContaining("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
    /**
     * 술 리스트 조회
     */
    @Test
    @DisplayName("술 전체 조회")
    public void 술_리스트_전체조회() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        CreateSoolRequest createSoolRequestV2 = new CreateSoolRequest("소주",
                "맜있는 소주",
                50,
                20000,
                5,
                "오리진소주",
                "브랜드소주");
        //when
        SoolDTO sool1 = soolService.createSool(createSoolRequest);
        SoolDTO sool2 = soolService.createSool(createSoolRequestV2);
        List<Sool> all = soolRepository.findAll();
        //then
        assertThat(all.get(0).getName()).isEqualTo("막걸리");
        assertThat(all.get(1).getName()).isEqualTo("소주");
    }

    /**
     * 술 정보 삭제(softDelete)
     */
    @Test
    @DisplayName("술 정보 삭제(Status=Delete)")
    public void 술_정보_삭제() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO sool = soolService.createSool(createSoolRequest);
        SoolDTO deleteSoolDTO = soolService.deleteSool(sool.getId());
        Sool deleteSool = getSoolById(deleteSoolDTO);
        //then
        assertThat(deleteSoolDTO.getItemStatus()).isEqualTo(ItemStatus.DELETE);
    }

    @Test
    @DisplayName("술 재고 증가")
    public void 술_재고_증가() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO soolDTO = soolService.createSool(createSoolRequest);
        int originStock = soolDTO.getStockQuantity();
        int addStock = 10;
        soolService.addStock(soolDTO.getId(), addStock);
        Sool sool = getSoolById(soolDTO);
        //then
        assertThat(sool.getStockQuantity()).isEqualTo(originStock + addStock);
    }

    @Test
    @DisplayName("술 재고 감소")
    public void 술_재고_감소() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO soolDTO = soolService.createSool(createSoolRequest);
        int originStock = soolDTO.getStockQuantity();
        int removeStock = 1;
        soolService.removeStock(soolDTO.getId(), removeStock);
        Sool sool = getSoolById(soolDTO);
        //then
        assertThat(sool.getStockQuantity()).isEqualTo(originStock - removeStock);
    }

    @Test
    @DisplayName("술 재고 감소 예외처리(재고가 음수가 될 때")
    public void 술_재고_감소_예외처리() throws Exception {
        //given
        CreateSoolRequest createSoolRequest = getCreateSoolRequest();
        //when
        SoolDTO soolDTO = soolService.createSool(createSoolRequest);
        int originStock = soolDTO.getStockQuantity();
        int removeStock = 100;
        //then
        assertThatThrownBy(() -> soolService.removeStock(soolDTO.getId(), removeStock))
                .isInstanceOf(NotEnoughStockException.class)
                .hasMessageContaining("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
    }

    // 술 가져오기(byId)
    private Sool getSoolById(SoolDTO sool) {
        return soolRepository.findById(sool.getId()).orElseThrow(
                () -> new NotFoundSoolException("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    }

    // 술 createRequest
    private static CreateSoolRequest getCreateSoolRequest() {
        return new CreateSoolRequest(
                "막걸리",
                "정말맛있습니다",
                30,
                20000,
                10,
                "origin",
                "brand");
    }
}
