package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    private String name;
    private String description;
}
