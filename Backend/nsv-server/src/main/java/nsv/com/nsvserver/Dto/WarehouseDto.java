package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Util.ConvertUtil;
import nsv.com.nsvserver.Util.NumberUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDto {
    private Integer id;

    private String name;

    private String type;

    private Address address;

    @JsonProperty("address_string")
    private String addressString;

    private Double containing;

    private Double capacity;

    @JsonProperty("current_capacity")
    private String currentCapacity;

    private String status;

    @JsonProperty(value = "lower_temperature")
    private Double lowerTemperature;
    @JsonProperty(value = "upper_temperature")
    private Double upperTemperature;

    public WarehouseDto(Integer id, String name, String type, Address address, Double containing, Double capacity, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.containing = containing;
        this.capacity = capacity;
        this.status = status;
        this.currentCapacity =NumberUtil.removeTrailingZero(containing)+"/"+NumberUtil.removeTrailingZero(capacity);
        this.addressString= ConvertUtil.convertAddressToString(address);
    }


    public WarehouseDto(Integer id, String name, String type, Address address, Double containing, Double capacity, String status, Double lowerTemperature, Double upperTemperature) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.containing = containing;
        this.capacity = capacity;
        this.status = status;
        this.currentCapacity =NumberUtil.removeTrailingZero(containing)+"/"+NumberUtil.removeTrailingZero(capacity);
        this.addressString= ConvertUtil.convertAddressToString(address);
        this.lowerTemperature = lowerTemperature;
        this.upperTemperature = upperTemperature;
    }
}
