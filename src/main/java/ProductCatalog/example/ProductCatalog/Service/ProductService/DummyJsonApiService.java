package ProductCatalog.example.ProductCatalog.Service.ProductService;

import ProductCatalog.example.ProductCatalog.DTO.DummyJsonProductDto;
import ProductCatalog.example.ProductCatalog.DTO.DummyJsonProductListDto;
import ProductCatalog.example.ProductCatalog.Models.Category;
import ProductCatalog.example.ProductCatalog.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;


@Service
public class DummyJsonApiService implements IProductService{

    @Autowired
    private RestTemplate restTemplate;
    private final String baseUrl="https://dummyjson.com";

    @Override
    public Page<Product> getAllProduct(int page, int size,String sortBy,String sortDir) {
        PageRequest pageable = PageRequest.of(page, size);
        try {
            int skip = Math.toIntExact(pageable.getOffset());
            ResponseEntity<DummyJsonProductListDto> responseEntity = restTemplate.getForEntity(
                    baseUrl + "/products?limit={size}&skip={skip}",
                    DummyJsonProductListDto.class,
                    size,
                    skip);
            if (!responseEntity.getStatusCode().is2xxSuccessful() || !responseEntity.hasBody()) {
                throw new IllegalStateException("Supplier returned no product data");
            }
            DummyJsonProductListDto body = responseEntity.getBody();
            if (body == null) {
                throw new IllegalStateException("Supplier returned an empty response body");
            }
            if (body.getProducts() == null) {
                throw new IllegalStateException("Supplier response did not contain a products list");
            }
            List<Product> products = body.getProducts().stream()
                    .map(this::from)
                    .toList();
            return new PageImpl<>(products, pageable, body.getTotal());
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch products from supplier", e);
        }
    }

    @Override
    public Product getProductById(long id) {
       try{
           ResponseEntity<DummyJsonProductDto> reponseEntity=restTemplate.getForEntity(baseUrl+"/products/{id}", DummyJsonProductDto.class,id);
           DummyJsonProductDto body = reponseEntity.getBody();
           if(reponseEntity.getStatusCode().is2xxSuccessful() && body != null){
               return from(body);
           }
           return null;
       }catch (Exception e){
           System.out.println("some errors"+e.getMessage());
           return null;
       }

    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Page<Product> search(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    private Product from(DummyJsonProductDto dummyJsonProductDto){
        Product product = new Product();
//        product.setId(dummyJsonProductDto.getId());
        product.setName(dummyJsonProductDto.getTitle());
        product.setDescription(dummyJsonProductDto.getDescription());
        product.setPrice(dummyJsonProductDto.getPrice());
        product.setImageUrl(dummyJsonProductDto.getThumbnail());

        Category category = new Category();
        category.setName(dummyJsonProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
