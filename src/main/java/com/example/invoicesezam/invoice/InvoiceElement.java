package com.example.invoicesezam.invoice;

import com.example.invoicesezam.product.Product;

import javax.persistence.*;

@Entity
@Table(name="invoice_element")
public class InvoiceElement
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    public InvoiceElement()
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

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double calculatePrice()
    {
        return quantity*product.getPrice();
    }

    public Double calculateNetPrice()
    {
       return quantity*product.getNetPrice();
    }

    public Double calculateVat()
    {
        return calculateNetPrice()*product.getVat()/100;
    }
}
