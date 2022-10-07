package ec.com.edimca.posbackend.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ec.com.edimca.posbackend.validation.ProductValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "public")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(groups = { ProductValidation.New.class })
    @NotEmpty(groups = { ProductValidation.New.class })
    private String name;

    @NotNull(groups = { ProductValidation.New.class })
    @Min(value = 0, groups = { ProductValidation.New.class })
    @Max(value = 9999, groups = { ProductValidation.New.class })
    @Column(columnDefinition = "NUMERIC(10,2)")
    private Double price;

    @NotNull(groups = { ProductValidation.New.class })
    @NotEmpty(groups = { ProductValidation.New.class })
    private String description;

    @NotNull(groups = { ProductValidation.New.class })
    @NotEmpty(groups = { ProductValidation.New.class })
    @Column(name = "use_cases")
    private String useCases;

    @NotNull(groups = { ProductValidation.New.class })
    @NotEmpty(groups = { ProductValidation.New.class })
    @Column(name = "format_available")
    private String formatAvailable;

    @NotNull(groups = { ProductValidation.New.class })
    private short stock;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

}
