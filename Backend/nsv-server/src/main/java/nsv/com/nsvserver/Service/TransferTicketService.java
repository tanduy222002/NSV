package nsv.com.nsvserver.Service;

import jakarta.transaction.Transactional;
import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.*;
import nsv.com.nsvserver.Exception.BinWeightMismatchException;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Exception.SlotAreaMismatchException;
import nsv.com.nsvserver.Exception.TicketStatusMismatchException;
import nsv.com.nsvserver.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class TransferTicketService {

    QualityRepository qualityRepository;
    SlotRepository slotRepository;
    TransferTicketRepository transferTicketRepository;

    PartnerRepository partnerRepository;

    EmployeeRepository employeeRepository;

    TicketDao ticketDaoImpl;

    BinSlotRepository binSlotRepository;

    BinDao binDaoImpl;

    @Autowired
    public TransferTicketService(QualityRepository qualityRepository, SlotRepository slotRepository, TransferTicketRepository transferTicketRepository, PartnerRepository partnerRepository, EmployeeRepository employeeRepository, TicketDao ticketDaoImpl, BinSlotRepository binSlotRepository, BinDao binDaoImpl) {
        this.qualityRepository = qualityRepository;
        this.slotRepository = slotRepository;
        this.transferTicketRepository = transferTicketRepository;
        this.partnerRepository = partnerRepository;
        this.employeeRepository = employeeRepository;
        this.ticketDaoImpl = ticketDaoImpl;
        this.binSlotRepository = binSlotRepository;
        this.binDaoImpl = binDaoImpl;
    }

    @Transactional
    public String createImportTransferTicket(CreateTransferTicketDto importTicketDto){
        List<CreateBinDto> binDtos =importTicketDto.getBinDto();
        CreateDebtDto createDebtDto =importTicketDto.getDebtDto();
        Employee employee = EmployeeDetailService.getCurrentUserDetails();
        employee= employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundException("No employee found"));
        TransferTicket transferTicket = new TransferTicket();
        transferTicket.setType("IMPORT");
        transferTicket.setName(importTicketDto.getName());
        transferTicket.setDescription(importTicketDto.getDescription());
        transferTicket.setCreateDate(new Date());
        transferTicket.setStatus("PENDING");
        transferTicket.setCreateEmployee(employee);
        Date importDate = importTicketDto.getImportDate();
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

        List<Bin> bins=binDtos.stream().map(binDto->{
            Bin bin = new Bin();
            Quality quality = qualityRepository.findById(binDto.getQualityId()).orElseThrow(()->new NotFoundException("Quality does not exist"));
            bin.setQuality(quality);
            bin.setTransferTicket(transferTicket);
            bin.setDocument(binDto.getNote());
            bin.setImportDate(importDate);
            bin.setPrice(binDto.getPrice());
            bin.setPackageType(binDto.getPackageType());
            List<BinSlot> binslots =new ArrayList<>();

            AtomicReference<Double> binWeight = new AtomicReference<>(0.0);
            binDto.getBinWithSlot().stream().forEach(slotWithBin -> {
                BinSlot binSlot = new BinSlot();
                Integer slotId=slotWithBin.getSlotId();
                double area = slotWithBin.getArea();
                Slot slot = slotRepository.findById(slotId).orElseThrow(()->new NotFoundException("Slot does not exist"));
                if(slot.getCapacity()-slot.getContaining()<area){
                    throw new SlotAreaMismatchException(area+ " m2 is overload the left capacity of slot: " + slot.getName());
                }
                binSlot.setSlot(slot);
                binSlot.setWeight(slotWithBin.getWeight());
                binSlot.setArea(slotWithBin.getArea());
                binSlot.setBin(bin);
                binWeight.updateAndGet(v -> v + slotWithBin.getWeight());
                binslots.add(binSlot);

            });
            bin.setBinSlot(binslots);
            if(binDto.getWeight()!=binWeight.get()) {
                throw new BinWeightMismatchException();
            }else{
                bin.setWeight(binDto.getWeight());
                bin.setLeftWeight(binDto.getWeight());
            }

            return bin;

        }).collect(Collectors.toList());

        transferTicket.setBins(bins);
        Partner partner = partnerRepository.findById(importTicketDto.getProviderId()).orElseThrow(()->new NotFoundException("Partner does not exist"));
        transferTicket.setPartner(partner);
        transferTicketRepository.save(transferTicket);
        return "New Import Transfer Ticket created with id: " + transferTicket.getId();
    }


    @Transactional
    public void approveTicketStatus(Integer id) {
        TransferTicket transferTicket = ticketDaoImpl.fetchWithBinAndSlot(id);
        if(transferTicket.getStatus().equals("APPROVED")) {
            throw new TicketStatusMismatchException();
        }
        transferTicket.setStatus("APPROVED");
        transferTicket.getBins().parallelStream().forEach(bin->{
            bin.setStatus("APPROVED");

            bin.getBinSlot().stream().forEach(binSlot -> {
            Slot slot = binSlot.getSlot();
            double area= binSlot.getArea();
            slot.setStatus("CONTAINING");

            Warehouse warehouse=slot.getRow().getMap().getWarehouse();
            if(slot.getCapacity()-slot.getContaining()<area){
                    throw new SlotAreaMismatchException(area+ " m2 is overload the left capacity of slot: " + slot.getName());
            }
            else {
                slot.setContaining(slot.getContaining()+area);
                warehouse.setContaining(warehouse.getContaining()+binSlot.getArea());
            }
            });
        });

        Employee employee = EmployeeDetailService.getCurrentUserDetails();
        employee= employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundException("No employee found"));
        transferTicket.setApprovedEmployee(employee);
        transferTicket.setApprovedDate(new Date());
        transferTicketRepository.save(transferTicket);
    }

    public PageDto getTransferTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        List<TransferTicket> ticket = ticketDaoImpl.getTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status);

        List<TransferTicketWithBinDto> transferTicketWithBinDtos = ticket.stream().map(transferTicket -> {
            TransferTicketWithBinDto dto = new TransferTicketWithBinDto();
            dto.setId(transferTicket.getId());
            dto.setName(transferTicket.getName());
            dto.setImportDate(transferTicket.getCreateDate());
            dto.setStatus(dto.getStatus());
            dto.setDescription(dto.getDescription());
            AtomicReference<Double> totalWeight= new AtomicReference<>(0.0);
            List<BinDto> binDtos=transferTicket.getBins().stream().map(bin->{
                BinDto binDto = new BinDto();
                binDto.setId(bin.getId());
                binDto.setPackaged(bin.getPackageType());
                Quality quality = bin.getQuality();
                Type productType = quality.getType();
                Product product = productType.getProduct();

                binDto.setProduct(product.getName());
                binDto.setQualityWithType(productType.getName()+" "+quality.getName());
                binDto.setProductImg(product.getImage());
                binDto.setQualityId(quality.getId());
                binDto.setWeight(bin.getWeight());
                totalWeight.updateAndGet(v ->  v + bin.getWeight());
                return binDto;
            }).collect(Collectors.toList());
            dto.setWeight(totalWeight.get());
            dto.setBins(binDtos);
            return dto;
        }).collect(Collectors.toList());

        long count= ticketDaoImpl.countTotalTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,transferTicketWithBinDtos);
    }
    @Transactional
    public String createExportTicket(CreateExportTransferTicketDto exportTicketDto){
        
        List<CreateExportBinDto> exportBinsDto = exportTicketDto.getExportBinDto();
        CreateDebtDto createDebtDto =exportTicketDto.getDebtDto();
        Employee employee = EmployeeDetailService.getCurrentUserDetails();
        employee= employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundException("No employee found"));
        
        TransferTicket transferTicket = new TransferTicket();
        transferTicket.setType("EXPORT");
        transferTicket.setName(exportTicketDto.getName());
        transferTicket.setDescription(exportTicketDto.getDescription());
        transferTicket.setCreateDate(new Date());
        transferTicket.setStatus("PENDING");
        transferTicket.setCreateEmployee(employee);
        Date exportDate = exportTicketDto.getExportDate();
        transferTicket.setTransportDate(exportDate);

        if(createDebtDto!=null){
            Debt debt = new Debt();
            debt.setAmount(createDebtDto.getValue());
            debt.setNote(createDebtDto.getDescription());
            debt.setCreateDate(new Date());
            debt.setDueDate(createDebtDto.getDueDate());
            debt.setName(createDebtDto.getName());
            transferTicket.addDebt(debt);
        }

        List<Bin> bins=exportBinsDto.stream().map(binDto->{
            Bin bin = new Bin();
            Quality quality = qualityRepository.findById(binDto.getQualityId()).orElseThrow(()->new NotFoundException("Quality does not exist"));
            bin.setQuality(quality);
            bin.setTransferTicket(transferTicket);
            bin.setDocument(binDto.getNote());
            bin.setExportDate(exportDate);
            bin.setPrice(binDto.getPrice());
            bin.setPackageType(binDto.getPackageType());
            List<BinBin> binBins =new ArrayList<>();

            AtomicReference<Double> binWeight = new AtomicReference<>(0.0);
            binDto.getImportBinWithSlot().stream().forEach(slotWithImportBin -> {
                BinBin binBin = new BinBin();
                Integer slotId=slotWithImportBin.getSlotId();
                Integer binId = slotWithImportBin.getBin().getId();
                double takenArea = slotWithImportBin.getTakenArea();
                double takenWeight = slotWithImportBin.getTakenWeight();
                Bin importBin = binDaoImpl.findBinInSlotBySlotIdAndBinId(binId,slotId).orElseThrow(()->new NotFoundException("Slot: "+slotId+" does not contains bin: "+binId));
                BinSlot binSlot=importBin.getBinSlot().get(0);
                Slot containingSlot=binSlot.getSlot();

                if(binSlot.getArea()<takenArea){
                    throw new SlotAreaMismatchException(takenArea+ " m2 is larger the current area of bin in slot: " + containingSlot.getName());
                }

                double importBinAreaInSlot=binSlot.getArea();
                binSlot.setWeight(binSlot.getWeight()-takenWeight);
                binSlot.setArea(importBinAreaInSlot-takenArea);
                binWeight.updateAndGet(v -> v + takenWeight);

                binBin.setImportBin(importBin);
                binBin.setExportBin(bin);
                binBins.add(binBin);
            });

            if(binDto.getWeight()!=binWeight.get()) {
                throw new BinWeightMismatchException();
            }else{
                bin.setWeight(binDto.getWeight());
                bin.setLeftWeight(binDto.getWeight());
            }
            

            bin.setImportBins(binBins);

            return bin;

        }).collect(Collectors.toList());

        transferTicket.setBins(bins);
        Partner partner = partnerRepository.findById(exportTicketDto.getCustomerId()).orElseThrow(()->new NotFoundException("Partner does not exist"));
        transferTicket.setPartner(partner);
        transferTicketRepository.save(transferTicket);

        return "New Export Transfer Ticket created with id: " + transferTicket.getId();
    }
}
