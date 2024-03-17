package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.ProductCreateDto;
import nsv.com.nsvserver.Dto.ProductDto;
import nsv.com.nsvserver.Dto.TypeCreateDto;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Type;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.ProductExistsException;
import nsv.com.nsvserver.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product->new ProductDto(Product)).collect(Collectors.toList());
    }
    @Transactional
    public void createNewProduct(ProductCreateDto productCreateDto) {
        try{
            Product product= new Product(productCreateDto);
            productRepository.save(product);
        }
        catch(DataIntegrityViolationException e){
            throw new ProductExistsException("The product with name: " + productCreateDto.getName() + " already exists");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }
    @Transactional
    public void createNewTypeForProduct(TypeCreateDto typeCreateDto, Integer id) {
        try{
            Type type= new Type(typeCreateDto);
            Optional<Product> productOptional = productRepository.findById(id);
            Product product  = productOptional.orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
            //bidirectional
            product.addType(type);
            type.addProduct(product);
            productRepository.save(product);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

}
