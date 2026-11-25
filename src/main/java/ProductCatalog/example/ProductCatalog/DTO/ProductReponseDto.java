package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductReponseDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private CategoryResponseDto category;
}
