package ie.jgriffin.priorityq.rest;


import ie.jgriffin.priorityq.model.WorkOrder;
import ie.jgriffin.priorityq.model.impl.RankConstants;
import ie.jgriffin.priorityq.model.impl.WorkOrderImpl;
import ie.jgriffin.priorityq.persistence.WorkOrderQueue;
import ie.jgriffin.priorityq.validation.WorkOrderValidator;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by jgriffin on 18/04/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WorkOrderRestControllerTest {

    @InjectMocks
    WorkOrderRestController workOrderRestController;

    @Mock
    WorkOrderQueue mockWorkOrderQueue;
    @Mock
    WorkOrderValidator mockWorkOrderValidator;
    @Mock
    WorkOrderImpl mockWorkOrder;

    @Before
    public void setup() {

    }

    @Test
    public void putWorkOrder_IdsDoNotMatch_ReturnBadRequest() {
        when(mockWorkOrder.getId()).thenReturn(RankConstants.MANAGEMENT_OVERRIDE);

        ResponseEntity<?> responseEntity = workOrderRestController.putWorkOrder(RankConstants.PRIORITY_FACTOR, mockWorkOrder);

        assertEquals("incorrect response status code", HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void putWorkOrder_IdInvalid_ReturnBadRequest() {
        when(mockWorkOrder.getId()).thenReturn(-1L);

        ResponseEntity<?> responseEntity = workOrderRestController.putWorkOrder(-1L, mockWorkOrder);

        assertEquals("incorrect response status code", HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void dequeue_Exists_ReturnsOk() {
        when(mockWorkOrderQueue.getFirst()).thenReturn(mockWorkOrder);

        ResponseEntity<WorkOrder> responseEntity = workOrderRestController.dequeue();

        assertEquals("incorrect response body", mockWorkOrder, responseEntity.getBody());
        assertEquals("incorrect status code",HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void dequeue_DoesNotExist_ReturnsNoContent() {
        when(mockWorkOrderQueue.getFirst()).thenReturn(null);

        ResponseEntity<WorkOrder> responseEntity = workOrderRestController.dequeue();

        assertEquals("incorrect status code",HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void ids_ReturnsOK() {
        List<Long> idList = new LinkedList<>();
        when(mockWorkOrderQueue.getSortedIds()).thenReturn(idList);

        ResponseEntity<List<Long>> responseEntity = workOrderRestController.ids();

        assertEquals("incorrect status code",HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("incorrect response body", idList, responseEntity.getBody());
    }

    @Test
    public void remove_DoesNotExist_ReturnsNotFound() {
        when(mockWorkOrderQueue.remove(RankConstants.VIP_FACTOR)).thenReturn(null);

        ResponseEntity<?> responseEntity = workOrderRestController.remove(RankConstants.VIP_FACTOR);

        assertEquals("incorrect status code", HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void remove_Exists_ReturnsNoContent() {
        when(mockWorkOrderQueue.remove(RankConstants.VIP_FACTOR)).thenReturn(mockWorkOrder);

        ResponseEntity<?> responseEntity = workOrderRestController.remove(RankConstants.VIP_FACTOR);

        assertEquals("incorrect status code", HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void position_DoesNotExist_ReturnsNotFound(){
        when(mockWorkOrderQueue.getPosition(RankConstants.VIP_FACTOR)).thenReturn(null);

        ResponseEntity<?> responseEntity = workOrderRestController.position(RankConstants.VIP_FACTOR);

        assertEquals("incorrect status code", HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void position_Exists_ReturnsOk(){
        when(mockWorkOrderQueue.getPosition(RankConstants.VIP_FACTOR)).thenReturn(0);

        ResponseEntity<?> responseEntity = workOrderRestController.position(RankConstants.VIP_FACTOR);

        assertEquals("incorrect status code", HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("incorrect response body", 0, responseEntity.getBody());
    }

    @Test
    public void averageWait_ReturnsOk(){
        when(mockWorkOrderQueue.getAverageWaitTime(any())).thenReturn(0);

        ResponseEntity<?> responseEntity = workOrderRestController.averageWait(DateTime.now());

        assertEquals("incorrect status code", HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("incorrect response body", 0, responseEntity.getBody());
    }

}
