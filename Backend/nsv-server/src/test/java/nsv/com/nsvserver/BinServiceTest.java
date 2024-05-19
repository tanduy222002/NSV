package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.ImportBinInSlot;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Repository.BinDao;
import nsv.com.nsvserver.Service.BinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BinServiceTest {
    @Mock
    private BinDao binDaoImpl;

    @InjectMocks
    BinService binService;


    @Test
    void getBinsWithDetailsAndPagination() {
        Integer pageIndex =1,pageSize=5,warehouseId=1,productId=1,typeId=1,qualityId=1,minWeight=null,maxWeight=null;

        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setImage("image1.jpg");

        Type type = new Type();
        type.setId(typeId);
        type.setName("Type 1");
        type.setProduct(product);

        Quality quality = new Quality();
        quality.setId(qualityId);
        quality.setName("Quality 1");
        quality.setType(type);

        Bin bin = new Bin();
        bin.setId(1);
        bin.setWeight(10.0);
        bin.setPackageType("Package 1");
        bin.setPrice(100.0);
        bin.setQuality(quality);

        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Warehouse 1");

        Map map = new Map();
        map.setWarehouse(warehouse);

        Row row = new Row();
        row.setMap(map);

        Slot slot = new Slot();
        slot.setId(1);
        slot.setName("Slot 1");
        slot.setRow(row);

        BinSlot binSlot = new BinSlot();
        binSlot.setSlot(slot);
        binSlot.setWeight(10.0);

        bin.setBinSlot(Arrays.asList(binSlot));
        List<Bin> bins = Arrays.asList(bin);
        when(binDaoImpl.findWithFilterAndPagination(pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight)).thenReturn(bins);
        when(binDaoImpl.countTotalBinWithFilterAndPagination(anyInt(), anyInt(), any(), any(), any(), any(), any(), any())).thenReturn(1L);

        PageDto result = binService.findBinWithFilterAndPagination(pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight);

        assertNotNull(result);
        assertEquals(1.0, result.getTotalPage());
        assertEquals(1L, result.getTotalElement());
        assertEquals(1, result.getPage());
        assertEquals(1, result.getContent().size());

        ImportBinInSlot dto = (ImportBinInSlot) result.getContent().get(0);
        assertEquals("Slot 1/Warehouse 1", dto.getLocation());
        assertEquals(10.0, dto.getInSlotWeight());
    }

    @Test
    void getBinsWithDetailsAndPagination_WarehouseIdIsNull() {
        Integer pageIndex =1,pageSize=5,warehouseId=null,productId=1,typeId=1,qualityId=1,minWeight=null,maxWeight=null;

        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setImage("image1.jpg");

        Type type = new Type();
        type.setId(typeId);
        type.setName("Type 1");
        type.setProduct(product);

        Quality quality = new Quality();
        quality.setId(qualityId);
        quality.setName("Quality 1");
        quality.setType(type);

        Bin bin = new Bin();
        bin.setId(1);
        bin.setWeight(10.0);
        bin.setPackageType("Package 1");
        bin.setPrice(100.0);
        bin.setQuality(quality);

        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Warehouse 1");

        Map map = new Map();
        map.setWarehouse(warehouse);

        Row row = new Row();
        row.setMap(map);

        Slot slot = new Slot();
        slot.setId(1);
        slot.setName("Slot 1");
        slot.setRow(row);

        BinSlot binSlot = new BinSlot();
        binSlot.setSlot(slot);
        binSlot.setWeight(10.0);

        bin.setBinSlot(Arrays.asList(binSlot));
        List<Bin> bins = Arrays.asList(bin);
        when(binDaoImpl.findWithFilterAndPagination(pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight)).thenReturn(bins);
        when(binDaoImpl.countTotalBinWithFilterAndPagination(anyInt(), anyInt(), any(), any(), any(), any(), any(), any())).thenReturn(1L);

        PageDto result = binService.findBinWithFilterAndPagination(pageIndex,pageSize,warehouseId,productId,typeId,qualityId,minWeight,maxWeight);

        assertNotNull(result);
        assertEquals(1.0, result.getTotalPage());
        assertEquals(1L, result.getTotalElement());
        assertEquals(1, result.getPage());
        assertEquals(1, result.getContent().size());

        ImportBinInSlot dto = (ImportBinInSlot) result.getContent().get(0);
        assertEquals("Slot 1/Warehouse 1", dto.getLocation());
        assertEquals(10.0, dto.getInSlotWeight());
    }





}
