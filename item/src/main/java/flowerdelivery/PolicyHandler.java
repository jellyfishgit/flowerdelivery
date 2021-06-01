package flowerdelivery;

import flowerdelivery.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired ItemRepository itemRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_StockChange(@Payload Paid paid){

        if(!paid.validate()) return;

        System.out.println("\n\n##### listener StockChange : " + paid.toJson() + "\n\n");

        // Sample Logic //
        Item item = new Item();
        itemRepository.save(item);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
