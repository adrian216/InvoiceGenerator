package com.example.invoicesezam.customer;

import com.example.invoicesezam.invoice.Invoice;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name="first_name")
    private String firstName;

    @NotEmpty
    @Column(name="last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @Temporal(TemporalType.DATE)
    @Column(name="created_at")
    @NotNull
    private Date createdAt;

    @OneToMany(mappedBy = "customer")
    private Set<Invoice> invoices;

    public Customer()
    {
        invoices=new HashSet<Invoice>();
    }

    @PrePersist
    public void prePersist()
    {
        createdAt=new Date();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }
    public String getName()
    {
        return firstName+" "+lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Set<Invoice> getInvoices()
    {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices)
    {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                firstName.equals(customer.firstName) &&
                lastName.equals(customer.lastName) &&
                address.equals(customer.address) &&
                createdAt.equals(customer.createdAt) &&
                Objects.equals(invoices, customer.invoices);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, firstName, lastName, address, createdAt, invoices);
    }
}
