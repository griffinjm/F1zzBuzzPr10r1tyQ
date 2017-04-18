package ie.jgriffin.priorityq.rest;

import ie.jgriffin.priorityq.model.WorkOrder;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jgriffin on 17/04/2017.
 */
@RestController
@RequestMapping("/work-orders")
public class RestRequestController {

    /*
    1.
    Favouring PUT over POST here as we already know the resource id where we want to place the work order
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> putWorkOrder(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        //if added
        ResponseEntity<WorkOrder> responseEntity = ResponseEntity.created(location).body(workOrder);

        //if already exists
        //ResponseEntity<WorkOrder> responseEntity = ResponseEntity.ok(workOrder);

        //if invalid request
        //ResponseEntity<WorkOrder> responseEntity = ResponseEntity.badRequest();

        return responseEntity;
    }

    /*
    2.
    Using POST here as we are removing the object from the queue so the method is not idempotent as required by DELETE
     */
    @PostMapping("/head")
    public ResponseEntity<WorkOrder> dequeue() {
        //if exists
        ResponseEntity<WorkOrder> responseEntity = ResponseEntity.ok(new WorkOrder(0L, DateTime.now()));
        //if queue empty
        //ResponseEntity<WorkOrder> responseEntity = ResponseEntity.noContent();
        return responseEntity;
    }

    /*
    3.
    Using GET as the underlying resource will not be modified by the request
     */
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> ids() {
        List<Long> idList = new ArrayList<>();
        ResponseEntity<List<Long>> responseEntity = ResponseEntity.ok(idList);
        return responseEntity;
    }

    /*
    4.
    Using DELETE for removal of object directly by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();
        //if it doesn't exist or already deleted
        //ResponseEntity<?> responseEntity = ResponseEntity.notFound().build();
        return  responseEntity;
    }

    /*
    5.
    Using GET as the underlying resource will not be modified by the request
    "position" is a virtual subresource on the WorkOrder object
     */
    @GetMapping("/{id}/position")
    public ResponseEntity<Long> position(@PathVariable Long id){
        ResponseEntity<Long> responseEntity = ResponseEntity.ok(0L);
        //if it doesn't exist
        //ResponseEntity<?> responseEntity = ResponseEntity.notFound().build();
        return responseEntity;
    }

    /*
    6.
    Using GET as the underlying resource will not be modified by the request
     */
    @GetMapping("/average-wait")
    public ResponseEntity<Long> averageWait(@RequestParam DateTime dateTime){
        ResponseEntity<Long> responseEntity = ResponseEntity.ok(0L);
        return responseEntity;
    }

}
