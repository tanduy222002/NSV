package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.CreateMapDto;
import nsv.com.nsvserver.Dto.CreateWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        Warehouse warehouse=new Warehouse();
        warehouse.setName(warehouseDto.getName());
        warehouse.setType(warehouseDto.getType());
        warehouse.setAddress(address);
        warehouse.setMap(map);

        warehouseRepository.save(warehouse);
        return "New ware house created with id: "+warehouse.getId();
    }
    public List<StatisticOfProductInWarehouseDto> getStatisticOfProductInWarehouse(Integer warehouseId){
        if(!warehouseRepository.existsById(warehouseId)){
            throw new NotFoundException("Warehouse not found with id: " + warehouseId);
        }
        return (List<StatisticOfProductInWarehouseDto>) warehouseDaoImpl.getStatisticsOfProductInWarehouse(warehouseId);
    }

}
