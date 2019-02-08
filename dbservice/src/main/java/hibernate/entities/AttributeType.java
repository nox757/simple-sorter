package hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "attribute_type")
public class AttributeType {

    @Id
    @Column(name = "attribute_type_id")
    @SequenceGenerator(name="attribute_type_seq", sequenceName="attribute_type_attribute_type_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="attribute_type_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "attributeType")
    private List<AttributeCity> attributeCity;

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
