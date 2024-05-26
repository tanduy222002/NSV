package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Client.ImageService;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.Partner;
import nsv.com.nsvserver.Entity.Profile;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.UploadImageException;
import nsv.com.nsvserver.Repository.PartnerDao;
import nsv.com.nsvserver.Repository.PartnerRepository;
import nsv.com.nsvserver.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerService {
    PartnerRepository partnerRepository;

    AddressService addressService;

    PartnerDao partnerDaoImpl;

    ImageService imageServiceImpl;

    ProfileRepository profileRepository;


    @Autowired
    public PartnerService(PartnerRepository partnerRepository, AddressService addressService, PartnerDao partnerDaoImpl, ImageService imageServiceImpl, ProfileRepository profileRepository) {
        this.partnerRepository = partnerRepository;
        this.addressService = addressService;
        this.partnerDaoImpl = partnerDaoImpl;
        this.imageServiceImpl = imageServiceImpl;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public void createPartner(CreatePartnerDto createPartnerDto){

        Profile partnerProfile =new Profile();
        partnerProfile.setName(createPartnerDto.getName());
        partnerProfile.setEmail(createPartnerDto.getEmail());
        partnerProfile.setPhoneNumber(createPartnerDto.getPhoneNumber());

        if (createPartnerDto.getAvatar()!=null){
            try{
                String imageUrl = imageServiceImpl.upLoadImageWithBase64(createPartnerDto.getAvatar());
                partnerProfile.setAvatar(imageUrl);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                throw new UploadImageException();
            }
        }

        AddressDto addressDto = createPartnerDto.getAddress();
        Address address = addressService.createAddress(addressDto.getAddress(),addressDto.getWardId(),addressDto.getDistrictId(), addressDto.getProvinceId());
        address.setProfile(partnerProfile);
//        partnerProfile.setAddress(address);

        Partner partner =new Partner();
        partner.setBankAccount(createPartnerDto.getBankAccount());
        partner.setFaxNumber(createPartnerDto.getFaxNumber());
        partner.setTaxNumber(createPartnerDto.getTaxNumber());
        partner.setProfile(partnerProfile);

        partnerRepository.save(partner);

    }

    public PageDto searchPartnerByFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String phone) {
        List<SearchPartnerDto> partners= partnerDaoImpl.searchWithFilterAndPagination(pageIndex,pageSize,name,phone);
        long count=partnerDaoImpl.countSearchWithFilter(name,phone);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,partners);
    }

    public PageDto getPartnersStatisticByFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String phone) {
        List<SearchPartnerDto> partners= partnerDaoImpl.getStatisticWithFilterAndPagination(pageIndex,pageSize,name,phone);
        long count=partnerDaoImpl.countGetStatisticWithFilter(name,phone);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,partners);
    }

    public PartnerDetailDto getPartnerDetailById(Integer id){
        return partnerDaoImpl.getPartnerDetailById(id);
    }

    public PageDto getPartnerTransactionById(Integer pageIndex, Integer pageSize, Integer id, String name, Boolean isPaid){
        List<TransferTicketDto> transactions=
                partnerDaoImpl.getTransactionsOfPartnerById(pageIndex, pageSize, id, name, isPaid);
        long count = partnerDaoImpl.countTransactionsOfPartnerById(pageIndex, pageSize, id, name, isPaid);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,transactions);
    }


    public PageDto getPartnerDebtById(Integer pageIndex, Integer pageSize, Integer id, Boolean isPaid){
        List<DebtDetailDto> debts
                = partnerDaoImpl.getDebtsOfPartnerById(pageIndex, pageSize, id, isPaid);
        long count = partnerDaoImpl.countDebtsOfPartnerById(pageIndex, pageSize, id, isPaid);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,debts);
    }

    @Transactional
    public void updatePartnerProfile(Integer id, UpdatePartnerDto dto) {
        Partner partner=partnerRepository.findById(id).orElseThrow(()->new NotFoundException("Partner not found"));
        Profile profile = partner.getProfile();
        AddressDto addressDtos = dto.getAddress();
        Address address=profile.getAddress();

        if(addressDtos!=null){
            profile.setAddress(null);
            address = addressService.createAddress(
                    addressDtos.getAddress(), addressDtos.getWardId(),
                    addressDtos.getDistrictId(), addressDtos.getProvinceId());
            profile.setAddress(address);
        }

        if(dto.getName()!=null){
            profile.setName(dto.getName());
        }

        if(dto.getBankAccount()!=null){
            partner.setBankAccount(dto.getBankAccount());
        }

        if(dto.getTaxNumber()!=null){
            partner.setTaxNumber(dto.getTaxNumber());
        }

        if(dto.getFaxNumber()!=null){
            partner.setFaxNumber(dto.getFaxNumber());
        }

        if(dto.getEmail()!=null){
            if(profileRepository.existsByEmail(dto.getEmail())
                    &&!dto.getEmail().equals(profile.getEmail())){
                throw new ExistsException("Email already taken");
            }
            profile.setEmail(dto.getEmail());
        }
        if(dto.getPhoneNumber()!=null){
            profile.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getAvatar()!=null){
            try{
                String imageUrl = imageServiceImpl.upLoadImageWithBase64(dto.getAvatar());
                profile.setAvatar(imageUrl);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                throw new UploadImageException();
            }
        }


    }


}
