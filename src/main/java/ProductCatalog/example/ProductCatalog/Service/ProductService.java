package ProductCatalog.example.ProductCatalog.Service;

import ProductCatalog.example.ProductCatalog.Models.Category;
import ProductCatalog.example.ProductCatalog.DTO.CategoryResponseDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductReponseDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductRequestDto;
import ProductCatalog.example.ProductCatalog.Models.Product;
import ProductCatalog.example.ProductCatalog.Repositories.CategoryRepository;
import ProductCatalog.example.ProductCatalog.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductReponseDto getProduct(long productId){
        if(productId<=0){
          throw new IllegalArgumentException("Invalid product Id");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new java.util.NoSuchElementException(
                        "Product not found with id " + productId));

        ProductReponseDto response = new ProductReponseDto();
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCategory(toCategoryResponse(product.getCategory()));
        response.setImageUrl(product.getImageUrl());
        return response;
    }

    public ProductReponseDto addProduct(ProductRequestDto productRequestDto){
        if (productRequestDto == null) {
            throw new IllegalArgumentException("Product input is required");
        }

        String name=productRequestDto.getName();
        String categoryName=productRequestDto.getCategory();
        String description=productRequestDto.getDescription();
        float price=productRequestDto.getPrice();
        int stocks=productRequestDto.getStocks();
        String imageUrl=productRequestDto.getImageUrl();

        if (name == null || name.isBlank()
                || categoryName == null || categoryName.isBlank()
                || description == null || description.isBlank()
                || imageUrl == null || imageUrl.isBlank()
                || !Float.isFinite(price) || price < 0
                || stocks <= 0) {
            throw new IllegalArgumentException("Invalid product input");
        }

        Category category=categoryRepository.findByName(categoryName).orElseThrow(
                ()->new RuntimeException("Category not found"));

        Product product=new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setStocks(stocks);
        productRepository.save(product);

        ProductReponseDto response=new ProductReponseDto();
        response.setImageUrl(product.getImageUrl());
        response.setCategory(toCategoryResponse(product.getCategory()));
        response.setDescription(product.getDescription());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        return response;

    }

    private CategoryResponseDto toCategoryResponse(Category category) {
        CategoryResponseDto response = new CategoryResponseDto();
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}
