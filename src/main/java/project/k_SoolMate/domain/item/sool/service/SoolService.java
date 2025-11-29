package project.k_SoolMate.domain.item.sool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.k_SoolMate.domain.item.sool.dto.SoolDTO;
import project.k_SoolMate.domain.item.sool.entity.Sool;
import project.k_SoolMate.domain.item.sool.repository.SoolRepository;
import project.k_SoolMate.domain.item.sool.request.CreateSoolRequest;
import project.k_SoolMate.domain.item.sool.request.UpdateSoolRequest;
import project.k_SoolMate.exception.item.DuplicateSoolNameException;
import project.k_SoolMate.exception.item.NotFoundSoolException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final 필드 생성사 자동생성
public class SoolService {

    private final SoolRepository soolRepository;

    /**
     * 술 제품 생성
     */
    @Transactional
    public SoolDTO createSool(CreateSoolRequest request) {
        duplicatedSoolName(request.getName());
        Sool sool = Sool.createSool(request.getName(), request.getDescription(), request.getAlcoholPercent(),
                request.getPrice(), request.getStockQuantity(), request.getOrigin(), request.getBrand());
        Sool save = soolRepository.save(sool);
        return new SoolDTO(save);
    }

    /**
     * 술 제품 수정
     */
    @Transactional
    public SoolDTO updateSool(Long id,UpdateSoolRequest request) {
        Sool sool = getSoolById(id);
        if (!sool.getName().equals(request.getName())) {
            duplicatedSoolName(request.getName());
        }
        sool.updateSool(request.getName(), request.getDescription(), request.getAlcoholPercent(), request.getPrice(),
                request.getStockQuantity(), request.getOrigin(), request.getBrand());
        Sool updateSool = getSoolById(id);
        return new SoolDTO(updateSool);
    }

    /**
     * 술 정보 찾기
     */
    public SoolDTO getSoolInfo(Long id) {
        Sool sool = getSoolById(id);
        return new SoolDTO(sool);
    }

    /**
     * 술 전체 조회
     */
    public List<SoolDTO> getAll() {
        return soolRepository.findAll().stream().map(SoolDTO::new).toList();
    }

    /**
     * 술 삭제(softDelete)
     */
    @Transactional
    public SoolDTO deleteSool(Long id) {
        Sool sool = getSoolById(id);
        sool.deleteSool();
        return new SoolDTO(sool);
    }

    /**
     * 재고 증가
     */
    @Transactional
    public void addStock(Long id, int count) {
        Sool sool = getSoolById(id);
        sool.addStock(count);
    }

    /**
     * 재고 감소
     */
    @Transactional
    public void removeStock(Long id, int count) {
        Sool sool = getSoolById(id);
        sool.removeStock(count);
    }
    // Sool entity id로 얻는 로직
    private Sool getSoolById(Long id) {
        return soolRepository.findById(id).orElseThrow(
                () -> new NotFoundSoolException("해당 술이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
    }

    // 제품 이름 중복 방지 로직
    private void duplicatedSoolName(String name) {
        if (soolRepository.existsByName(name)) {
            throw new DuplicateSoolNameException("이미 존재하는 술 이름 입니다.", HttpStatus.CONFLICT);
        }
    }
}
