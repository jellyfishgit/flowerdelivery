package flowerdelivery;

import java.util.Optional;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String itemName;
    private Integer qty;
    private Long itemPrice;
    private String storeName;
    private String userName;
    private Long itemId;
    private String orderStatus;

    @PostPersist
    public void onPostPersist(){
        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        flowerdelivery.external.Payment payment = new flowerdelivery.external.Payment();
        payment.setOrderId(this.getId());
        payment.setItemPrice(this.getItemPrice());
        payment.setItemName(this.getItemName());
        payment.setQty(this.getQty());
        payment.setStoreName(this.getStoreName());
        payment.setItemId(this.getItemId());
        payment.setPaymentStatus("paid");
        
        // mappings goes here
        OrderApplication.applicationContext.getBean(flowerdelivery.external.PaymentService.class)
            .pay(payment);


    }


     /**
     * 주문이 취소됨
     */
    @PostUpdate
    private void onPostUpdate(){
        if( "OrderCancelled".equals(this.getOrderStatus())){
            // 이벤트를 발송하기 위하여 주문의 상세 정보를 조회

            OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
            Optional<Order> orderOptional = orderRepository.findById(this.getId());
            Order order = orderOptional.get();

            OrderCancelled orderCancelled = new OrderCancelled();
            orderCancelled.setId(order.getId());
            orderCancelled.setItemId(order.getItemId());
            orderCancelled.setQty(order.getQty());

            orderCancelled.publish();
        }
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
