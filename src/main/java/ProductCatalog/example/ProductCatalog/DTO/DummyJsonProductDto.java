package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DummyJsonProductDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;
    private String thumbnail;
}
