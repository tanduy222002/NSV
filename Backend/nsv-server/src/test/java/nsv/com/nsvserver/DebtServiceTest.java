package nsv.com.nsvserver;

import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Entity.Debt;
import nsv.com.nsvserver.Exception.NotFoundException;
import nsv.com.nsvserver.Repository.DebtRepository;
import nsv.com.nsvserver.Service.DebtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DebtServiceTest {
    @Mock
    DebtRepository debtRepository;

    @InjectMocks
    DebtService debtService;

    @Test
    public void putDebt_DebtNotFound_NotFoundExceptionThrown() {
        // Arrange
        Integer debtId = 1;
        when(debtRepository.findById(debtId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            debtService.putDebt(debtId, "New Debt", 100.0, "Description", new Date(), new Date(), false, "USD");
        });

        assertEquals("Debt is not found: 1", exception.getMessage());
        verify(debtRepository, times(1)).findById(debtId);
    }

    @Test
    public void putDebt_DebtFound_updatesAndReturnsDebtDetailDto() {
        // Arrange
        Integer debtId = 1;
        Debt debt = new Debt();
        debt.setId(debtId);
        debt.setName("Old Debt");
        debt.setAmount(50.0);
        debt.setNote("Old Description");
        debt.setCreateDate(new Date());
        debt.setDueDate(new Date());
        debt.setIsPaid(false);
        debt.setUnit("USD");

        when(debtRepository.findById(debtId)).thenReturn(Optional.of(debt));

        String newName = "New Debt";
        Double newAmount = 100.0;
        String newDescription = "New Description";
        Date newCreateDate = new Date();
        Date newDueDate = new Date();
        Boolean newIsPaid = true;
        String newUnit = "EUR";

        // Act
        DebtDetailDto result = debtService.putDebt(debtId, newName, newAmount, newDescription, newCreateDate, newDueDate, newIsPaid, newUnit);

        // Assert
        assertNotNull(result);
        assertEquals(debtId, result.getId());
        assertEquals(newName, result.getName());
        assertEquals(newAmount, result.getValue());
        assertEquals(newDescription, result.getDescription());
        assertEquals(newCreateDate, result.getCreate_date());
        assertEquals(newDueDate, result.getDueDate());
        assertEquals(newIsPaid, result.getIsPaid());
        assertEquals(newUnit, result.getUnit());
        assertNotNull(result.getPaidDate());

        verify(debtRepository, times(1)).findById(debtId);
        verify(debtRepository, times(1)).save(debt);
    }

    @Test
    public void putDebt_AllNullParameters_doesNotChangeDebt() {
        // Arrange
        Integer debtId = 1;
        Debt debt = new Debt();
        debt.setId(debtId);
        debt.setName("Old Debt");
        debt.setAmount(50.0);
        debt.setNote("Old Description");
        debt.setCreateDate(new Date());
        debt.setDueDate(new Date());
        debt.setIsPaid(false);
        debt.setUnit("USD");

        when(debtRepository.findById(debtId)).thenReturn(Optional.of(debt));

        // Act
        DebtDetailDto result = debtService.putDebt(debtId, null, null, null, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(debtId, result.getId());
        assertEquals("Old Debt", result.getName());
        assertEquals(50.0, result.getValue());
        assertEquals("Old Description", result.getDescription());
        assertEquals(debt.getCreateDate(), result.getCreate_date());
        assertEquals(debt.getDueDate(), result.getDueDate());
        assertEquals(false, result.getIsPaid());
        assertEquals("USD", result.getUnit());
        assertNull(result.getPaidDate());

        verify(debtRepository, times(1)).findById(debtId);
        verify(debtRepository, times(1)).save(debt);
    }

}
