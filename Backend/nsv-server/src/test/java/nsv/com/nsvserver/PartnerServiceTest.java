package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.AddressService;
import nsv.com.nsvserver.Service.EmployeeService;
import nsv.com.nsvserver.Service.PartnerService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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


//    @Test
//    public void testCreatePartner() {
//        // Arrange
//        CreatePartnerDto createPartnerDto = new CreatePartnerDto();
//        createPartnerDto.setName("Partner Name");
//        createPartnerDto.setEmail("partner@example.com");
//        createPartnerDto.setPhoneNumber("1234567890");
//        createPartnerDto.setBankAccount("123456");
//        createPartnerDto.setFaxNumber("987654321");
//        createPartnerDto.setTaxNumber("TAX12345");
//
//        AddressDto addressDto = new AddressDto();
//        addressDto.setAddress("123 Main St");
//        addressDto.setWardId(1);
//        addressDto.setDistrictId(2);
//        addressDto.setProvinceId(3);
//        createPartnerDto.setAddress(addressDto);
//
//        Address address = new Address();
//        when(addressService.createAddress(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(address);
//
//        // Act
//        partnerService.createPartner(createPartnerDto);
//
//        // Assert
//        verify(addressService, times(1)).createAddress(anyString(), anyInt(), anyInt(), anyInt());
//        verify(partnerRepository, times(1)).save(any(Partner.class));
//    }
//
//    @Test
//    public void testSearchPartnerByFilterAndPagination() {
//        // Arrange
//        Integer pageIndex = 1;
//        Integer pageSize = 10;
//        String name = "John";
//        String phone = "1234567890";
//
//        List<SearchPartnerDto> partners = new ArrayList<>();
//        partners.add(new SearchPartnerDto());  // Add some dummy data
//
//        when(partnerDaoImpl.searchWithFilterAndPagination(pageIndex, pageSize, name, phone)).thenReturn(partners);
//        when(partnerDaoImpl.countSearchWithFilter(name, phone)).thenReturn(20L);
//
//        // Act
//        PageDto result = partnerService.searchPartnerByFilterAndPagination(pageIndex, pageSize, name, phone);
//
//        // Assert
//        assertEquals(2, result.getTotalPages());  // 20 items, 10 per page -> 2 pages
//        assertEquals(20, result.getTotalElements());
//        assertEquals(1, result.getPageIndex());
//        assertEquals(partners, result.getContent());
//    }
//    @Test
//    public void testGetPartnersStatisticByFilterAndPagination() {
//        // Arrange
//        Integer pageIndex = 1;
//        Integer pageSize = 10;
//        String name = "John";
//        String phone = "1234567890";
//
//        List<SearchPartnerDto> partners = new ArrayList<>();
//        partners.add(new SearchPartnerDto());  // Add some dummy data
//
//        when(partnerDaoImpl.getStatisticWithFilterAndPagination(pageIndex, pageSize, name, phone)).thenReturn(partners);
//        when(partnerDaoImpl.countGetStatisticWithFilter(name, phone)).thenReturn(20L);
//
//        // Act
//        PageDto result = partnerService.getPartnersStatisticByFilterAndPagination(pageIndex, pageSize, name, phone);
//
//        // Assert
//        assertEquals(2, result.getTotalPages());  // 20 items, 10 per page -> 2 pages
//        assertEquals(20, result.getTotalElements());
//        assertEquals(1, result.getPageIndex());
//        assertEquals(partners, result.getContent());
//    }
//
//    @Test
//    public void testGetPartnerDetailById() {
//        // Arrange
//        Integer partnerId = 1;
//        PartnerDetailDto partnerDetailDto = new PartnerDetailDto();  // Dummy PartnerDetailDto
//
//        when(partnerDaoImpl.getPartnerDetailById(partnerId)).thenReturn(partnerDetailDto);
//
//        // Act
//        PartnerDetailDto result = partnerService.getPartnerDetailById(partnerId);
//
//        // Assert
//        assertEquals(partnerDetailDto, result);
//    }
//
//    @Test
//    public void testGetPartnerTransactionById() {
//        // Arrange
//        Integer pageIndex = 1;
//        Integer pageSize = 10;
//        Integer partnerId = 1;
//        String name = "Transaction Name";
//        Boolean isPaid = true;
//
//        List<TransferTicketDto> transactions = new ArrayList<>();
//        transactions.add(new TransferTicketDto());  // Add some dummy data
//
//        when(partnerDaoImpl.getTransactionsOfPartnerById(pageIndex, pageSize, partnerId, name, isPaid)).thenReturn(transactions);
//        when(partnerDaoImpl.countTransactionsOfPartnerById(pageIndex, pageSize, partnerId, name, isPaid)).thenReturn(20L);
//
//        // Act
//        PageDto result = partnerService.getPartnerTransactionById(pageIndex, pageSize, partnerId, name, isPaid);
//
//        // Assert
//        assertEquals(2, result.getTotalPages());  // 20 items, 10 per page -> 2 pages
//        assertEquals(20, result.getTotalElements());
//        assertEquals(1, result.getPageIndex());
//        assertEquals(transactions, result.getContent());
//    }
//    @Test
//    public void testGetPartnerDebtById() {
//        // Arrange
//        Integer pageIndex = 1;
//        Integer pageSize = 10;
//        Integer partnerId = 1;
//        Boolean isPaid = true;
//
//        List<DebtDetailDto> debts = new ArrayList<>();
//        debts.add(new DebtDetailDto());  // Add some dummy data
//
//        when(partnerDaoImpl.getDebtsOfPartnerById(pageIndex, pageSize, partnerId, isPaid)).thenReturn(debts);
//        when(partnerDaoImpl.countDebtsOfPartnerById(pageIndex, pageSize, partnerId, isPaid)).thenReturn(20L);
//
//        // Act
//        PageDto result = partnerService.getPartnerDebtById(pageIndex, pageSize, partnerId, isPaid);
//
//        // Assert
//        assertEquals(2, result.getTotalPages());  // 20 items, 10 per page -> 2 pages
//        assertEquals(20, result.getTotalElements());
//        assertEquals(1, result.getPageIndex());
//        assertEquals(debts, result.getContent());
//    }


}