package nsv.com.nsvserver;
import nsv.com.nsvserver.Client.ImageService;
import nsv.com.nsvserver.Dto.TypeCreateDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private QualityRepository qualityRepository;
    @Mock
    private ImageService imageService;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private ProductDao productDaoImpl;

    @InjectMocks
    ProductService productService;

    @Test
    public void GetProductById_Found_ReturnSingleResult() {
        // Mocking ProductRepository behavior
        Product product = new Product();
        product.setName("test");
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        // Testing ProductService method
        Product result = productService.getProductById(1);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
    }

    @Test
    public void GetProductById_NotFound_NotFoundExceptionThrown() {
        // Mocking ProductRepository behavior
        Product product = new Product();
        product.setName("test");
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());


        Exception exception = assertThrows(NotFoundException.class, () -> {
            productService.getProductById(1);
        });


        verify(productRepository, times(1)).findById(anyInt());
        // assert that exception is thrown with correct message
        assertThat(exception.getMessage()).isEqualTo("Product not found with id: 1" );

    }

    @Test
    public void createNewProduct_ProductExist_ExistExceptionThrown() throws Exception {

        Product product= new Product();
        String productName = "test";
        product.setName(productName);
        when(productRepository.existsByNameIgnoreCase(productName)).thenReturn(true);

        Exception exception = assertThrows(ExistsException.class, () -> {
            productService.createNewProduct(productName, "" ,"");
        });


        verify(productRepository, times(1)).existsByNameIgnoreCase(productName);
        // assert that exception is thrown with correct message
        assertThat(exception.getMessage()).isEqualTo("Product with name: test already exists" );


    }

    @Test
    public void createNewProduct_ProductNotExist_SaveProduct() throws Exception {

        String productName = "test";
        String variety = "variety";
        String image = "image";

        when(productRepository.existsByNameIgnoreCase(productName)).thenReturn(false);
        when(imageService.upLoadImageWithBase64(image)).thenReturn(image);

        productService.createNewProduct(productName, variety ,image);

        verify(productRepository, times(1)).existsByNameIgnoreCase(productName);
        verify(imageService, times(1)).upLoadImageWithBase64(image);
        verify(productRepository, times(1)).save(argThat(product1 -> {
            return product1.getName().equals(productName);
        }));

    }

    @Test
    public void CreateNewTypeForProduct_ProductFound_SaveType() throws Exception {

        Integer productId =1;
        Product product = new Product();
        product.setId(1);
        TypeCreateDto  typeCreateDto = new TypeCreateDto();
        String typeName ="type";
        String image = "image";
        typeCreateDto.setName(typeName);
        typeCreateDto.setImage(image);



        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(imageService.upLoadImageWithBase64(anyString())).thenReturn("image");

        productService.createNewTypeForProduct(typeCreateDto,productId);

        verify(productRepository, times(1)).findById(productId);
        verify(imageService, times(1)).upLoadImageWithBase64(anyString());
        verify(productRepository, times(1)).save(argThat(product1->{
            return product1.getTypes().get(0).getName().equals(typeName);
        }));
    }

//    @Test
//    public void testCreateNewTypeForProduct_ProductNotFound() {
//        TypeCreateDto typeCreateDto = new TypeCreateDto();
//        Integer productId = 1;
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> productService.createNewTypeForProduct(typeCreateDto, productId));
//
//        verify(productRepository, times(1)).findById(productId);
//        verifyNoInteractions(imageService);
//        verifyNoInteractions(typeRepository);
//    }
//
//    @Test
//
//    public void testCreateQualityForType_Success() throws Exception {
//        Quality quality = new Quality();
//        Integer typeId = 1;
//
//        Type type = new Type();
//        type.setId(typeId);
//
//        when(typeRepository.findById(typeId)).thenReturn(Optional.of(type));
//
//        assertDoesNotThrow(() -> productService.createQualityForType(quality, typeId));
//
//        verify(typeRepository, times(1)).findById(typeId);
//        verify(typeRepository, times(1)).save(type);
//    }
//
//    @Test
//    public void testCreateQualityForType_TypeNotFound() {
//        Quality quality = new Quality();
//        Integer typeId = 1;
//
//        when(typeRepository.findById(typeId)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> productService.createQualityForType(quality, typeId));
//
//        verify(typeRepository, times(1)).findById(typeId);
//        verifyNoInteractions(productRepository);
//    }
//}



}
