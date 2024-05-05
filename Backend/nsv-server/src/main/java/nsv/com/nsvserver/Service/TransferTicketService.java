package nsv.com.nsvserver.Service;

import jakarta.transaction.Transactional;
import nsv.com.nsvserver.Annotation.TraceTime;
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
import java.util.concurrent.CopyOnWriteArrayList;
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
        transferTicket.getBins().stream().forEach(bin->{
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

    @TraceTime
    public PageDto getTransferTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        List<TransferTicket> ticket = ticketDaoImpl.getTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status);

        List<TransferTicketDto> transferTicketWithBinDtos = ticket.stream().map(transferTicket -> {
            TransferTicketDto dto = new TransferTicketDto();
            dto.setId(transferTicket.getId());
            dto.setName(transferTicket.getName());
            dto.setTransferDate(transferTicket.getCreateDate());
            dto.setDescription(transferTicket.getDescription());
            dto.setStatus(transferTicket.getStatus());

            Set<String> productNames= new HashSet<>();
            AtomicReference<Double> totalWeight= new AtomicReference<>(0.0);
                transferTicket.getBins().stream().forEach(bin->{
                Quality quality = bin.getQuality();
                Type productType = quality.getType();
                Product product = productType.getProduct();
                if(!productNames.contains(product.getName())){
                    productNames.add(product.getName());
                }
                totalWeight.updateAndGet(v ->  v + bin.getWeight());
            });

            dto.setWeight(totalWeight.get());
            dto.setNumberOfProducts(productNames.size());
            dto.setProduct(productNames.toString());
            return dto;
        }).collect(Collectors.toList());

        long count= ticketDaoImpl.countTotalTicketWithFilterAndPagination(pageIndex, pageSize, name, type, status);
        return new PageDto(Math.ceil((double)count/pageSize),count,pageIndex,transferTicketWithBinDtos);
    }




    @Transactional
    @TraceTime
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

        List<Bin> bins=exportBinsDto.parallelStream().map(binDto->{

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

                BinSlot binSlot= binDaoImpl.GetBinSlotBySlotIdAndBinId(binId,slotId);

                Bin importBin = binSlot.getBin();

                if(importBin == null){
                    throw new NotFoundException("Slot: "+slotId+" does not contains bin: "+binId);
                }


//                BinSlot binSlot= importBin.getBinSlot().get(0);
                Slot containingSlot=binSlot.getSlot();
//                System.out.println(containingSlot.getId());
//                System.out.println(binSlot.getWeight());

                if(binSlot.getArea()<takenArea){
                    throw new SlotAreaMismatchException(takenArea+ " m2 is larger the current area of bin in slot: " + containingSlot.getName());
                }


                binWeight.updateAndGet(v -> v + takenWeight);

                binBin.setImportBin(importBin);
                binBin.setExportBin(bin);
                binBin.setWeight(takenWeight);
                binBin.setImportSlot(containingSlot);
                binBin.setArea(takenArea);

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


    @Transactional
    public void approveExportTicketStatus(Integer id) {
        TransferTicket transferTicket = ticketDaoImpl.fetchExportTicket(id);
//        if(id==40){
//            return;
//        }
        if(!transferTicket.getType().equals("EXPORT"))
            throw new TicketStatusMismatchException();
        if(transferTicket.getStatus().equals("APPROVED")) {
            throw new TicketStatusMismatchException();
        }
        transferTicket.setStatus("APPROVED");

        transferTicket.getBins().stream().forEach(bin->{
            bin.setStatus("APPROVED");
            bin.getImportBins().stream().forEach(binBin -> {
                Bin importBin = binBin.getImportBin();
                if(importBin.getLeftWeight()<binBin.getWeight()){
                    throw new BinWeightMismatchException();
                }

                importBin.setLeftWeight(importBin.getLeftWeight()-binBin.getWeight());

                Slot containingSlot = binBin.getImportSlot();
                double area= binBin.getArea();

                Warehouse warehouse=containingSlot.getRow().getMap().getWarehouse();
                if(containingSlot.getCapacity()-containingSlot.getContaining()<area){
                    throw new SlotAreaMismatchException(area+ " m2 is overload the left capacity of slot: " + containingSlot.getName());
                }
                else {
                    containingSlot.setContaining(containingSlot.getContaining()-area);
                    warehouse.setContaining(warehouse.getContaining()-area);
                }
            });


        Employee employee = EmployeeDetailService.getCurrentUserDetails();
        employee= employeeRepository.findById(employee.getId()).orElseThrow(()->new NotFoundException("No employee found"));
        transferTicket.setApprovedEmployee(employee);
        transferTicket.setApprovedDate(new Date());
        transferTicketRepository.save(transferTicket);
    }
}
