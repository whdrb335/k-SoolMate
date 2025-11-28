package project.k_SoolMate.domain.address;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    @NotBlank(message = "도시는 필수 입니다.")
    private String city;
    @NotBlank(message = "도로명은 필수 입니다.")
    private String street;
    @NotBlank(message = "우편번호는 필수 입니다.")
    private String zipcode;

    //주소 생성자 생성
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
