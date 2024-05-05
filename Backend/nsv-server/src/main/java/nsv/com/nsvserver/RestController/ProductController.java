package nsv.com.nsvserver.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.District;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @Operation(summary = "Get products with filter and pagination")
    public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "1") @Min(1) Integer pageIndex,
                                           @RequestParam(defaultValue = "5") @Min(1) Integer pageSize,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String variety) {
        PageDto page= productService.getAllProducts(pageIndex,pageSize,name,variety);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by id")

    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new ProductDto(product));
    }

    @GetMapping("/variety")
    @Operation(summary = "Get variety of products")
    public ResponseEntity<?> getVariety() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("static/variety.json");
        List<String> data = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<String>>() {});
        return ResponseEntity.ok(data);
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


    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
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


    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
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

    @Secured({ "ROLE_MANAGER", "ROLE_ADMIN" })
    @PostMapping("/types/qualities")
    @Operation(summary = "Create a new product with type and quality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create type for product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")
    })
    public ResponseEntity<?> createProductTypeQuality(@Valid @RequestBody ProductTypeQualityDto dto) throws Exception{
        productService.createProductQualityType(dto);
        return ResponseEntity.ok("New product with types and qualities is added successfully");
    }

    @GetMapping("/{productId}/quality_with_type")
    @Operation(summary = "Get quality combine type of products")
    public List<QualityInTypeDto> getQualityCombineType(@PathVariable Integer productId){
       return productService.getQualityCombineType(productId);
    }

    @GetMapping("/{productId}/types")
    @Operation(summary = "Get types of products")
    public List<TypeDto> getTypesInProduct(@PathVariable Integer productId){
        return productService.getTypeInProduct(productId);
    }

    @GetMapping("/{productId}/type/{typeId}/qualities")
    @Operation(summary = "Get qualities of type")
    public List<QualityDto> getQualitiesInType(@PathVariable Integer productId,@PathVariable Integer typeId){
        return productService.getQualitiesInType(typeId);
    }

}
