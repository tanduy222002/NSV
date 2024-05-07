package nsv.com.nsvserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailDto {
    private Integer id;
    private String name;
    private String status;
    private Date createDate;
    private Date approvedDate;
    private String transporter;
    private String description;
    private PartnerProfileDto partner;
    private DebtDetailDto debt;
    private Double weight;
    private Double value;

    List<?> bins;





}
