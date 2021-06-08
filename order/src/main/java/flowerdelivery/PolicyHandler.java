package flowerdelivery;

import flowerdelivery.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
	
	@Autowired(required=true)
	private OrderRepository orderRepository;
	
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_UpdateOrderStatus(@Payload DeliveryCanceled deliveryCanceled){

        if(deliveryCanceled.isMe()){
            System.out.println("##### listener UpdateOrderStatus : " + deliveryCanceled.toJson());
            System.out.println("deliveryCanceled 주문 발생");
            System.out.println("주문 번호 : "+deliveryCanceled.getOrderId());
            orderRepository.deleteById(deliveryCanceled.getOrderId());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDepartedForDelivery_UpdateOrderStatus(@Payload DepartedForDelivery departedForDelivery){

        if(departedForDelivery.isMe()){
            System.out.println("##### listener UpdateOrderStatus : " + departedForDelivery.toJson());
            System.out.println("departedForDelivery 주문 발생");
            System.out.println("주문 번호 : "+ departedForDelivery.getOrderId());
            Order order= new Order();
            order.setId(departedForDelivery.getOrderId());
            orderRepository.save(order);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCompleted_UpdateOrderStatus(@Payload DeliveryCompleted deliveryCompleted){

        if(deliveryCompleted.isMe()){
            System.out.println("##### listener UpdateOrderStatus : " + deliveryCompleted.toJson());
            System.out.println("deliveryCompleted 주문 발생");
            System.out.println("주문 번호 : "+ deliveryCompleted.getOrderId());
            Order order= new Order();
            order.setId(deliveryCompleted.getOrderId());
            orderRepository.save(order);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverForciblyCanceled_UpdateOrderStatus(@Payload ForciblyCanceled forciblyCanceled){

        if(forciblyCanceled.isMe()){
            System.out.println("##### listener UpdateOrderStatus : " + forciblyCanceled.toJson());
            System.out.println("forciblyCanceled 주문 발생");
            System.out.println("주문 번호 : "+ forciblyCanceled.getOrderId());
            orderRepository.deleteById(forciblyCanceled.getOrderId());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReceived_UpdateOrderStatus(@Payload Received received){

        if(received.isMe()){
            System.out.println("##### listener UpdateOrderStatus : " + received.toJson());
            System.out.println("Received 주문 발생");
            System.out.println("주문 번호 : "+ received.getOrderId());
            Order order= new Order();
            order.setId(received.getOrderId());
            orderRepository.save(order);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverItemOutOfStock_ItemOutOfStock(@Payload ItemOutOfStock itemOutOfStock){

        if(itemOutOfStock.isMe())  {
            System.out.println("\n\n##### listener ItemOutOfStock : " + itemOutOfStock.toJson() + "\n\n");
            
            Optional<Order> orderOptional = orderRepository.findById(itemOutOfStock.getOrderId());
            Order order = orderOptional.get();
            order.setOrderStatus("OrderCancelled");
            orderRepository.save(order);
        }
    }
}
