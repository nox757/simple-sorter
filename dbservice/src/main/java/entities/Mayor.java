package entities;

import dai.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "mayor")
public class Mayor implements Identifiable<Long> {

    @Id
    @Column(name = "mayor_id")
    @SequenceGenerator(name="mayor_seq", sequenceName="mayor_mayor_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mayor_seq")
    private Long id;

    @Column(name = "fio")
    private String fio;

    @OneToOne(mappedBy = "mayor", fetch = FetchType.LAZY)
    private City city;

    @Version
    private long version;

    public Mayor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public long getVersion() {
        return version;
    }

    public Mayor setVersion(long version) {
        this.version = version;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mayor mayor = (Mayor) o;

        return id != null ? id.equals(mayor.id) : mayor.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
