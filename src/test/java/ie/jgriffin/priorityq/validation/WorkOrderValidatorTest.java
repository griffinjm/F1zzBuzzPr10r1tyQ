package ie.jgriffin.priorityq.validation;

import ie.jgriffin.priorityq.model.impl.WorkOrderImpl;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jgriffin on 20/04/2017.
 */
public class WorkOrderValidatorTest {

    public WorkOrderValidator workOrderValidator = new WorkOrderValidator();

    @Test
    public void idIsValid_NegativeId_ReturnsFalse() {
        assertFalse(workOrderValidator.idIsValid(-1L));
    }

    @Test
    public void idIsValid_Zero_ReturnsFalse() {
        assertFalse(workOrderValidator.idIsValid(0L));
    }

    @Test
    public void idIsValid_PositiveId_ReturnsTrue() {
        assertTrue(workOrderValidator.idIsValid(1L));
    }

    @Test
    public void workOrderIsValid_NullId_ReturnsFalse() {
        assertFalse(workOrderValidator.workOrderIsValid(new WorkOrderImpl(null, DateTime.now())));
    }

    @Test
    public void workOrderIsValid_NegativeId_ReturnsFalse() {
        assertFalse(workOrderValidator.workOrderIsValid(new WorkOrderImpl(-1L, DateTime.now())));
    }

    @Test
    public void workOrderIsValid_NullDateTime_ReturnsFalse() {
        assertFalse(workOrderValidator.workOrderIsValid(new WorkOrderImpl(1L, null)));
    }

    @Test
    public void workOrderIsValid_PositiveIdAndNonNullDateTime_ReturnsTrue() {
        assertTrue(workOrderValidator.workOrderIsValid(new WorkOrderImpl(1L, DateTime.now())));
    }
}
