package ProductCatalog.example.ProductCatalog.Controller;

import ProductCatalog.example.ProductCatalog.DTO.ProductPitchDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductReponseDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductRequestDto;
import ProductCatalog.example.ProductCatalog.Mapper.ProductMapper;
import ProductCatalog.example.ProductCatalog.Models.Category;
import ProductCatalog.example.ProductCatalog.Models.Product;
import ProductCatalog.example.ProductCatalog.Service.ProductService.IProductService;
import ProductCatalog.example.ProductCatalog.Service.ProductService.ProductPitchService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductPitchService productPitchService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    @Qualifier("dummyJsonApiService")
    private IProductService supplierService;

    @GetMapping("/{Id}")
    public ResponseEntity<?> getProduct(@PathVariable("Id") long id){
        try {
            Product product = productService.getProductById(id);
            if(product==null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(productMapper.toDto(product));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("Message", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message", e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(500).body(Map.of("Message",e.getMessage()));
        }
    }

    @GetMapping("/{id}/pitch")
    public ResponseEntity<?> getPitch(@PathVariable("id") long id){
        if(id < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getProductById(id);

        if(product == null){
//            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }
        String pitch  = productPitchService.generatePitch(product);
        ProductPitchDto productPitchDto = new ProductPitchDto();
        productPitchDto.setProductName(product.getName());
        productPitchDto.setProductId(product.getId());
        productPitchDto.setPitchNotification(pitch);
//        return new ResponseEntity<>(from(product), HttpStatus.OK);
        return ResponseEntity.ok(productPitchDto);

    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productRequestDto){
        try {
            Product response= productService.createProduct(productMapper.toEntity(productRequestDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("Message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("Message",e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<Page<ProductReponseDto>> getAllProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                                                  @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        Page<Product> productPage=productService.getAllProduct(page, size,sortBy,sortDir);
        List<Product> products = productPage.getContent();
        List<ProductReponseDto> productdto=new ArrayList<>();
        for(Product product:products){
            productdto.add(productMapper.toDto(product));
        }
        Page<ProductReponseDto> dtoPage = new PageImpl<>(
                productdto,
                productPage.getPageable(),
                productPage.getTotalElements()
        );

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search")
    public PagedModel<ProductReponseDto> searchAllProduct(@RequestParam(value = "minprice",required = false)BigDecimal minPrice,
                                                          @RequestParam(value = "maxprice",required = false)BigDecimal maxPrice,
                                                          Pageable pageable

    ){

        Page<Product> productPage=productService.search(minPrice,maxPrice,pageable);
        Page<ProductReponseDto> productDtos = productPage.map(product -> productMapper.toDto(product));
        return new PagedModel<>(productDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id){
        boolean deleted=productService.deleteProduct(id);
        if(deleted){
            return ResponseEntity.ok().body(Map.of("Message","Deleted successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("Message","Something went wrong"));
    }

    @PostMapping("/import")
    public ResponseEntity<?> importProduct(){
        final int importPageSize = 100;
        int pageNumber = 0;
        int count=0;
        String sortBy="id";
        String sortDir="ASC";
        Page<Product> supplierPage;
        do {
            supplierPage = supplierService.getAllProduct(pageNumber, importPageSize,sortBy,sortDir);
            for (Product supplierProduct : supplierPage.getContent()) {
                Product localProduct = new Product();
                localProduct.setName(supplierProduct.getName());
                localProduct.setDescription(supplierProduct.getDescription());
                localProduct.setPrice(supplierProduct.getPrice());
                localProduct.setImageUrl(supplierProduct.getImageUrl());
                localProduct.setStocks(supplierProduct.getStocks());

                if (supplierProduct.getCategory() != null) {
                    Category category = new Category();
                    category.setName(supplierProduct.getCategory().getName());
                    category.setDescription(supplierProduct.getCategory().getDescription());
                    localProduct.setCategory(category);
                }

                productService.createProduct(localProduct);
                count++;
            }
            pageNumber++;
        } while (supplierPage.hasNext());
        return ResponseEntity.ok().body(Map.of("Message",count+" Number of products imported from Supplier"));
    }
}
