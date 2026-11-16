package ProductCatalog.example.ProductCatalog.Controller;

import ProductCatalog.example.ProductCatalog.DTO.ProductReponseDto;
import ProductCatalog.example.ProductCatalog.DTO.ProductRequestDto;
import ProductCatalog.example.ProductCatalog.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/products/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{Id}")
    public ResponseEntity<?> getProduct(@PathVariable("Id") long id){
        try {
            ProductReponseDto product = productService.getProduct(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("Message", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message", e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(500).body(Map.of("Message",e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productRequestDto){
        try {
            ProductReponseDto response= productService.addProduct(productRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("Message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("Message",e.getMessage()));
        }

    }
}
