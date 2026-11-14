package ProductCatalog.example.ProductCatalog.DTO;

import ProductCatalog.example.ProductCatalog.Controller.Category;
import lombok.Data;

@Data
public class ProductRequestDto {
    private String imageUrl;
    private String name;
    private String Description;
    private float price;
    private int stocks;
    private Category category;

}
