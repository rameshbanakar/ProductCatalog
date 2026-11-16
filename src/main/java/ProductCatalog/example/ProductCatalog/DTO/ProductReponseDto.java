package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Data;

@Data
public class ProductReponseDto {
    private String name;
    private String description;
    private float price;
    private String imageUrl;
    private CategoryResponseDto category;
}
