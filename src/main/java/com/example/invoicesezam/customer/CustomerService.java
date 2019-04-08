package com.example.invoicesezam.customer;

import com.example.invoicesezam.invoice.Invoice;
import com.example.invoicesezam.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService
{

     Page<Customer> getPaginatedCustomers(Pageable page);

     void save(Customer customer);

     Customer findById(long id);

     void delete(long id);

     List<Product> findByName(String search);

     Product findProductById(long id);

     void saveInvoice(Invoice invoice);

     Invoice findInvoiceById(long id);

     void deleteInvoice(long id);

     Invoice fetchByIdWithCustomerWithInvoiceElementWithProduct(long id);

     Customer fetchByIdWithInvoice(long id);

     List<Customer> findCustomersByName(String name);
}
