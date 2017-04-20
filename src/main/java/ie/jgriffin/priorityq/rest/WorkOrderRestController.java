package ie.jgriffin.priorityq.rest;

import ie.jgriffin.priorityq.model.WorkOrder;
import ie.jgriffin.priorityq.model.impl.WorkOrderImpl;
import ie.jgriffin.priorityq.persistence.WorkOrderQueue;
import ie.jgriffin.priorityq.validation.WorkOrderValidator;
import io.swagger.annotations.*;
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
    @ApiOperation(value = "Add a WorkOrder to the queue")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "the id of the WorkOrder to add, valid range: 1-9223372036854775807", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "workOrder", value = "the WorkOrder to add", required = true, dataType = "WorkOrder", paramType = "body")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = WorkOrder.class),
            @ApiResponse(code = 400, message = "BadRequest")
    })
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
    @ApiOperation(value = "Retrieve the WorkOrder at the head of the queue.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = WorkOrder.class),
            @ApiResponse(code = 204, message = "NoContent")
    })
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
    @ApiOperation(value = "Retrieve a list of the ids of the WorkOrders in the sorted queue.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = WorkOrder.class)})
    public ResponseEntity<List<Long>> ids() {
        return ResponseEntity.ok(workOrderQueue.getSortedIds());
    }

    /*
    4.
    Using DELETE for removal of object directly by id
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove a WorkOrder from the queue")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "the id of the WorkOrder to remove, valid range: 1-9223372036854775807", required = true, dataType = "long", paramType = "path"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NoContent"),
            @ApiResponse(code = 400, message = "BadRequest")
    })
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
    @ApiOperation(value = "Return the position of a WorkOrder in the queue, index is zero based.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "the id of the WorkOrder to query, valid range: 1-9223372036854775807", required = true, dataType = "long", paramType = "path"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Integer.class),
            @ApiResponse(code = 204, message = "NoContent")
    })
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
    @ApiOperation(value = "Return the average wait time for all WorkOrders in the queue, zero is returned if the queue is empty.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "the current dateTime, used to determine elapsed time", required = true, dataType = "DateTime in ISO-8601 format", paramType = "query"),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Integer.class)})
    public ResponseEntity<Integer> averageWait(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime dateTime) {
        return ResponseEntity.ok(workOrderQueue.getAverageWaitTime(dateTime));
    }

}
