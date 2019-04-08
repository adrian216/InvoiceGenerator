package com.example.invoicesezam.customer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class Address
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String street;
    @NotEmpty
    private String postalCode;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;

    @OneToOne(mappedBy = "address")
    private Customer customer;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString()
    {
        return street + " \n" + postalCode+ " " + city;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                street.equals(address.street) &&
                postalCode.equals(address.postalCode) &&
                city.equals(address.city) &&
                country.equals(address.country);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, street, postalCode, city, country);
    }
}
