package ProductCatalog.example.ProductCatalog.Mapper;

import ProductCatalog.example.ProductCatalog.DTO.CategoryResponseDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductReponseDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductRequestDto;
import ProductCatalog.example.ProductCatalog.Models.Category;
import ProductCatalog.example.ProductCatalog.Models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setStocks(productRequestDto.getStocks());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setDescription(productRequestDto.getDescription());
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());

        if (productRequestDto.getCategory() != null) {
            Category category = new Category();
            category.setDescription(productRequestDto.getCategory().getDescription());
            category.setName(productRequestDto.getCategory().getName());

            product.setCategory(category);
        }
        return product;
    }

    public ProductReponseDto toDto(Product product){
        ProductReponseDto productReponseDto=new ProductReponseDto();
        productReponseDto.setImageUrl(product.getImageUrl());
        productReponseDto.setDescription(product.getDescription());
        productReponseDto.setName(product.getName());
        productReponseDto.setPrice(product.getPrice());

        if(product.getCategory()!=null){
            CategoryResponseDto categoryResponseDto=new CategoryResponseDto();
            categoryResponseDto.setDescription(product.getCategory().getDescription());
            categoryResponseDto.setName(product.getCategory().getName());
            productReponseDto.setCategory(categoryResponseDto);
        }
        return productReponseDto;
    }

}
