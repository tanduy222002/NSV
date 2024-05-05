package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.Partner;
import nsv.com.nsvserver.Entity.Profile;
import nsv.com.nsvserver.Repository.PartnerDao;
import nsv.com.nsvserver.Repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerService {
    PartnerRepository partnerRepository;

    AddressService addressService;

    PartnerDao partnerDaoImpl;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, AddressService addressService, PartnerDao partnerDaoImpl) {
        this.partnerRepository = partnerRepository;
        this.addressService = addressService;
        this.partnerDaoImpl = partnerDaoImpl;
    }

    @Transactional
    public void createPartner(CreatePartnerDto createPartnerDto){

        Profile partnerProfile =new Profile();
        partnerProfile.setName(createPartnerDto.getName());
        partnerProfile.setEmail(createPartnerDto.getEmail());
        partnerProfile.setPhoneNumber(createPartnerDto.getPhoneNumber());
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
}