package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Dto.SlotStatisticInWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.SlotDao;
import nsv.com.nsvserver.Service.SlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SlotServiceTest {
    @Mock
    SlotDao slotDaoImpl;
    @InjectMocks
    SlotService slotService;

    @Test
    public void testGetSlotDetail() {
        Bin bin =  new Bin(1,2.2,2.3,"No","Kg","APPROVED",500,50000.0,new Date(),new Date(),"","");
        TransferTicket ticket = new TransferTicket();
        ticket.setName("Test");
        bin.setTransferTicket(ticket);
        BinSlot binSlot= new BinSlot();
        binSlot.setWeight(20.0);
        binSlot.setArea(20.0);
        bin.setBinSlot(Arrays.asList(binSlot));
        Quality quality = new Quality();
        quality.setName("Quality");
        Type type = new Type();
        type.setName("Type");
        Product product = new Product();
        product.setName("Product");
        quality.setType(type);
        type.setProduct(product);
        bin.setQuality(quality);


        // Mocking slotDaoImpl behavior
        List<Bin> bins = Arrays.asList(
                bin
        );
        when(slotDaoImpl.getSlotDetail(anyInt(), anyInt(), anyInt())).thenReturn(bins);
        when(slotDaoImpl.countGetSlotDetail(anyInt(),anyInt(),anyInt())).thenReturn(3l);

        // Testing SlotService method
        PageDto pageDto = slotService.getSlotDetail(1, 10, 1);

        assertNotNull(pageDto);
    }

    @Test
    public void testGetQualityStatisticInSlot()  {
        // Mocking SlotDao behavior
        Slot slot = new Slot();
        when(slotDaoImpl.getSlotContainingAndCapacityById(anyInt())).thenReturn(slot);

        List<StatisticOfProductInWarehouseDto> productStatistics = Arrays.asList(
                new StatisticOfProductInWarehouseDto()
        );
        when(slotDaoImpl.getStatisticsOfProductInSlot(anyInt())).thenReturn(productStatistics);
        try{
            SlotStatisticInWarehouseDto result = slotService.getQualityStatisticInSlot(1);
            assertNotNull(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
