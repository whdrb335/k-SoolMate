package project.k_SoolMate.domain.item.sool.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSoolRequest {
    @NotBlank(message = "술 이름은 필수 입니다.")
    private String name;
    @NotBlank(message = "설명은 필수 입니다.")
    private String description;
    @NotBlank(message = "도수는 필수 입니다.")
    private double alcoholPercent;
    private int price;
    @NotBlank(message = "가격은 필수 입니다.")
    private int stockQuantity;
    private String origin;
    private String brand;

}