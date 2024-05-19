package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.PartnerDao;
import nsv.com.nsvserver.Repository.PartnerRepository;
import nsv.com.nsvserver.Service.AddressService;
import nsv.com.nsvserver.Service.PartnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartnerServiceTest {

    @Mock
    PartnerRepository partnerRepository;

    @Mock
    AddressService addressService;

    @Mock
    PartnerDao partnerDaoImpl;

    @InjectMocks
    private PartnerService partnerService;


    @Test
    public void testCreatePartner() {
        // Arrange
        CreatePartnerDto createPartnerDto = new CreatePartnerDto();
        createPartnerDto.setName("Partner");
        createPartnerDto.setEmail("partner@test.com");
        createPartnerDto.setPhoneNumber("1234567890");
        createPartnerDto.setBankAccount("123456");
        createPartnerDto.setFaxNumber("987654321");
        createPartnerDto.setTaxNumber("123456789");

        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Street");
        addressDto.setWardId(1);
        addressDto.setDistrictId(2);
        addressDto.setProvinceId(3);
        createPartnerDto.setAddress(addressDto);

        Address address = new Address();
        address.setName("Street");
        Ward ward = new Ward();
        ward.setName("ward");
        address.setWard(ward);
        District district = new District();
        district.setName("district");
        ward.setDistrict(district);
        Province province = new Province();
        province.setName("province");
        district.setProvince(province);
        when(addressService.createAddress(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(address);

        // Act
        partnerService.createPartner(createPartnerDto);

        // Assert
        verify(addressService, times(1)).createAddress(anyString(), anyInt(), anyInt(), anyInt());
        verify(partnerRepository, times(1)).save(any(Partner.class));

    }

    @Test
    public void searchPartnerByFilterAndPagination() {
        // Arrange
        Integer pageIndex = 1;
        Integer pageSize = 10;
        String name = "Name";
        String phone = "1234567890";

        List<SearchPartnerDto> partners = new ArrayList<>();
        partners.add(new SearchPartnerDto());

        when(partnerDaoImpl.searchWithFilterAndPagination(pageIndex, pageSize, name, phone)).thenReturn(partners);
        when(partnerDaoImpl.countSearchWithFilter(name, phone)).thenReturn(20L);

        // Act
        PageDto result = partnerService.searchPartnerByFilterAndPagination(pageIndex, pageSize, name, phone);

        // Assert
        assertEquals(2.0, result.getTotalPage());  // 20 items, 10 per page -> 2 pages
        assertEquals(20L, result.getTotalElement());
        assertEquals(1, result.getPage());
        assertEquals(partners, result.getContent());
    }
    @Test
    public void testGetPartnersStatisticByFilterAndPagination() {
        // Arrange
        Integer pageIndex = 1;
        Integer pageSize = 10;
        String name = "name";
        String phone = "1234567890";

        List<SearchPartnerDto> partners = new ArrayList<>();
        partners.add(new SearchPartnerDto());

        when(partnerDaoImpl.getStatisticWithFilterAndPagination(pageIndex, pageSize, name, phone)).thenReturn(partners);
        when(partnerDaoImpl.countGetStatisticWithFilter(name, phone)).thenReturn(20L);

        // Act
        PageDto result = partnerService.getPartnersStatisticByFilterAndPagination(pageIndex, pageSize, name, phone);

        // Assert
        assertEquals(2.0, result.getTotalPage());
        assertEquals(20L, result.getTotalElement());
        assertEquals(1, result.getPage());
        assertEquals(partners, result.getContent());
    }

    @Test
    public void testGetPartnerDetailById() {
        // Arrange
        Integer partnerId = 1;
        PartnerDetailDto partnerDetailDto = new PartnerDetailDto();// Dummy PartnerDetailDto

        when(partnerDaoImpl.getPartnerDetailById(partnerId)).thenReturn(partnerDetailDto);

        // Act
        PartnerDetailDto result = partnerService.getPartnerDetailById(partnerId);

        // Assert
        assertEquals(partnerDetailDto, result);
    }

    @Test
    public void testGetPartnerTransactionById() {
        // Arrange
        Integer pageIndex = 1;
        Integer pageSize = 10;
        Integer partnerId = 1;
        String name = "Transaction Name";
        Boolean isPaid = true;

        List<TransferTicketDto> transactions = new ArrayList<>();
        transactions.add(new TransferTicketDto());

        when(partnerDaoImpl.getTransactionsOfPartnerById(pageIndex, pageSize, partnerId, name, isPaid)).thenReturn(transactions);
        when(partnerDaoImpl.countTransactionsOfPartnerById(pageIndex, pageSize, partnerId, name, isPaid)).thenReturn(20L);

        // Act
        PageDto result = partnerService.getPartnerTransactionById(pageIndex, pageSize, partnerId, name, isPaid);

        // Assert
        assertEquals(2.0, result.getTotalPage());
        assertEquals(20L, result.getTotalElement());
        assertEquals(1, result.getPage());
        assertEquals(transactions, result.getContent());
    }
    @Test
    public void getPartnerDebtById() {
        // Arrange
        Integer pageIndex = 1;
        Integer pageSize = 10;
        Integer partnerId = 1;
        Boolean isPaid = true;

        List<DebtDetailDto> debts = new ArrayList<>();
        debts.add(new DebtDetailDto());  // Add some dummy data

        when(partnerDaoImpl.getDebtsOfPartnerById(pageIndex, pageSize, partnerId, isPaid)).thenReturn(debts);
        when(partnerDaoImpl.countDebtsOfPartnerById(pageIndex, pageSize, partnerId, isPaid)).thenReturn(2L);

        // Act
        PageDto result = partnerService.getPartnerDebtById(pageIndex, pageSize, partnerId, isPaid);

        // Assert
        assertEquals(1.0, result.getTotalPage());
        assertEquals(2L, result.getTotalElement());
        assertEquals(1, result.getPage());
        assertEquals(debts, result.getContent());
    }


}