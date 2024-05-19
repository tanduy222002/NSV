package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Annotation.TraceTime;
import nsv.com.nsvserver.Dto.BinDto;
import nsv.com.nsvserver.Dto.ImportBinInSlot;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.BinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinService {


    private BinDao binDaoImpl;

    @Autowired
    public BinService(BinDao binDaoImpl) {
        this.binDaoImpl = binDaoImpl;
    }


    @TraceTime
    public PageDto findBinWithFilterAndPagination(Integer pageIndex, Integer pageSize, Integer warehouseId,
                                                  Integer productId, Integer typeId, Integer qualityId,
                                                  Integer minWeight, Integer maxWeight) {
        List<Bin> bins = binDaoImpl.findWithFilterAndPagination(pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight);


        List<ImportBinInSlot> dto = new ArrayList<ImportBinInSlot>();
        bins.parallelStream().forEach(bin -> {
            Quality quality = bin.getQuality();
            Type type=quality.getType();
            Product product = type.getProduct();
            String qualityWithType= type.getName()+" "+quality.getName();
            BinDto binDto = new BinDto(
                    bin.getId(), product.getName(), product.getImage(), quality.getId(), qualityWithType,
                    bin.getWeight(),bin.getPackageType(),bin.getPrice());

            List<ImportBinInSlot> importBinInSlots= bin.getBinSlot().parallelStream()
                            .filter(binSlot-> warehouseId==null||binSlot.getSlot().getRow().getMap().getWarehouse().getId().equals(warehouseId))
                            .map(binSlot->{
                Slot currSlot=binSlot.getSlot();
                ImportBinInSlot binInSlotDto = new ImportBinInSlot();
                Warehouse warehouse= currSlot.getRow().getMap().getWarehouse();
                String warehouseName =warehouse.getName();
                binInSlotDto.setBin(binDto);
                binInSlotDto.setSlotId(currSlot.getId());
                binInSlotDto.setSlotName(currSlot.getName());
                binInSlotDto.setWarehouseName(warehouseName);
                binInSlotDto.setLocation(currSlot.getName()+"/"+warehouseName);
                binInSlotDto.setInSlotWeight(binSlot.getWeight());
                return binInSlotDto;
            }).collect(Collectors.toList());
            dto.addAll(importBinInSlots);
        });

        long count= binDaoImpl.countTotalBinWithFilterAndPagination(pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight);

        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,dto);
    }
}
