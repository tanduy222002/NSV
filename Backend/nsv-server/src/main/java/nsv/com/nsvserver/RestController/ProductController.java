package nsv.com.nsvserver.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import nsv.com.nsvserver.Dto.EmployeeDto;
import nsv.com.nsvserver.Dto.ProductCreateDto;
import nsv.com.nsvserver.Dto.ProductDto;
import nsv.com.nsvserver.Dto.TypeCreateDto;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get product list successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDto.class)) }),

            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })
    public ResponseEntity<?> getAllProduct() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    }


    @PostMapping("")
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")

    })
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
            productService.createNewProduct(productCreateDto);
        return ResponseEntity.ok("New product is added successfully");
    }


    @PostMapping("/{productId}/types")
    @Operation(summary = "Create a new type for product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create type for product successfully"),
            @ApiResponse(responseCode = "500", description = "Internal  Server Error")
    })
    public ResponseEntity<?> createTypeForProduct(@PathVariable Integer productId,@Valid @RequestBody TypeCreateDto typeCreateDto) {
        productService.createNewTypeForProduct(typeCreateDto,productId);
        return ResponseEntity.ok("New type is added successfully");
    }

}
