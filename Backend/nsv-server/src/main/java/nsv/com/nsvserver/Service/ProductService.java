package nsv.com.nsvserver.Service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import nsv.com.nsvserver.Client.ImageService;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Entity.Type;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.ProductExistsException;
import nsv.com.nsvserver.Exception.UploadImageException;
import nsv.com.nsvserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private QualityRepository qualityRepository;
    private ImageService imageService;

    private TypeRepository typeRepository;

    private ProductDao productDaoImpl;

    @Autowired
    public ProductService(ProductRepository productRepository, QualityRepository qualityRepository, ImageService imageService, TypeRepository typeRepository, ProductDao productDaoImpl) {
        this.productRepository = productRepository;
        this.qualityRepository = qualityRepository;
        this.imageService = imageService;
        this.typeRepository = typeRepository;
        this.productDaoImpl = productDaoImpl;
    }

    public Product getProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product  = productOptional.orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return product;
    }

    public PageDto getAllProducts(Integer pageIndex,Integer pageSize, String name, String variety) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        Specification<Product> specifications = new ProductSpecification(new SearchCriteria());

        if(name!=null && !name.isBlank()){
            ProductSpecification nameSpecification = new ProductSpecification(new SearchCriteria("name", ":",name));
            specifications=specifications.and(nameSpecification);
        }

        if(variety!=null && !variety.isBlank()){
            ProductSpecification varietySpecification = new ProductSpecification(new SearchCriteria("variety", ":",variety));
            specifications=specifications.and(varietySpecification);
        }
        Page<Product> page = productRepository.findAll(specifications,pageable);
        List<ProductDto> productDtos=page.getContent().stream().map(ProductDto::new).collect(Collectors.toList());
        return new PageDto(page.getTotalPages(),page.getTotalElements(),pageIndex,productDtos);

    }
    @Transactional
    public void createNewProduct(String name, String variety, String base64Img) throws Exception {

            Product product= new Product();
            if(productRepository.existsByNameIgnoreCase(name)){
                throw new ExistsException("Product with name: "+name+" already exists");
            }
            product.setName(name);
            product.setVariety(variety);
            String imgUrl = imageService.upLoadImageWithBase64(base64Img);

            product.setImage(imgUrl);

            productRepository.save(product);

    }
    @Transactional
    public void createNewTypeForProduct(TypeCreateDto typeCreateDto, Integer id) throws Exception {

            Type type= new Type(typeCreateDto);
            Optional<Product> productOptional = productRepository.findById(id);
            Product product  = productOptional.orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
            //bidirectional
            product.addType(type);
            String imgUrl=imageService.upLoadImageWithBase64(type.getImage());
            type.setImage(imgUrl);
            type.addProduct(product);

            productRepository.save(product);

    }

    @Transactional
    public void createQualityForType(Quality quality, Integer id) throws Exception {
        Optional<Type> TypeOptional = typeRepository.findById(id);
        Type type  = TypeOptional.orElseThrow(() -> new NotFoundException("Type not found with id: " + id));
        //bidirectional
        type.addQuality(quality);
        typeRepository.save(type);

    }
    @Transactional
    public void createProductQualityType(ProductTypeQualityDto dto) throws Exception {
        String productName = dto.getProductCreateDto().getName();
        if(productRepository.existsByNameIgnoreCase(productName)){
            throw new ExistsException("Product with name: "+productName+" already exists");
        }
        Product product =new Product(dto.getProductCreateDto());
        product.setImage(imageService.upLoadImageWithBase64(dto.getProductCreateDto().getImage()));
        List<Type> type=dto.getTypeWithQualityListDto().stream().map(
                typeWithQualityDto -> {
                    Type currType = new Type(typeWithQualityDto);
                    try {
                        currType.setImage(imageService.upLoadImageWithBase64(currType.getImage()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    product.addType(currType);
                    currType.setProduct(product);

                    List<Quality> qualities= typeWithQualityDto.getQualityCreateDtoList().stream().map(
                            qualityCreateDto -> {
                                Quality currQuality=new Quality(qualityCreateDto);
                                currQuality.setType(currType);
                                currType.addQuality(currQuality);
                                return currQuality;
                            }).collect(Collectors.toList());
                    return currType;
                }).collect(Collectors.toList());

        productRepository.save(product);


    }
    public List<QualityInTypeDto> getQualityCombineType(Integer productId){
        Product product=productRepository.findWithEagerQualityAndType(productId).orElseThrow(
                ()-> new NotFoundException("Product not found with id " + productId)
        );
        List<QualityInTypeDto> dtos = product.getTypes().stream().flatMap(type ->
                type.getQualities().parallelStream().map(quality -> {
                    QualityInTypeDto dto = new QualityInTypeDto();
                    dto.setQualityId(quality.getId());
                    dto.setName(type.getName() + " " + quality.getName());
                    return dto;
                })
        ).collect(Collectors.toList());
        return dtos;
    }

    public List<TypeDto> getTypeInProduct( Integer productId){
        return productDaoImpl.getTypesInProductByProductId(productId);
    }

    public List<QualityDto> getQualitiesInType( Integer typeId){
        return productDaoImpl.getQualitiesInTypeByTypeId(typeId);
    }

    public PageDto getProductDetailsWithFilterPagination(Integer pageIndex,Integer pageSize, String name){
        List<ProductDetailDto> productDetails = productDaoImpl.getProductDetails(pageIndex, pageSize, name);
        long count= productDaoImpl.countTotalProductDetailsWithFilterAndPagination(name);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,productDetails);
    }


}
