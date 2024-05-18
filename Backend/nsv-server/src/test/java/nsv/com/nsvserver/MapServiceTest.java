package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Map;
import nsv.com.nsvserver.Entity.Row;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Exception.ExistsException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import nsv.com.nsvserver.Service.MapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MapServiceTest {
    @Mock
    ProvinceRepository provinceRepository;
    @Mock
    WardRepository wardRepository;

    @Mock
    DistrictRepository districtRepository;

    @Mock
    AddressRepository addressRepository;

    @Mock
    MapRepository mapRepository;

    @Mock
    MapDao mapDaoImpl;

    @InjectMocks
    MapService mapService;

    @Test
    public void searchMapByFilterAndPagination() {
        // Arrange
        int pageIndex = 1;
        int pageSize = 10;
        String name = "testMap";
        List<MapWithIdAndNameDto> maps = Arrays.asList(
                new MapWithIdAndNameDto(1, "Map1"),
                new MapWithIdAndNameDto(2, "Map2")
        );
        when(mapDaoImpl.getMapIdAndNameWithFilterAndPagination(pageIndex, pageSize, name)).thenReturn(maps);
        when(mapDaoImpl.countTotalHasName(name)).thenReturn(2L);

        // Act
        PageDto result = mapService.searchMapByFilterAndPagination(pageIndex, pageSize, name);

        // Assert
        verify(mapDaoImpl,times(1)).getMapIdAndNameWithFilterAndPagination(pageIndex, pageSize, name);
        verify(mapDaoImpl,times(1)).countTotalHasName(name);
        assertEquals(2, result.getContent().size());
        assertEquals(1.0, result.getTotalPage());
        assertEquals(2L, result.getTotalElement());
    }

    @Test
    public void getSlotInMapByFilterAndPagination() {
        // Arrange
        int pageIndex = 1;
        int pageSize = 10;
        String name = "testSlot";
        int mapId = 1;
        List<SlotWithIdAndNameDto> slots = Arrays.asList(
                new SlotWithIdAndNameDto(1, "Slot1"),
                new SlotWithIdAndNameDto(2, "Slot2")
        );
        when(mapDaoImpl.getSlotIdAndNameInMapWithFilterAndPagination(pageIndex, pageSize, name, mapId)).thenReturn(slots);
        when(mapDaoImpl.countTotalSlotInMapWithFilter(name, mapId)).thenReturn(2L);

        // Act
        PageDto result = mapService.getSlotInMapByFilterAndPagination(pageIndex, pageSize, name, mapId);

        // Assert
        verify(mapDaoImpl).getSlotIdAndNameInMapWithFilterAndPagination(pageIndex, pageSize, name, mapId);
        verify(mapDaoImpl).countTotalSlotInMapWithFilter(name, mapId);
        assertEquals(2, result.getContent().size());
        assertEquals(1.0, result.getTotalPage());
        assertEquals(2L, result.getTotalElement());
    }

    @Test
    public void searchMapByFilterAndPagination_NoParams() {
        // Arrange
        List<MapWithIdAndNameDto> maps = Arrays.asList(
                new MapWithIdAndNameDto(1, "Map1"),
                new MapWithIdAndNameDto(2, "Map2")
        );
        when(mapDaoImpl.getMapIdAndNameWithFilterAndPagination()).thenReturn(maps);

        // Act
        List<MapWithIdAndNameDto> result = (List<MapWithIdAndNameDto>) mapService.searchMapByFilterAndPagination();

        // Assert
        verify(mapDaoImpl).getMapIdAndNameWithFilterAndPagination();
        assertEquals(2, result.size());
        assertEquals(maps, result);
    }
    @Test
    public void testCreateMap_Success() {
        // Arrange
        CreateMapDto mapDto = new CreateMapDto();
        mapDto.setName("New Map");
        List<CreateRowDto> rowDtos = new ArrayList<>();
        CreateRowDto rowDto = new CreateRowDto();
        rowDto.setName("Row 1");
        rowDto.setYPosition(1);

        List<CreateSlotDto> slotDtos = new ArrayList<>();
        CreateSlotDto slotDto = new CreateSlotDto();
        slotDto.setName("Slot 1");
        slotDto.setDescription("First Slot");
        slotDto.setCapacity(100.0);
        slotDto.setXPosition(1);
        slotDtos.add(slotDto);

        rowDto.setSlotDtos(slotDtos);
        rowDtos.add(rowDto);
        mapDto.setRowDtos(rowDtos);

        when(mapRepository.existsByName("New Map")).thenReturn(false);

        // Act
        String result = mapService.createMap(mapDto);

        // Assert
        verify(mapRepository).save(argThat(map->{
            return map.getName().equals("New Map") && map.getRows().get(0).getName().equals("Row 1");
        }));
        assertTrue(result.contains("New map was created with id:"));
    }

    @Test
    public void testCreateMap_MapExist_ExistExceptionThrown() {
        // Arrange
        CreateMapDto mapDto = new CreateMapDto();
        mapDto.setName("Existing Map");

        when(mapRepository.existsByName("Existing Map")).thenReturn(true);

        // Act & Assert
        ExistsException exception = assertThrows(ExistsException.class, () -> {
            mapService.createMap(mapDto);
        });

        assertEquals("Map with name: Existing Map already exists", exception.getMessage());
    }

    @Test
    public void getMapById_MapNotFound_NotFoundExceptionThrown() {
        // Arrange
        Integer mapId =1;
        Map map = new Map();
        map.setId(mapId);
        map.setName("New Map");
        List<Row> rows = new ArrayList<>();
        Row row = new Row();
        row.setName("Row 1");
        row.setYPosition(1);

        List<Slot> slots = new ArrayList<>();
        Slot slot = new Slot();
        slot.setName("Slot 1");
        slot.setDescription("First Slot");
        slot.setCapacity(100.0);
        slot.setXPosition(1);
        slots.add(slot);

        row.setSlots(slots);
        rows.add(row);
        map.setRows(rows);

        when(mapRepository.findById(mapId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,()->{
            mapService.getMapById(mapId);
        });

    }

    @Test
    public void getMapById_MapFound_GetSingleResult() {
        // Arrange
        Integer mapId =1;
        Map map = new Map();
        map.setId(mapId);
        map.setName("New Map");
        List<Row> rows = new ArrayList<>();
        Row row = new Row();
        row.setName("Row 1");
        row.setYPosition(1);

        List<Slot> slots = new ArrayList<>();
        Slot slot = new Slot();
        slot.setName("Slot 1");
        slot.setDescription("First Slot");
        slot.setCapacity(100.0);
        slot.setXPosition(1);
        slots.add(slot);

        row.setSlots(slots);
        rows.add(row);
        map.setRows(rows);

        when(mapRepository.findById(mapId)).thenReturn(Optional.of(map));

        CreateMapDto dto = mapService.getMapById(mapId);

        verify(mapRepository,times(1)).findById(mapId);


        assertEquals(map.getName(), dto.getName());
        assertEquals(map.getRows().size(), dto.getRowDtos().size());

    }
}
