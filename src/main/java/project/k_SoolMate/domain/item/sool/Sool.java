package project.k_SoolMate.domain.item.sool;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import project.k_SoolMate.domain.item.Item;
import project.k_SoolMate.domain.item.ItemType;
import project.k_SoolMate.exception.item.NotEnoughException;

@Entity
@Getter
@DiscriminatorValue("SOOL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sool extends Item {
    private double alcoholPercent;  //도수
    private int price;              //가격
    private int stockQuantity;      //수량
    private String origin;          //제조지역
    private String brand;           //브랜드명

    //==생성 메서드==//

    /**
     * 술 생성
     */
    public static Sool createSool(String name, String description, ItemType itemType, double alcoholPercent, int price,
                                  int stockQuantity, String origin, String brand) {
        Sool sool = new Sool();
        sool.setCommonFieldsCreate(name, description, itemType);
        sool.alcoholPercent = alcoholPercent;
        sool.price = price;
        sool.stockQuantity = stockQuantity;
        sool.origin = origin;
        sool.brand = brand;
        return sool;
    }

    /**
     * 술 업데이트
     */
    public void updateSool(String name, String description, double alcoholPercent, int price,
                           int stockQuantity, String origin, String brand) {
        this.setCommonFieldsUpdate(name, description);
        this.alcoholPercent = alcoholPercent;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.origin = origin;
        this.brand = brand;
    }

    /**
     * 술 삭제
     */
    public void deleteSool() {
        this.deleteItem();
    }

    /**
     * 재고 증가
     */
    public void addStock(int count) {
        this.stockQuantity += count;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int count) {
        int restStock = this.stockQuantity - count;
        if (restStock < 0) {
            //재고 부족 에러를 HttpStatus랑 같이보낸다.
            throw new NotEnoughException("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
        }
        this.stockQuantity = restStock;
    }


}
