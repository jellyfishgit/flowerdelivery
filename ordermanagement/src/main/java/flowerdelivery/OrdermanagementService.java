package flowerdelivery;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdermanagementService {

    @Autowired
    OrdermanagementRepository ordermanagementRepository;


    public void changeOrdermanagementStatus(Long ordermanagementId, String eventString){


        Optional<Ordermanagement> ordermanagementOptional = ordermanagementRepository.findById(ordermanagementId);
        Ordermanagement ordermanagement = ordermanagementOptional.get();

        //상태변경 한다. 
        ordermanagement.setOrdermanagementStatus(eventString);
        
        ordermanagementRepository.save(ordermanagement);

    }
    
}
