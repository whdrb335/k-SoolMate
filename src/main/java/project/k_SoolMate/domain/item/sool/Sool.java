package project.k_SoolMate.domain.item.sool;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.item.Item;

@Entity
@Getter
@DiscriminatorValue("SOOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sool extends Item {
    private double alcoholPercent; //도수
    private String origin; // 제조지역
    private String brand; //브랜드명
}
