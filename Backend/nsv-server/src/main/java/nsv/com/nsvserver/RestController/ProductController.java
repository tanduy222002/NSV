package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    @Operation(summary = "Get all products")

    public ResponseEntity<?> getAllProduct() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by id")

    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new ProductDto(product));
    }



    @PostMapping("")
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })
    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductCreateDto productDto) throws Exception {
        productService.createNewProduct(
                productDto.getName()
                ,productDto.getVariety()
                ,productDto.getImage()
        );
        return ResponseEntity.ok("New product is added successfully");
    }


    @PostMapping("/{productId}/types")
    @Operation(summary = "Create a new type for product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create type for product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")
    })
    public ResponseEntity<?> createTypeForProduct(@PathVariable Integer productId,@Valid @RequestBody TypeCreateDto typeCreateDto) throws Exception{
        productService.createNewTypeForProduct(typeCreateDto,productId);
        return ResponseEntity.ok("New type is added successfully");
    }


    @PostMapping("/types/{typeId}")
    @Operation(summary = "Create a new quality for type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create type for product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")
    })
    public ResponseEntity<?> createQualityForType(@PathVariable Integer typeId, @Valid @RequestBody QualityCreateDto qualityCreateDto) throws Exception{
        productService.createQualityForType(new Quality(qualityCreateDto),typeId);
        return ResponseEntity.ok("New type is added successfully");
    }

    @PostMapping("/products/types/qualities")
    @Operation(summary = "Create a new quality for type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create type for product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")
    })
    public ResponseEntity<?> createProductTypeQuality(@Valid @RequestBody ProductTypeQualityDto dto) throws Exception{
        productService.createProductQualityType(dto);
        return ResponseEntity.ok("New product with types and qualities is added successfully");
    }

}
