package project.k_SoolMate.domain.item.sool.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSoolRequest {
    private String name;
    private String description;
    private double alcoholPercent;
    private int price;
    private int stockQuantity;
    private String origin;
    private String brand;
}