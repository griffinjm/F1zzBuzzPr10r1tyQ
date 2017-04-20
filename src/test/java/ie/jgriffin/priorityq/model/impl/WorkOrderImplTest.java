package ie.jgriffin.priorityq.model.impl;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jgriffin on 18/04/2017.
 */
public class WorkOrderImplTest {

    @Test
    public void constructor_ValidFields_GettersReturnPassedValues() {
        final Long id = 1L;
        final DateTime dateTime = DateTime.now();

        WorkOrderImpl workOrder = new WorkOrderImpl(id, dateTime);

        assertEquals("incorrect id", id, workOrder.getId());
        assertEquals("incorrect dateTime", dateTime, workOrder.getDateTime());
    }
}
