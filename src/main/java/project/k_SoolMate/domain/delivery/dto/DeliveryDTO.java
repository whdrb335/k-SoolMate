package project.k_SoolMate.domain.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.k_SoolMate.domain.delivery.Delivery;
import project.k_SoolMate.domain.delivery.DeliveryStatus;

@Getter
@AllArgsConstructor
public class DeliveryDTO {

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;

    public DeliveryDTO(Delivery delivery) {
        this.city = delivery.getAddress().getCity();
        this.street = delivery.getAddress().getStreet();
        this.zipcode = delivery.getAddress().getZipcode();
        this.status = delivery.getDeliveryStatus();
    }
}
