package flowerdelivery;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Menu_table")
public class Menu {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String storeName;
        private String itemName;
        private Long itemPrice;
        private Long itemId;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
        public Long getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(Long itemPrice) {
            this.itemPrice = itemPrice;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

}
