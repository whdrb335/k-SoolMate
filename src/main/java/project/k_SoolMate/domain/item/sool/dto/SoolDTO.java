package project.k_SoolMate.domain.item.sool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.k_SoolMate.domain.item.ItemStatus;
import project.k_SoolMate.domain.item.ItemType;
import project.k_SoolMate.domain.item.sool.entity.Sool;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SoolDTO {
    private Long id;
    private String name;
    private String description;
    private double alcoholPercent;
    private int price;
    private int stockQuantity;
    private String origin;
    private String brand;
    private ItemType itemType;
    private ItemStatus itemStatus;


    public SoolDTO(Sool sool) {
        this.id = sool.getId();
        this.name = sool.getName();
        this.description = sool.getDescription();
        this.alcoholPercent = sool.getAlcoholPercent();
        this.price = sool.getPrice();
        this.stockQuantity = sool.getStockQuantity();
        this.origin = sool.getOrigin();
        this.brand = sool.getBrand();
        this.itemType = sool.getItemType();
        this.itemStatus = sool.getItemStatus();
    }

}