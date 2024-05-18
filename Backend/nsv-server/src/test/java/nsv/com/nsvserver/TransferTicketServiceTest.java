package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Entity.Map;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.TicketStatusMismatchException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.EmployeeDetailService;
import nsv.com.nsvserver.Service.TransferTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferTicketServiceTest {
    @Mock
    QualityRepository qualityRepository;
    @Mock
    SlotRepository slotRepository;

    @Mock
    TransferTicketRepository transferTicketRepository;

    @Mock
    PartnerRepository partnerRepository;

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    TicketDao ticketDaoImpl;

    @Mock
    BinSlotRepository binSlotRepository;

    @Mock
    BinDao binDaoImpl;

    @Mock
    private MockedStatic<EmployeeDetailService> mockedStatic;

    @InjectMocks
    TransferTicketService transferTicketService;



    @Test
    public void testCreateImportTransferTicket_Success() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1);

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        Quality quality = new Quality();
        quality.setId(1);
        quality.setName("quality");

        when(qualityRepository.findById(anyInt())).thenReturn(Optional.of(quality));

        Slot slot = new Slot();
        slot.setId(1);
        slot.setName("Slot 1");
        slot.setCapacity(100.0);
        slot.setContaining(0.0);

        when(slotRepository.findById(anyInt())).thenReturn(Optional.of(slot));

        Partner partner = new Partner();
        partner.setId(1);

        when(partnerRepository.findById(anyInt())).thenReturn(Optional.of(partner));

        CreateTransferTicketDto importTicketDto = new CreateTransferTicketDto();
        importTicketDto.setName("Import Ticket");
        importTicketDto.setDescription("Description");
        importTicketDto.setTransporter("Transporter");
        importTicketDto.setImportDate(new Date());
        importTicketDto.setProviderId(1);


        CreateBinDto binDto = new CreateBinDto();
        binDto.setQualityId(1);
        binDto.setWeight(50.0);
        binDto.setNote("Note");
        binDto.setPrice(100.0);
        binDto.setPackageType("Type A");

        BinWithSlotDto binWithSlotDto = new BinWithSlotDto();
        binWithSlotDto.setSlotId(1);
        binWithSlotDto.setWeight(50.0);
        binWithSlotDto.setArea(10.0);

        binDto.setBinWithSlot(Arrays.asList(binWithSlotDto));
        importTicketDto.setBinDto(Arrays.asList(binDto));

        CreateDebtDto createDebtDto = new CreateDebtDto();
        createDebtDto.setValue(1000.0);
        createDebtDto.setDescription("Debt Description");
        createDebtDto.setDueDate(new Date());
        importTicketDto.setDebtDto(createDebtDto);
        mockedStatic.when(EmployeeDetailService::getCurrentUserDetails).thenReturn(employee);


            // Act
        String result = transferTicketService.createImportTransferTicket(importTicketDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("New Import Transfer Ticket created with id:"));
        verify(transferTicketRepository, times(1)).save(any(TransferTicket.class));
    }

    @Test
    public void testApproveTicketStatus_Success() {
        // Arrange
        Integer ticketId = 1;
        TransferTicket transferTicket = new TransferTicket();
        transferTicket.setId(ticketId);
        transferTicket.setStatus("PENDING");

        Bin bin = new Bin();
        bin.setStatus("PENDING");
        BinSlot binSlot = new BinSlot();
        Slot slot = new Slot();
        slot.setStatus("AVAILABLE");
        slot.setCapacity(100.0);
        slot.setContaining(50.0);
        binSlot.setSlot(slot);
        binSlot.setArea(20.0);
        bin.setBinSlot(Arrays.asList(binSlot));
        transferTicket.setBins(Arrays.asList(bin));

        Map map =new Map();
        Row row = new Row();
        row.setMap(map);
        slot.setRow(row);
        Warehouse warehouse = new Warehouse();
        warehouse.setContaining(50.0);
        slot.getRow().getMap().setWarehouse(warehouse);

        when(ticketDaoImpl.fetchWithBinAndSlot(ticketId)).thenReturn(transferTicket);

        Employee mockEmployee = new Employee();
        mockEmployee.setId(1);


            mockedStatic.when(EmployeeDetailService::getCurrentUserDetails).thenReturn(mockEmployee);

            when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(mockEmployee));

            // Act
            transferTicketService.approveTicketStatus(ticketId);

            // Assert
            assertEquals("APPROVED", transferTicket.getStatus());
            assertEquals("APPROVED", bin.getStatus());
            assertEquals("CONTAINING", slot.getStatus());
            assertEquals(70.0, slot.getContaining());

            verify(transferTicketRepository, times(1)).save(transferTicket);

    }

    @Test
    public void testApproveTicketStatus_TicketAlreadyApproved() {
        // Arrange
        Integer ticketId = 1;
        TransferTicket transferTicket = new TransferTicket();
        transferTicket.setId(ticketId);
        transferTicket.setStatus("APPROVED");
        Bin bin = new Bin();
        bin.setStatus("PENDING");
        BinSlot binSlot = new BinSlot();
        Slot slot = new Slot();
        slot.setStatus("AVAILABLE");
        slot.setCapacity(100.0);
        slot.setContaining(50.0);
        binSlot.setSlot(slot);
        binSlot.setArea(20.0);
        bin.setBinSlot(Arrays.asList(binSlot));
        transferTicket.setBins(Arrays.asList(bin));
        Map map =new Map();
        Row row = new Row();
        row.setMap(map);
        slot.setRow(row);
        Warehouse warehouse = new Warehouse();
        warehouse.setContaining(50.0);
        slot.getRow().getMap().setWarehouse(warehouse);

        when(ticketDaoImpl.fetchWithBinAndSlot(ticketId)).thenReturn(transferTicket);

        // Act & Assert
        assertThrows(TicketStatusMismatchException.class, () -> {
            transferTicketService.approveTicketStatus(ticketId);
        });

        verify(transferTicketRepository, never()).save(any(TransferTicket.class));
    }

    @Test
    public void testApproveTicketStatus_EmployeeNotFound() {
        // Arrange
        Integer ticketId = 1;
        TransferTicket transferTicket = new TransferTicket();
        transferTicket.setId(ticketId);
        transferTicket.setStatus("PENDING");
        Bin bin = new Bin();
        bin.setStatus("PENDING");
        BinSlot binSlot = new BinSlot();
        Slot slot = new Slot();
        slot.setStatus("AVAILABLE");
        slot.setCapacity(100.0);
        slot.setContaining(50.0);
        binSlot.setSlot(slot);
        binSlot.setArea(20.0);
        bin.setBinSlot(Arrays.asList(binSlot));
        transferTicket.setBins(Arrays.asList(bin));
        Map map =new Map();
        Row row = new Row();
        row.setMap(map);
        slot.setRow(row);
        Warehouse warehouse = new Warehouse();
        warehouse.setContaining(50.0);
        slot.getRow().getMap().setWarehouse(warehouse);

        when(ticketDaoImpl.fetchWithBinAndSlot(ticketId)).thenReturn(transferTicket);

        Employee mockEmployee = new Employee();
        mockEmployee.setId(1);


        mockedStatic.when(EmployeeDetailService::getCurrentUserDetails).thenReturn(mockEmployee);

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

            // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
                transferTicketService.approveTicketStatus(ticketId);
            });

        assertEquals("No employee found", thrown.getMessage());

        verify(transferTicketRepository, never()).save(any(TransferTicket.class));

    }

    @Test
    public void getTransferTicketWithFilterAndPagination_Success() {
        // Arrange
        Integer pageIndex = 0;
        Integer pageSize = 10;
        String name = "test";
        String type = "IMPORT";
        String status = "PENDING";

        List<TransferTicket> mockTickets = new ArrayList<>();
        TransferTicket mockTicket = new TransferTicket();
        mockTicket.setId(1);
        mockTicket.setName("Ticket");
        mockTicket.setCreateDate(new Date());
        mockTicket.setDescription("Description");
        mockTicket.setStatus("PENDING");

        Bin bin = new Bin();
        bin.setWeight(100.0);

        Quality quality = new Quality();
        Type productType = new Type();
        Product product = new Product();
        product.setName("Product");
        productType.setProduct(product);
        quality.setType(productType);

        bin.setQuality(quality);
        mockTicket.setBins(Collections.singletonList(bin));

        mockTickets.add(mockTicket);

        when(ticketDaoImpl.getTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status)).thenReturn(mockTickets);
        when(ticketDaoImpl.countTotalTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status)).thenReturn(1L);

        // Act
        PageDto result = transferTicketService.getTransferTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getTotalElement());
        assertEquals(1, result.getContent().size());

        TransferTicketDto dto = (TransferTicketDto) result.getContent().get(0);
        assertEquals(1, dto.getId());
        assertEquals("Ticket", dto.getName());
        assertEquals("Description", dto.getDescription());
        assertEquals("PENDING", dto.getStatus());
        assertEquals(100.0, dto.getWeight());
        assertEquals(1, dto.getNumberOfProducts());
    }

    @Test
    public void createExportTicket_Success() {
        // Arrange
        CreateExportTransferTicketDto exportTicketDto = new CreateExportTransferTicketDto();
        exportTicketDto.setName("Export Ticket");
        exportTicketDto.setDescription("Description");
        exportTicketDto.setTransporter("Transporter");
        exportTicketDto.setExportDate(new Date());
        exportTicketDto.setCustomerId(1);

        CreateDebtDto createDebtDto = new CreateDebtDto();
        createDebtDto.setValue(100.0);
        createDebtDto.setDescription("Debt Description");
        createDebtDto.setDueDate(new Date());
        exportTicketDto.setDebtDto(createDebtDto);

        CreateExportBinDto binDto = new CreateExportBinDto();
        binDto.setQualityId(1);
        binDto.setNote("Note");
        binDto.setPrice(100.0);
        binDto.setPackageType("Type");
        binDto.setWeight(20.0);
        ImportBinInSlot importBinInSlot = new ImportBinInSlot();
        importBinInSlot.setSlotId(1);
        importBinInSlot.setTakenWeight(20.0);
        importBinInSlot.setTakenArea(20.0);
        BinDto binDto2=new BinDto();
        binDto2.setId(1);
        importBinInSlot.setBin(binDto2);
        binDto.setImportBinWithSlot(Collections.singletonList(importBinInSlot));

        List<CreateExportBinDto> exportBinsDto = Collections.singletonList(binDto);
        exportTicketDto.setExportBinDto(exportBinsDto);




        Employee mockEmployee = new Employee();
        mockEmployee.setId(1);
        when(EmployeeDetailService.getCurrentUserDetails()).thenReturn(mockEmployee);
        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));

        Quality mockQuality = new Quality();
        when(qualityRepository.findById(1)).thenReturn(Optional.of(mockQuality));

        BinSlot mockBinSlot = new BinSlot();
        mockBinSlot.setArea(20.0);
        mockBinSlot.setWeight(20.0);
        Bin mockBin = new Bin();
        mockBinSlot.setBin(mockBin);
        Slot mockSlot = new Slot();
        mockBinSlot.setSlot(mockSlot);
        when(binDaoImpl.GetBinSlotBySlotIdAndBinId(anyInt(), anyInt())).thenReturn(mockBinSlot);

        Partner mockPartner = new Partner();
        when(partnerRepository.findById(1)).thenReturn(Optional.of(mockPartner));

        // Act
        String result = transferTicketService.createExportTicket(exportTicketDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("New Export Transfer Ticket created with id:"));

        verify(transferTicketRepository, times(1)).save(any(TransferTicket.class));
    }

}
