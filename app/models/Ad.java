package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long brand_id;
    private Long model_id;
    private int mileage;
    private int price;

    public Ad(Long brand_id, Long model_id, int mileage, int price) {
        this.brand_id = brand_id;
        this.model_id = model_id;
        this.mileage = mileage;
        this.price = price;
    }

    public Ad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }

    public Long getModel_id() {
        return model_id;
    }

    public void setModel_id(Long model_id) {
        this.model_id = model_id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}