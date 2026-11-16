package ProductCatalog.example.ProductCatalog.Controller;

import ProductCatalog.example.ProductCatalog.DTO.CategoryRequestDto;
import ProductCatalog.example.ProductCatalog.DTO.CategoryResponseDto;
import ProductCatalog.example.ProductCatalog.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        try {
            CategoryResponseDto responseDto=categoryService.addCategory(categoryRequestDto);
            return ResponseEntity.status(201).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("Message",e.getMessage()));
        }
    }
}
