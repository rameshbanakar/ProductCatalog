package ProductCatalog.example.ProductCatalog.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
     private String imageUrl;
     private String name;
     private BigDecimal price;
     private String description;
     private int stocks;

     @ManyToOne(cascade = CascadeType.PERSIST)
     private Category category;

     private String status;
}
