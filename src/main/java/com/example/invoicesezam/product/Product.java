package com.example.invoicesezam.product;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String sku;
    @NotEmpty
    private String name;
    @NotEmpty
    private String details;

    private double price;

    private int vat;

    @Temporal(TemporalType.DATE)
    @Column(name="created_at")
    @NotNull
    private Date createdAt;

    @PrePersist
    public void prePersist()
    {
        createdAt=new Date();
    }

    public Product()
    {

    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getVat()
    {
        return vat;
    }

    public void setVat(int vat)
    {
        this.vat = vat;
    }

    public double getNetPrice()
    {
        if (vat ==0)
            return price;
        else
            return (price*100)/(vat+100);
    }
}
