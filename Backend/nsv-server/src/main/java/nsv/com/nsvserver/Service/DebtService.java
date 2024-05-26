package nsv.com.nsvserver.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Entity.Debt;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.DebtRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DebtService {
    DebtRepository debtRepository;

    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Transactional
    public DebtDetailDto putDebt(Integer debtId, String name, Double amount, String description, Date createDate, Date dueDate, Boolean isPaid, String unit){
        Debt debt = debtRepository.findById(debtId).orElseThrow(()->new NotFoundException("Debt is not found: "+debtId));
        if(name!=null){
            debt.setName(name);
        }
        if(amount!=null){
            debt.setAmount(amount);
        }
        if(description!=null){
            debt.setNote(description);
        }
        if(createDate!=null){
            debt.setCreateDate(createDate);
        }
        if(dueDate!=null){
            debt.setDueDate(dueDate);
        }
        if(unit!=null){
            debt.setUnit(unit);
        }
        if(isPaid!=null&&isPaid!=debt.getIsPaid()){
            debt.setIsPaid(isPaid);
            if(isPaid){
                debt.setPaidDate(new Date());
            }
        }

        debtRepository.save(debt);

        DebtDetailDto debtDetailDto = new DebtDetailDto(debt.getId(), debt.getName(), debt.getAmount(),
                debt.getCreateDate(), debt.getDueDate(), debt.getIsPaid(), debt.getNote(),debt.getUnit(),debt.getPaidDate());
        return debtDetailDto;
    }

    public String deleteDebt(Integer id) {
        Debt debt = debtRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        debtRepository.delete(debt);
        return "Debt deleted successfully";
    }
}
