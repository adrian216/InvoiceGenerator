package com.example.invoicesezam.invoice;

import com.example.invoicesezam.customer.Customer;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="invoice_id")
    private Set<InvoiceElement> invoiceElementSet;

    @Temporal(TemporalType.DATE)
    @Column(name="created_at")
    private Date createdAt;

    public Invoice()
    {
        invoiceElementSet = new HashSet<>();
    }

    @PrePersist
    public void prePersist()
    {
        createdAt = new Date();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Set<InvoiceElement> getInvoiceElementSet()
    {
        return invoiceElementSet;
    }

    public void setInvoiceElementSet(Set<InvoiceElement> invoiceElementSet)
    {
        this.invoiceElementSet = invoiceElementSet;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public void addElement(InvoiceElement invoiceElement)
    {
        this.invoiceElementSet.add(invoiceElement);
    }

    public Double getTotal()
    {
        double total=0.0;
        for (InvoiceElement invoiceElement : invoiceElementSet)
        {
            total+=invoiceElement.calculatePrice();
        }
        return total;
    }

    public Double getTotalNet()
    {
        double total=0.0;
        for (InvoiceElement invoiceElement : invoiceElementSet)
        {
            total+=invoiceElement.calculateNetPrice();
        }
        return total;
    }

    public double getTotalVat()
    {
        return invoiceElementSet.stream().mapToDouble(value -> value.calculateVat()).sum();
    }
}
