package ie.jgriffin.priorityq.rest;

import ie.jgriffin.priorityq.model.WorkOrder;
import ie.jgriffin.priorityq.model.impl.WorkOrderImpl;
import ie.jgriffin.priorityq.persistence.WorkOrderQueue;
import ie.jgriffin.priorityq.validation.WorkOrderValidator;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by jgriffin on 17/04/2017.
 */
@RestController
@RequestMapping("/work-orders")
public class WorkOrderRestController {

    private final WorkOrderQueue workOrderQueue;
    private final WorkOrderValidator workOrderValidator;

    public WorkOrderRestController(WorkOrderQueue workOrderQueue, WorkOrderValidator workOrderValidator) {
        this.workOrderQueue = workOrderQueue;
        this.workOrderValidator = workOrderValidator;
    }

    /*
    1.
    Favouring PUT over POST here as we already know the resource id where we want to place the work order
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> putWorkOrder(@PathVariable Long id, @RequestBody WorkOrderImpl workOrder) {
        //ensure the workOrder id matches the path id variable
        if (!id.equals(workOrder.getId())) {
            return ResponseEntity.badRequest().build();
        }

        workOrderValidator.idIsValid(id);
        workOrderValidator.workOrderIsValid(workOrder);

        boolean success = workOrderQueue.add(workOrder);
        if (success) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).body(workOrder);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
    2.
    Using POST here as we are removing the object from the queue so the method is not idempotent as required by DELETE
     */
    @PostMapping("/head")
    public ResponseEntity<WorkOrder> dequeue() {
        //if exists
        WorkOrder queueHead = workOrderQueue.getFirst();
        if (queueHead == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(queueHead);
        }
    }

    /*
    3.
    Using GET as the underlying resource will not be modified by the request
     */
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> ids() {
        return ResponseEntity.ok(workOrderQueue.getSortedIds());
    }

    /*
    4.
    Using DELETE for removal of object directly by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        WorkOrder removedWorkOrder = workOrderQueue.remove(id);
        if (removedWorkOrder == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /*
    5.
    Using GET as the underlying resource will not be modified by the request
    "position" is a virtual subresource on the WorkOrder object
     */
    @GetMapping("/{id}/position")
    public ResponseEntity<Integer> position(@PathVariable Long id) {
        Integer position = workOrderQueue.getPosition(id);
        if (position == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(position);
        }
    }

    /*
    6.
    Using GET as the underlying resource will not be modified by the request
     */
    @GetMapping("/average-wait")
    public ResponseEntity<Integer> averageWait(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime dateTime) {
        return ResponseEntity.ok(workOrderQueue.getAverageWaitTime(dateTime));
    }

}
