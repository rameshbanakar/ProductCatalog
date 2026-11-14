package ProductCatalog.example.ProductCatalog.DTO;

import ProductCatalog.example.ProductCatalog.Controller.Category;
import lombok.Data;

@Data
public class ProductReponseDto {
    private String name;
    private String description;
    private float price;
    private String imageUrl;
    private Category category;
}
