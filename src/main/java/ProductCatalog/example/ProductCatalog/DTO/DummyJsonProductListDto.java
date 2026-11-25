package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class DummyJsonProductListDto {
    private List<DummyJsonProductDto> products;
    private int total;
    private int skip;
    private int limit;
}
