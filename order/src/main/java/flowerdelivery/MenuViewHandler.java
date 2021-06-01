package flowerdelivery;

import flowerdelivery.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MenuViewHandler {


    @Autowired
    private MenuRepository menuRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenItemRegistered_then_CREATE_1 (@Payload ItemRegistered itemRegistered) {
        try {
            //if (!itemRegistered.validate()) return;
            if(itemRegistered.isMe()){

                 // view 객체 생성
                Menu menu = new Menu();
                // view 객체에 이벤트의 Value 를 set 함
                menu.setItemName(itemRegistered.getItemName());
                menu.setStoreName(itemRegistered.getStoreName());
                menu.setItemPrice(itemRegistered.getItemPrice());
                menu.setItemId(itemRegistered.getId());
                // view 레파지 토리에 save
                menuRepository.save(menu);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}