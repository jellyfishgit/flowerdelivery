package flowerdelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 @RestController
 public class OrdermanagementController {

    @Autowired
    OrdermanagementService ordermanagementService;

    
    @PutMapping("/ordermanagements/receive/{ordermanagementId}")
    void receiveOrdermanagement(@PathVariable(value = "ordermanagementId") Long ordermanagementId) {
        this.ordermanagementService.changeOrdermanagementStatus(ordermanagementId, "Received");
    }

    @PutMapping("/ordermanagements/decorate/{ordermanagementId}")
    void decorateOrdermanagement(@PathVariable(value = "ordermanagementId") Long ordermanagementId) {
        this.ordermanagementService.changeOrdermanagementStatus(ordermanagementId, "Decorated");
    }

    @PutMapping("/ordermanagements/reject/{ordermanagementId}")
    void rejectOrdermanagement(@PathVariable(value = "ordermanagementId") Long ordermanagementId) {
        this.ordermanagementService.changeOrdermanagementStatus(ordermanagementId, "Rejected");
    }

    



 }
