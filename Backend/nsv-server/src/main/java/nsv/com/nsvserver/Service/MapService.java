package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.ExistsException;
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

    MapDao mapDaoImpl;


    @Autowired
    public MapService(ProvinceRepository provinceRepository, WardRepository wardRepository,
                      DistrictRepository districtRepository, AddressRepository addressRepository,
                      MapRepository mapRepository, MapDao mapDaoImpl) {
        this.provinceRepository = provinceRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.addressRepository = addressRepository;
        this.mapRepository = mapRepository;
        this.mapDaoImpl = mapDaoImpl;
    }

    @Transactional
    public String createMap(CreateMapDto mapDto){
        Map currentMap =new Map();
        if(mapRepository.existsByName(mapDto.getName())){
            throw new ExistsException("Map with name: "+mapDto.getName()+" already exists");
        }
        currentMap.setName(mapDto.getName());
        List<Row> rows=mapDto.getRowDtos().parallelStream().map(rowDto->{
            Row row =new Row();
            row.setName(rowDto.getName());
            row.setYPosition(rowDto.getYPosition());

            List<Slot> slots=rowDto.getSlotDtos().parallelStream().map(slotDto->{
                Slot slot =new Slot();
                slot.setName(slotDto.getName());
                slot.setDescription(slotDto.getDescription());
                slot.setCapacity(slotDto.getCapacity());
                slot.setXPosition(slotDto.getXPosition());
                slot.setRow(row);
                return slot;
            }).collect(Collectors.toList());
            row.setSlots(slots);
            row.setMap(currentMap);
            return row;
        }).collect(Collectors.toList());
        currentMap.setRows(rows);

        mapRepository.save(currentMap);
        return "New map was created with id: "+ currentMap.getId();
    }


    public CreateMapDto getMapById(Integer mapId) {
        Map map= mapRepository.findById(mapId).orElseThrow(()->new NotFoundException("Map not found with id:"));
        CreateMapDto mapDto =new CreateMapDto();
        mapDto.setName(map.getName());
        List<CreateRowDto> rowsDto=map.getRows().stream().map(row->{
            CreateRowDto rowDto =new CreateRowDto();
            rowDto.setName(row.getName());
            rowDto.setYPosition(row.getYPosition());

            List<CreateSlotDto> slotsDto=row.getSlots().stream().map(slot->{
                CreateSlotDto slotDto =new CreateSlotDto();
                slotDto.setName(slot.getName());
                slotDto.setDescription(slot.getDescription());
                slotDto.setCapacity(slot.getCapacity());
                slotDto.setXPosition(slot.getXPosition());
                return slotDto;
            }).collect(Collectors.toList());
            rowDto.setSlotDtos(slotsDto);
            return rowDto;
        }).collect(Collectors.toList());
        mapDto.setRowDtos(rowsDto);

        return mapDto;
    }

    public PageDto searchMapByFilterAndPagination(Integer pageIndex, Integer pageSize, String name){
        List<MapWithIdAndNameDto> maps= mapDaoImpl.getMapIdAndNameWithFilterAndPagination(pageIndex,pageSize,name);
        long count= mapDaoImpl.countTotalHasName(name);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,maps);
    }

    public List<?> searchMapByFilterAndPagination(){
        List<MapWithIdAndNameDto> maps= mapDaoImpl.getMapIdAndNameWithFilterAndPagination();
        return maps;
    }


    public PageDto getSlotInMapByFilterAndPagination(Integer pageIndex, Integer pageSize, String name, Integer mapId) {
        List<SlotWithIdAndNameDto> slots= mapDaoImpl.getSlotIdAndNameInMapWithFilterAndPagination(pageIndex,pageSize,name,mapId);
        long count= mapDaoImpl.countTotalSlotInMapWithFilter(name,mapId);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,slots);
    }
}
