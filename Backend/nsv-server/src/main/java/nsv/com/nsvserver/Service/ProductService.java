package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Client.ImageService;
import nsv.com.nsvserver.Dto.ProductCreateDto;
import nsv.com.nsvserver.Dto.ProductDto;
import nsv.com.nsvserver.Dto.ProductTypeQualityDto;
import nsv.com.nsvserver.Dto.TypeCreateDto;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Entity.Type;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.ProductExistsException;
import nsv.com.nsvserver.Exception.UploadImageException;
import nsv.com.nsvserver.Repository.ProductRepository;
import nsv.com.nsvserver.Repository.QualityRepository;
import nsv.com.nsvserver.Repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private QualityRepository qualityRepository;
    private ImageService imageService;

    private TypeRepository typeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, QualityRepository qualityRepository, ImageService imageService, TypeRepository typeRepository) {
        this.productRepository = productRepository;
        this.qualityRepository = qualityRepository;
        this.imageService = imageService;
        this.typeRepository = typeRepository;
    }

    public Product getProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product  = productOptional.orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return product;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product->new ProductDto(Product)).collect(Collectors.toList());
    }
    @Transactional
    public void createNewProduct(String name, String variety, String base64Img) throws Exception {

            Product product= new Product();
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

}
