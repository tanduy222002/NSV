package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.CreateMapDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapService {
    ProvinceRepository provinceRepository;
    WardRepository wardRepository;

    DistrictRepository districtRepository;

    AddressRepository addressRepository;

    MapRepository mapRepository;


    @Autowired
    public MapService(ProvinceRepository provinceRepository, WardRepository wardRepository, DistrictRepository districtRepository, AddressRepository addressRepository, MapRepository mapRepository) {
        this.provinceRepository = provinceRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.addressRepository = addressRepository;
        this.mapRepository = mapRepository;
    }

    @Transactional
    public String createMap(CreateMapDto mapDto){
        Map currentMap =new Map();
        currentMap.setName(mapDto.getName());
        List<Row> rows=mapDto.getRowDtos().parallelStream().map(rowDto->{
            Row row =new Row();
            row.setName(rowDto.getName());
            System.out.println(row.getName());
            row.setYPosition(rowDto.getYPosition());

            List<Slot> slots=rowDto.getSlotDtos().parallelStream().map(slotDto->{
                Slot slot =new Slot();
                slot.setName(slotDto.getName());
                slot.setDescription(slotDto.getDescription());
                slot.setXPosition(slotDto.getXPosition());
                slot.setRow(row);
                return slot;
            }).collect(Collectors.toList());
            row.setSlots(slots);
            row.setMap(currentMap);
            return row;
        }).collect(Collectors.toList());
        currentMap.setRow(rows);

        mapRepository.save(currentMap);
        return "New map was created with id: "+ currentMap.getId();
    }


    public Map getMapById(Integer mapId) {
        return mapRepository.findById(mapId).orElseThrow(()->new NotFoundException("Map not found with id:"));
    }
}
