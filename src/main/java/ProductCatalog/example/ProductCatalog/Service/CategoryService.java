package ProductCatalog.example.ProductCatalog.Service;

import ProductCatalog.example.ProductCatalog.DTO.CategoryRequestDto;
import ProductCatalog.example.ProductCatalog.DTO.CategoryResponseDto;
import ProductCatalog.example.ProductCatalog.Models.Category;
import ProductCatalog.example.ProductCatalog.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto){
        String name=categoryRequestDto.getName();
        String description=categoryRequestDto.getDescription();

        Optional<Category> optionalCategory=categoryRepository.findByName(name);
        if(optionalCategory.isPresent()){
            throw new RuntimeException("Category with "+name+" already exist");
        }

        Category category=new Category();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);

        CategoryResponseDto response=new CategoryResponseDto();
        response.setDescription(category.getDescription());
        response.setName(category.getName());
        return response;

    }
}
