package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeWithQualityDetailInSlotDto {
    private String type;
    private String image;
    private String quality;
    private Double weight;
    @JsonProperty("slot_id")
    private Integer slotId;
    @JsonProperty("slot_name")
    private String slotName;
    @JsonProperty("warehouse_name")
    private String warehouseName;
    @JsonProperty("warehouse_id")
    private Integer warehouseId;

    private String location;
    @JsonProperty("type_with_quality")
    private String typeWithQuality;

    @JsonProperty("containing_area")
    private Double area;
    @JsonProperty("slot_capacity")
    private Double slotCapacity;

    public ProductTypeWithQualityDetailInSlotDto(String type, String image, String quality, Double weight, Double area, Integer slotId, String slotName, Double slotCapacity, String warehouseName, Integer warehouseId) {
        this.type = type;
        this.image = image;
        this.quality = quality;
        this.weight = weight;
        this.slotId = slotId;
        this.slotName = slotName;
        this.area = area;
        this.slotCapacity=slotCapacity;
        this.warehouseName = warehouseName;
        this.location = slotName+"/"+warehouseName;
        this.typeWithQuality=type+" "+quality;
        this.warehouseId=warehouseId;

    }
}
