package nsv.com.nsvserver;

import nsv.com.nsvserver.Client.ImageService;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Entity.Type;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void testCreateNewTypeForProduct_ProductNotFound() {
        TypeCreateDto typeCreateDto = new TypeCreateDto();
        Integer productId = 1;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.createNewTypeForProduct(typeCreateDto, productId));

        verify(productRepository, times(1)).findById(productId);

    }

    @Test
    public void createQualityForType_Success() {
        Integer qualityId=1;
        Quality quality = new Quality();
        quality.setId(qualityId);
        Integer typeId = 1;

        Type type = new Type();
        type.setId(typeId);



        when(typeRepository.findById(typeId)).thenReturn(Optional.of(type));
        productService.createQualityForType(quality,typeId);

        verify(typeRepository, times(1)).findById(typeId);
        verify(typeRepository, times(1)).save(argThat(savedType->{
            return savedType.getQualities().get(0).getId().equals(qualityId);
        }));
    }

    @Test
    public void testCreateQualityForType_TypeNotFound() {
        Quality quality = new Quality();
        Integer typeId = 1;

        when(typeRepository.findById(typeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.createQualityForType(quality, typeId));

        verify(typeRepository, times(1)).findById(typeId);

    }

    @Test
    public void testGetTypeInProduct() {
        // Arrange
        Integer productId = 1;
        List<TypeDto> expectedTypes = Arrays.asList(new TypeDto(), new TypeDto());
        when(productDaoImpl.getTypesInProductByProductId(productId)).thenReturn(expectedTypes);

        // Act
        List<TypeDto> actualTypes = productService.getTypeInProduct(productId);

        verify(productDaoImpl,times(1)).getTypesInProductByProductId(productId);
        // Assert
        assertEquals(expectedTypes, actualTypes);

    }

    @Test
    public void testGetQualitiesInType() {
        // Arrange
        Integer typeId = 1;
        List<QualityDto> expectedQualities = Arrays.asList(new QualityDto(), new QualityDto());
        when(productDaoImpl.getQualitiesInTypeByTypeId(typeId)).thenReturn(expectedQualities);

        // Act
        List<QualityDto> actualQualities = productService.getQualitiesInType(typeId);

        verify(productDaoImpl,times(1)).getQualitiesInTypeByTypeId(typeId);
        // Assert
        assertEquals(expectedQualities, actualQualities);

    }

    @Test
    public void getProductDetailsWithFilterPagination() {
        // Arrange
        Integer pageIndex = 1;
        Integer pageSize = 10;
        String name = "test";
        List<ProductDetailDto> expectedProductDetails = Arrays.asList(new ProductDetailDto());
        long expectedCount = 20L;
        when(productDaoImpl.getProductDetails(pageIndex, pageSize, name)).thenReturn(expectedProductDetails);
        when(productDaoImpl.countTotalProductDetailsWithFilterAndPagination(name)).thenReturn(expectedCount);

        // Act
        PageDto actualPageDto = productService.getProductDetailsWithFilterPagination(pageIndex, pageSize, name);

        // Assert
        verify(productDaoImpl,times(1)).getProductDetails(pageIndex, pageSize, name);
        verify(productDaoImpl,times(1)).countTotalProductDetailsWithFilterAndPagination(name);

        assertEquals(Math.ceil((double) expectedCount / pageSize), actualPageDto.getTotalPage());
        assertEquals(expectedCount, actualPageDto.getTotalElement());
        assertEquals(pageIndex, actualPageDto.getPage());
        assertEquals(expectedProductDetails, actualPageDto.getContent());

    }

    @Test
    public void getProductTypesWithQualityStatisticDetail() {
        // Arrange
        Integer productId = 1;
        List<ProductTypeWithQualityDetailInSlotDto> expectedDetails = Arrays.asList(new ProductTypeWithQualityDetailInSlotDto());
        when(productDaoImpl.getStatisticsOfProduct(productId)).thenReturn(expectedDetails);

        // Act
        List<ProductTypeWithQualityDetailInSlotDto> actualDetails = productService.getProductTypesWithQualityStatisticDetail(productId);

        verify(productDaoImpl,times(1)).getStatisticsOfProduct(productId);
        // Assert
        assertEquals(expectedDetails, actualDetails);

    }

//    @Test
//    public void testCreateProductQualityType() throws Exception {
//        // Arrange
//        QualityCreateDto qualityCreateDto = new QualityCreateDto();
//
//        ProductTypeQualityDto dto = new ProductTypeQualityDto();
//
//        ProductCreateDto productCreateDto = new ProductCreateDto();
//        productCreateDto.setName("Test Product");
//        productCreateDto.setImage("base64Image");
//
//        dto.setProductCreateDto(productCreateDto);
//        TypeWithQualityDto typeWithQualityListDto = new TypeWithQualityDto();
//        TypeCreateDto typeCreateDto = new TypeCreateDto();
//        typeCreateDto.setImage("base64TypeImage");
//        typeWithQualityListDto.setQualityCreateDtoList(Arrays.asList(qualityCreateDto));
//        dto.setTypeWithQualityListDto(Arrays.asList(typeWithQualityListDto));
//
//        when(productRepository.existsByNameIgnoreCase("Test Product")).thenReturn(false);
//        when(imageService.upLoadImageWithBase64("base64Image")).thenReturn("imageUrl");
//        when(imageService.upLoadImageWithBase64("base64TypeImage")).thenReturn("typeImageUrl");
//
//        // Act
//        productService.createProductQualityType(dto);
//
//        // Assert
//        verify(productRepository).save(any(Product.class));
//    }

    @Test
    public void createProductQualityType_ProductExist_ExistsExceptionThrown() {
        // Arrange
        ProductTypeQualityDto dto = new ProductTypeQualityDto();
        ProductCreateDto productCreateDto = new ProductCreateDto();
        productCreateDto.setName("Test Product");
        dto.setProductCreateDto(productCreateDto);

        when(productRepository.existsByNameIgnoreCase("Test Product")).thenReturn(true);

        // Act & Assert
        assertThrows(ExistsException.class, () -> productService.createProductQualityType(dto));
    }

    @Test
    public void getQualityCombineType_getResultList() {
        // Arrange
        Integer productId = 1;
        Product product = new Product();

        Type type = new Type();

        Quality quality = new Quality();

        quality.setId(1);
        quality.setName("Quality");

        type.setName("Type");
        type.setQualities(Arrays.asList(quality));
        product.setTypes(Arrays.asList(type));

        when(productRepository.findWithEagerQualityAndType(productId)).thenReturn(Optional.of(product));

        // Act
        List<QualityInTypeDto> dtos = productService.getQualityCombineType(productId);

        // Assert
        assertEquals(1, dtos.size());
        assertEquals("Type Quality", dtos.get(0).getName());
        assertEquals(1, dtos.get(0).getQualityId());
    }


    @Test
    public void testGetQualityCombineTypeThrowsNotFoundException() {
        // Arrange
        Integer productId = 1;
        when(productRepository.findWithEagerQualityAndType(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> productService.getQualityCombineType(productId));
    }

    @Test
    public void getAllProducts() {
        Integer pageIndex =1;
        Integer pageSize =5;
        String name ="name";
        String variety ="variety";
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        Specification<Product> specifications = new ProductSpecification(new SearchCriteria());
        Product product = new Product();
        product.setName(name);
        product.setVariety(variety);
        product.setId(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        Page<Product> page = new PageImpl<>(products,pageable,1);
        when(productRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(page);

        PageDto resultPage = productService.getAllProducts(pageIndex,pageSize,name,variety);



        verify(productRepository,times(1)).findAll(any(Specification.class),any(Pageable.class));
        assertEquals(1,resultPage.getTotalPage());
        ProductDto dto = (ProductDto) resultPage.getContent().get(0);
        assertEquals(products.get(0).getId(),dto.getId());

    }

    @Test
    public void getAllProducts_NameAndVarietyNull() {
        Integer pageIndex =1;
        Integer pageSize =5;
        String name =null;
        String variety =null;
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        Specification<Product> specifications = new ProductSpecification(new SearchCriteria());
        Product product = new Product();
        product.setName(name);
        product.setVariety(variety);
        product.setId(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        Page<Product> page = new PageImpl<>(products,pageable,1);
        when(productRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(page);

        PageDto resultPage = productService.getAllProducts(pageIndex,pageSize,name,variety);



        verify(productRepository,times(1)).findAll(any(Specification.class),any(Pageable.class));
        assertEquals(1,resultPage.getTotalPage());
        ProductDto dto = (ProductDto) resultPage.getContent().get(0);
        assertEquals(products.get(0).getId(),dto.getId());

    }



}
