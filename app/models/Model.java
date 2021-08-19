package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Date start_prod;
    private Date end_prod;

    public Model(String name, Date start_prod, Date end_prod) {
        this.name = name;
        this.start_prod = start_prod;
        this.end_prod = end_prod;
    }

    public Model() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_prod() {
        return start_prod;
    }

    public void setStart_prod(Date start_prod) {
        this.start_prod = start_prod;
    }

    public Date getEnd_prod() {
        return end_prod;
    }

    public void setEnd_prod(Date end_prod) {
        this.end_prod = end_prod;
    }
}
