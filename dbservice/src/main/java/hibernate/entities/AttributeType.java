package hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attributetype") //todo: rename table
public class AttributeType {

    @Id
    @Column(name = "attribute_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //todo: replace seq
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "attributeType")
    private List<AttributeCity> attributeCity = new ArrayList<>();

    public AttributeType() {
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

    public List<AttributeCity> getAttributeCity() {
        return attributeCity;
    }

    public AttributeType setAttributeCity(List<AttributeCity> attributeCity) {
        this.attributeCity = attributeCity;
        return this;
    }
}
