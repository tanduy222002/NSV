package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    WarehouseRepository warehouseRepository;

    AddressService addressService;

    MapRepository mapRepository;

    WarehouseDao warehouseDaoImpl;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, AddressService addressService, MapRepository mapRepository, WarehouseDao warehouseDaoImpl) {
        this.warehouseRepository = warehouseRepository;
        this.addressService = addressService;
        this.mapRepository = mapRepository;
        this.warehouseDaoImpl = warehouseDaoImpl;
    }

    @Transactional
    public String createWarehouse(CreateWarehouseDto warehouseDto){
        Integer wardId=warehouseDto.getAddress().getWardId();
        Integer districtId=warehouseDto.getAddress().getDistrictId();
        Integer provinceId=warehouseDto.getAddress().getProvinceId();
        String streetAddress = warehouseDto.getAddress().getAddress();
        Integer mapId=warehouseDto.getMapId();
        Address address=addressService.createAddress(streetAddress, wardId,districtId, provinceId);
        Map map = mapRepository.findById(warehouseDto.getMapId()).orElseThrow(()->new NotFoundException("Map not found with id: "+mapId));
        double totalCapacity = map.getRows().stream()
                .flatMap(row -> row.getSlots().stream())
                .mapToDouble(slot -> slot.getCapacity())
                .sum();
        Warehouse warehouse=new Warehouse();
        warehouse.setName(warehouseDto.getName());
        warehouse.setType(warehouseDto.getType());
        warehouse.setAddress(address);
        warehouse.setMap(map);
        warehouse.setCapacity(totalCapacity);

        warehouseRepository.save(warehouse);
        return "New ware house created with id: "+warehouse.getId();
    }
    public List<StatisticOfProductInWarehouseDto> getStatisticOfProductInWarehouse(Integer warehouseId){
        if(!warehouseRepository.existsById(warehouseId)){
            throw new NotFoundException("Warehouse not found with id: " + warehouseId);
        }
        return (List<StatisticOfProductInWarehouseDto>) warehouseDaoImpl.getStatisticsOfProductInWarehouse(warehouseId);
    }

    public WarehouseDetailDto getWarehouseDetail(Integer id) {
        Warehouse warehouse= warehouseDaoImpl.getMapDetail(id);
        if (warehouse == null){
            throw new NotFoundException("Warehouse not found with id: " + id);
        }
        Map map= warehouse.getMap();
        WarehouseDetailDto warehouseDto = new WarehouseDetailDto();
        warehouseDto.setName(warehouse.getName());
        warehouseDto.setId(warehouse.getId());
        warehouseDto.setCapacity(warehouse.getCapacity());


        MapInWareHouseDto mapDto =new MapInWareHouseDto();
        mapDto.setMapName(map.getName());
        List<RowInMapDto> rowsDto=map.getRows().stream().map(row->{
            RowInMapDto rowDto =new RowInMapDto();
            rowDto.setName(row.getName());
            rowDto.setYPosition(row.getYPosition());
            List<SlotInRowDto> slotsDto=row.getSlots().parallelStream().map(slot->{
                SlotInRowDto slotDto =new SlotInRowDto();
                slotDto.setName(slot.getName());
                slotDto.setDescription(slot.getDescription());
                slotDto.setCapacity(slot.getCapacity());
                slotDto.setXPosition(slot.getXPosition());
                slotDto.setStatus(slot.getStatus());
                return slotDto;
            }).collect(Collectors.toList());
            rowDto.setSlots(slotsDto);
            return rowDto;
        }).collect(Collectors.toList());

        double containing=rowsDto.parallelStream().flatMap(rowDto -> rowDto.getSlots().stream()).mapToDouble(slot->slot.getCapacity()).sum();

        mapDto.setRows(rowsDto);
        warehouseDto.setMap(mapDto);
        warehouseDto.setContaining(containing);

        return warehouseDto;
    }

    public List<?> getWarehouseNameAndId() {
        return warehouseRepository.getWarehouseNameAndId();
    }
}
