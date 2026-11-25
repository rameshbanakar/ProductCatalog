package ProductCatalog.example.ProductCatalog.Service.ProductService;

import ProductCatalog.example.ProductCatalog.Models.Category;
import ProductCatalog.example.ProductCatalog.Models.Product;
import ProductCatalog.example.ProductCatalog.Repositories.CategoryRepository;
import ProductCatalog.example.ProductCatalog.Repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Primary
@Service
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> getAllProduct(int page,int size,String sortBy,String sortDir) {
        Sort.Direction direction=sortDir.equalsIgnoreCase("DESC")? Sort.Direction.DESC: Sort.Direction.ASC;
        Sort sort=Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        return productRepository.findAll(pageable);

    }


    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("ACTIVE");
        if (product.getCategory() != null) {
            product.setCategory(findOrSetCategory(product.getCategory()));
        }
        return productRepository.save(product);
    }

    @Override
    public Page<Product> search(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if(minPrice!=null && maxPrice!=null){
            return productRepository.findByPriceBetweenAndStatus(minPrice,maxPrice,"ACTIVE",pageable);
        }
        return productRepository.findByStatus("ACTIVE",pageable);

    }

    @Override
    public boolean deleteProduct(Long id) {
        if(id !=null){
            Optional<Product> productOptional=productRepository.findById(id);
            if(productOptional.isPresent()){
                Product product=productOptional.get();
                productRepository.delete(product);
                return true;
            }
            return false;
        }
        return false;
    }

    public Category findOrSetCategory(Category category){
        Optional<Category> category1=categoryRepository.findByName(category.getName());
        if(category1.isPresent()){
            return category1.get();
        }
        Category category2=new Category();
        category2.setName(category.getName());
        category2.setDescription(category.getDescription());
        return category2;
    }
}
