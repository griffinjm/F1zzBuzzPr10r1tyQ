package ie.jgriffin.priorityq.rest;

import ie.jgriffin.priorityq.model.WorkOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jgriffin on 17/04/2017.
 */
@RestController
@RequestMapping("/work-orders")
public class RestRequestController {

    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> putWorkOrder(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        ResponseEntity<WorkOrder> responseEntity = ResponseEntity.ok(workOrder);

        return responseEntity;
    }


}
