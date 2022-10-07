package ec.com.edimca.posbackend.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@Entity
@Table(name = "order_product", schema = "public")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"order\"", referencedColumnName = "id")
    @Getter(AccessLevel.NONE)
    private OrderHeader order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "id")
    private Product product;

    private short quantity;

    @JsonProperty(access = Access.WRITE_ONLY)
    public OrderHeader getOrder() {
        return order;
    }
    
}
