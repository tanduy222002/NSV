package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Util.ConvertUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDetailDto {
    private Integer ID;
    private String name;
    private String phone;

    @Schema(hidden = true)
    private Address address;

    @JsonProperty("address_string")
    private String addressString;





    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "fax_number")
    private String faxNumber;

    @JsonProperty("total_transaction")
    private long totalTransaction;

    @JsonProperty("total_transaction_amount")
    private Double totalTransactionAmount;


    public PartnerDetailDto(Integer ID, String name, String phone) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
    }

    public PartnerDetailDto(Integer ID, String name, String phone, Address address,  String bankAccount, String taxNumber, String faxNumber, long totalTransaction, Double totalTransactionAmount) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.addressString = ConvertUtil.convertAddressToString(address);
        this.bankAccount = bankAccount;
        this.taxNumber=taxNumber;
        this.faxNumber=faxNumber;
        this.totalTransaction=totalTransaction;
        this.totalTransactionAmount=totalTransactionAmount;
    }
}
