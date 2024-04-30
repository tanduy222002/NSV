package nsv.com.nsvserver.Service;

import nsv.com.nsvserver.Dto.BinInSlotDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.SlotDao;
import nsv.com.nsvserver.Util.ConvertUtil;
import nsv.com.nsvserver.Util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {
    SlotDao slotDaoImpl;

    @Autowired
    public SlotService(SlotDao slotDaoImpl) {
        this.slotDaoImpl = slotDaoImpl;
    }

    public PageDto getSlotDetail(Integer slotId, Integer pageSize, Integer pageIndex) {
        List<Bin> bins = slotDaoImpl.getSlotDetail(slotId, pageIndex, pageSize);

        List<BinInSlotDto> binInSlotDtos=bins.parallelStream().map(bin->{
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
}
