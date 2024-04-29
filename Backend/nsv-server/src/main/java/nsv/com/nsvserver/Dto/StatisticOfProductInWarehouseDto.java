package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticOfProductInWarehouseDto {
    @JsonProperty("product_name")
    private String productName;
    private String img;
    private List<StatisticOfTypeAndQuality> type;

    public StatisticOfProductInWarehouseDto(String productName, String productImg) {
        this.productName=productName;
        this.img=productImg;
        this.type=new ArrayList<>();
    }

    public void addType(StatisticOfTypeAndQuality type){
        if(this.type==null){
            this.type=new ArrayList<>();
            this.type.add(type);
        }
        else {
            this.type.add(type);
        }
    }
}
