package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.BinInSlotDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Dto.SlotStatisticInWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.SlotDao;
import nsv.com.nsvserver.Util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class SlotService {
    SlotDao slotDaoImpl;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);


    @Autowired
    public SlotService(SlotDao slotDaoImpl) {
        this.slotDaoImpl = slotDaoImpl;
    }

    public PageDto getSlotDetail(Integer slotId, Integer pageSize, Integer pageIndex) {
        List<Bin> bins = slotDaoImpl.getSlotDetail(slotId, pageIndex, pageSize);

        List<BinInSlotDto> binInSlotDtos=bins.stream().map(bin->{
            TransferTicket transferTicket=bin.getTransferTicket();
            Quality quality = bin.getQuality();
            Type type=quality.getType();
            Product product = type.getProduct();
            BinSlot binSlot = bin.getBinSlot().get(0);
            BinInSlotDto dto = new BinInSlotDto();
            dto.setProductName(product.getName());
            dto.setPackaged(bin.getPackageType());
            dto.setImportDate(bin.getImportDate());
            dto.setTicketName(transferTicket.getName());
            dto.setWeight(NumberUtil.removeTrailingZero(bin.getWeight()));
            dto.setInSlotWeight(NumberUtil.removeTrailingZero(binSlot.getWeight()));
            dto.setId(bin.getId());
            dto.setProductType(type.getName()+" "+quality.getName());
            return dto;
        }).collect(Collectors.toList());

        long count=slotDaoImpl.countGetSlotDetail(slotId, pageSize, pageIndex);

        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,binInSlotDtos);
    }
    public SlotStatisticInWarehouseDto getQualityStatisticInSlot(Integer slotId) throws InterruptedException,ExecutionException {

        CompletableFuture<Slot> slotFuture = CompletableFuture.supplyAsync(() -> slotDaoImpl.getSlotContainingAndCapacityById(slotId), executorService);
        CompletableFuture<List<StatisticOfProductInWarehouseDto>> productStatisticFuture = CompletableFuture.supplyAsync(() -> slotDaoImpl.getStatisticsOfProductInSlot(slotId), executorService);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(slotFuture, productStatisticFuture);

        // Wait for both tasks to complete

        combinedFuture.get();

        SlotStatisticInWarehouseDto dto =new SlotStatisticInWarehouseDto(slotFuture.get(),productStatisticFuture.get());

//        long interTime=System.currentTimeMillis();
//        Slot slot = slotDaoImpl.getSlotContainingAndCapacityById(slotId);
//        long outterTime = System.currentTimeMillis();
//        System.out.println(System.currentTimeMillis()-interTime);
//        List<StatisticOfProductInWarehouseDto> productStatistic = slotDaoImpl.getStatisticsOfProductInSlot(slotId);
//        System.out.println(System.currentTimeMillis()-outterTime);
//        SlotStatisticInWarehouseDto dto = new SlotStatisticInWarehouseDto(slot,productStatistic);
        return dto;


    }
}
