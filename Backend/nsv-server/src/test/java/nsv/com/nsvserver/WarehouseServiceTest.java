package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.MapRepository;
import nsv.com.nsvserver.Repository.WarehouseDao;
import nsv.com.nsvserver.Repository.WarehouseRepository;
import nsv.com.nsvserver.Service.AddressService;
import nsv.com.nsvserver.Service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    AddressService addressService;

    @Mock
    MapRepository mapRepository;

    @Mock
    WarehouseDao warehouseDaoImpl;
    @InjectMocks
    WarehouseService warehouseService;
    @Test
    public void getWarehouseNameAndId() {
        // Arrange
        List<WarehouseNameAndIdDto> warehouses = Arrays.asList(
                new WarehouseNameAndIdDto(1, "Warehouse A"),
                new WarehouseNameAndIdDto(2, "Warehouse B")
        );
        when(warehouseRepository.getWarehouseNameAndId()).thenReturn((List) warehouses);

        // Act
        List<?> result = warehouseService.getWarehouseNameAndId();

        verify(warehouseRepository,times(1)).getWarehouseNameAndId();
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(warehouseRepository,times(1)).getWarehouseNameAndId();
    }

    @Test
    public void getWarehouseSlots() {
        // Arrange
        List<WarehouseSlotsDto> slots = Arrays.asList(
                new WarehouseSlotsDto(),
                new WarehouseSlotsDto()
        );
        when(warehouseRepository.getWarehouseSlot(1)).thenReturn(slots);

        // Act
        List<?> result = warehouseService.getWarehouseSlots(1);

        // Assert
        verify(warehouseRepository,times(1)).getWarehouseSlot(1);
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(warehouseRepository).getWarehouseSlot(1);
    }

    @Test
    public void testGetWarehouses() {

        Address address = new Address("street");
        address.setWard(new Ward(1,"ward"));
        address.getWard().setDistrict(new District(1,"District"));
        address.getWard().getDistrict().setProvince(new Province(1,"province"));
        List<WarehouseDto> warehouses = Arrays.asList(
                new WarehouseDto(1, "Warehouse A", "Type A", address,20.0,20.0,"Active"),
                new WarehouseDto(2, "Warehouse B", "Type A", address,20.0,20.0,"Active")
                );
        when(warehouseDaoImpl.getWarehouses(1, 10, "Warehouse", "Type A", "Active")).thenReturn(warehouses);
        when(warehouseDaoImpl.countTotalGetWarehouse(1, 10, "Warehouse", "Type A", "Active")).thenReturn(2L);

        // Act
        PageDto result = warehouseService.getWarehouses(1, 10, "Warehouse", "Type A", "Active");

        // Assert

        verify(warehouseDaoImpl,times(1)).getWarehouses(1, 10, "Warehouse", "Type A", "Active");
        verify(warehouseDaoImpl,times(1)).countTotalGetWarehouse(1, 10, "Warehouse", "Type A", "Active");


        assertNotNull(result);
        assertEquals(2L, result.getTotalElement());
        assertEquals(1.0, result.getTotalPage());
        assertEquals(1, result.getPage());
        assertEquals(2, result.getContent().size());
        verify(warehouseDaoImpl).getWarehouses(1, 10, "Warehouse", "Type A", "Active");
        verify(warehouseDaoImpl).countTotalGetWarehouse(1, 10, "Warehouse", "Type A", "Active");
    }

    @Test
    public void getStatisticOfProductInWarehouse_WarehouseNotFound_NotFoundExceptionThrown() {
        // Arrange
        Integer warehouseId = 1;

        when(warehouseRepository.existsById(warehouseId)).thenReturn(false);

        // Act & Assert
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            warehouseService.getStatisticOfProductInWarehouse(warehouseId);
        });

        assertEquals("Warehouse not found with id: 1", thrown.getMessage());
        verify(warehouseRepository,times(1)).existsById(warehouseId);
        verify(warehouseDaoImpl, never()).getStatisticsOfProductInWarehouse(warehouseId);
    }

    @Test
    public void getStatisticOfProductInWarehouse_WarehouseFound_GetStatistic() {
        // Arrange
        Integer warehouseId = 1;
        List<StatisticOfProductInWarehouseDto> statistics = Arrays.asList(
                new StatisticOfProductInWarehouseDto(),
                new StatisticOfProductInWarehouseDto()
        );

        when(warehouseRepository.existsById(warehouseId)).thenReturn(true);
        when(warehouseDaoImpl.getStatisticsOfProductInWarehouse(warehouseId)).thenReturn((List)statistics);

        // Act
        List<StatisticOfProductInWarehouseDto> result = warehouseService.getStatisticOfProductInWarehouse(warehouseId);

        // Assert
        verify(warehouseRepository,times(1)).existsById(warehouseId);
        verify(warehouseDaoImpl,times(1)).getStatisticsOfProductInWarehouse(warehouseId);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(statistics.size(), result.size());

    }

    @Test
    public void createWarehouse_MapNotFound_NotFoundExceptionThrown() {
        // Arrange
        AddressDto address = new AddressDto("address",1,1,1);
        Integer mapId=1;
        CreateWarehouseDto warehouseDto = new CreateWarehouseDto();
        warehouseDto.setMapId(mapId);
        warehouseDto.setAddress(address);
        when(mapRepository.findById(mapId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            warehouseService.createWarehouse(warehouseDto);
        });

        assertEquals("Map not found with id: 1", exception.getMessage());
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }

    @Test
    public void createWarehouse_MapAlreadyAssociated_ExistExceptionThrown() {
        // Arrange
        AddressDto address = new AddressDto("address",1,1,1);
        Integer mapId=1;
        CreateWarehouseDto warehouseDto = new CreateWarehouseDto();
        warehouseDto.setMapId(mapId);
        warehouseDto.setAddress(address);
        Map map = new Map();
        map.setWarehouse(new Warehouse());

        when(mapRepository.findById(anyInt())).thenReturn(Optional.of(map));

        // Act & Assert
        ExistsException thrown = assertThrows(ExistsException.class, () -> {
            warehouseService.createWarehouse(warehouseDto);
        });

        assertEquals("This map is already associate with an existing warehouse", thrown.getMessage());
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }
    @Test
    public void testCreateWarehouse_Success() {
        // Arrange
        // Arrange
        AddressDto address = new AddressDto("address",1,1,1);
        Integer mapId=1;
        CreateWarehouseDto warehouseDto = new CreateWarehouseDto();
        warehouseDto.setMapId(mapId);
        warehouseDto.setAddress(address);
        warehouseDto.setName("warehouse");
        Map map = new Map();
        Row row =new Row();
        row.setMap(map);
        row.setId(1);
        map.setRows(Arrays.asList(row));
        Slot slot = new Slot();
        slot.setRow(row);
        slot.setCapacity(20.0);
        slot.setContaining(20.0);
        slot.setStatus("CONTAINING");
        row.setSlots(Arrays.asList(slot));

        Address address1 = new Address("street");
        address1.setWard(new Ward(1,"ward"));
        address1.getWard().setDistrict(new District(1,"District"));
        address1.getWard().getDistrict().setProvince(new Province(1,"province"));

        when(addressService.createAddress(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(address1);
        when(mapRepository.findById(anyInt())).thenReturn(Optional.of(map));

        // Act
        CreateMapResponseDto response = warehouseService.createWarehouse(warehouseDto);

        // Assert
        assertNotNull(response);
        assertEquals("Create new warehouse successfully", response.getMessage());
        verify(warehouseRepository).save(argThat(warehouse -> {
            return warehouse.getCapacity().equals(20.0) && warehouse.getName().equals("warehouse");
        }));
    }

    @Test
    public void testGetWarehouseDetail_Success() {
        // Arrange
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Warehouse");
        warehouse.setCapacity(100.0);
        warehouse.setContaining(50.0);

        Map map = new Map();
        map.setName("Map A");

        Row row = new Row();
        row.setName("Row A");
        row.setYPosition(1);

        Slot slot = new Slot();
        slot.setName("Slot A");
        slot.setDescription("Description A");
        slot.setCapacity(100.0);
        slot.setXPosition(1);
        slot.setStatus("Available");
        slot.setContaining(50.0);
        slot.setId(1);

        row.setSlots(Collections.singletonList(slot));
        map.setRows(Collections.singletonList(row));
        warehouse.setMap(map);

        when(warehouseDaoImpl.getMapDetail(1)).thenReturn(warehouse);

        // Act
        WarehouseDetailDto result = warehouseService.getWarehouseDetail(1);

        // Assert
        verify(warehouseDaoImpl,times(1)).getMapDetail(1);
        assertNotNull(result);
        assertEquals("Warehouse", result.getName());
        assertEquals(1, result.getId());
        assertEquals(100.0, result.getCapacity());
        assertEquals(50.0, result.getContaining());
        assertEquals("Map A", result.getMap().getMapName());
        assertEquals(1, result.getMap().getRows().size());
        assertEquals("Row A", result.getMap().getRows().get(0).getName());
        assertEquals(1, result.getMap().getRows().get(0).getSlots().size());
        assertEquals("Slot A", result.getMap().getRows().get(0).getSlots().get(0).getName());


    }

    @Test
    public void getWarehouseDetail_WarehouseNotFound_NotFoundExceptionThrown() {
        // Arrange
        when(warehouseDaoImpl.getMapDetail(1)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(NotFoundException.class, () -> {
            warehouseService.getWarehouseDetail(1);
        });

        assertEquals("Warehouse not found with id: 1", exception.getMessage());
        verify(warehouseDaoImpl).getMapDetail(1);
    }



}
