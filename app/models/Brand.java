package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
    public String name;
    public String country;
    public Date created_at;

    public Brand(String name, String country, Date created_at) {
        this.name = name;
        this.country = country;
        this.created_at = created_at;
    }

    public Brand(String name, String country) {
        this.name = name;
        this.country = country;
    }


    public Brand() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
