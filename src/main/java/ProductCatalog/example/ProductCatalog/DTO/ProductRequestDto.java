package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String imageUrl;
    private String name;
    private String description;
    private float price;
    private int stocks;
    private String category;

}
