package nsv.com.nsvserver.Service;

import jakarta.transaction.Transactional;
import nsv.com.nsvserver.Dto.CreateBinDto;
import nsv.com.nsvserver.Dto.CreateDebtDto;
import nsv.com.nsvserver.Dto.CreateTransferTicketDto;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferTicketService {

    QualityRepository qualityRepository;
    SlotRepository slotRepository;
    TransferTicketRepository transferTicketRepository;

    PartnerRepository partnerRepository;

    EmployeeRepository employeeRepository;

    @Autowired
    public TransferTicketService(QualityRepository qualityRepository, SlotRepository slotRepository, TransferTicketRepository transferTicketRepository, PartnerRepository partnerRepository, EmployeeRepository employeeRepository) {
        this.qualityRepository = qualityRepository;
        this.slotRepository = slotRepository;
        this.transferTicketRepository = transferTicketRepository;
        this.partnerRepository = partnerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public String createImportTransferTicket(CreateTransferTicketDto createTransferTicketDto){
        List<CreateBinDto> binDtos =createTransferTicketDto.getBinDto();
        CreateDebtDto createDebtDto =createTransferTicketDto.getDebtDto();
        Employee employee = EmployeeDetailService.getCurrentUserDetails();
        employee= employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundException("No employee found"));
        TransferTicket transferTicket = new TransferTicket();
        transferTicket.setType("IMPORT");
        transferTicket.setName(createTransferTicketDto.getName());
        transferTicket.setDescription(createTransferTicketDto.getDescription());
        transferTicket.setCreateDate(new Date());
        transferTicket.setStatue("PENDING");
        transferTicket.setCreateEmployee(employee);
        Date importDate = createTransferTicketDto.getImportDate();
        transferTicket.setTransportDate(importDate);

        if(createDebtDto!=null){
            Debt debt = new Debt();
            debt.setAmount(createDebtDto.getValue());
            debt.setNote(createDebtDto.getDescription());
            debt.setCreateDate(new Date());
            debt.setDueDate(createDebtDto.getDueDate());
            debt.setName(createDebtDto.getName());
            transferTicket.addDebt(debt);
        }
        List<Bin> bins=binDtos.parallelStream().map(binDto->{
            Bin bin = new Bin();
            Quality quality = qualityRepository.findById(binDto.getQualityId()).orElseThrow(()->new NotFoundException("Quality does not exist"));
            bin.setQuality(quality);
            bin.setTransferTicket(transferTicket);
            bin.setAmount(binDto.getCount());
            bin.setWeight(binDto.getWeight());
            bin.setDocument(binDto.getNote());
            bin.setImportDate(importDate);
            bin.setPrice(binDto.getPrice());
            bin.setPackageType(binDto.getPackageType());

            List<Slot> slots=binDto.getSlotId().stream().map(slotId -> {
                Slot slot = slotRepository.findById(slotId).orElseThrow(()->new NotFoundException("Slot does not exist"));
                slot.addBin(bin);
                return slot;
            }).collect(Collectors.toList());
            bin.setSlots(slots);
            return bin;

        }).collect(Collectors.toList());
        transferTicket.setBins(bins);

        Partner partner = partnerRepository.findById(createTransferTicketDto.getProviderId()).orElseThrow(()->new NotFoundException("Partner does not exist"));
        transferTicket.setPartner(partner);
        transferTicketRepository.save(transferTicket);
        return "New Import Transfer Ticket created with id: " + transferTicket.getId();

    }
}
