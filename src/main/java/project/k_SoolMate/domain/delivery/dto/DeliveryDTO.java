package project.k_SoolMate.domain.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.delivery.DeliveryStatus;

@Getter
@AllArgsConstructor
@Schema(description = "배송 정보 DTO")
public class DeliveryDTO {

    @Schema(description = "도시", example = "서울시 관악구")
    private String city;

    @Schema(description = "도로명 주소", example = "봉천로 123길 45")
    private String street;

    @Schema(description = "우편번호", example = "08790")
    private String zipcode;

    @Schema(description = "배송 상태", example = "READY")
    private DeliveryStatus status;

    public DeliveryDTO(Delivery delivery) {
        this.city = delivery.getAddress().getCity();
        this.street = delivery.getAddress().getStreet();
        this.zipcode = delivery.getAddress().getZipcode();
        this.status = delivery.getDeliveryStatus();
    }
}
