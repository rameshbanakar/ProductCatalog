package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String imageUrl;
    private String name;
    private String description;
    private BigDecimal price;
    private int stocks;
    private CategoryRequestDto category;

}
