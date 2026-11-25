package ProductCatalog.example.ProductCatalog.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPitchDto {
    private String productName;
    private Long productId;
    private String pitchNotification;
}
