package ProductCatalog.example.ProductCatalog.Models;

import ProductCatalog.example.ProductCatalog.Controller.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product extends BaseModel{
     private String name;
     private float price;
     private String description;
     private int stocks;

     @ManyToOne
     private Category category;
}
