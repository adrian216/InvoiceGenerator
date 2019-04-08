package com.example.invoicesezam.customer;

import com.example.invoicesezam.invoice.Invoice;
import com.example.invoicesezam.product.Product;
import com.example.invoicesezam.invoice.InvoiceRepository;
import com.example.invoicesezam.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService
{

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private InvoiceRepository invoiceRepository;

    public CustomerServiceImpl(CustomerRepository theCustomerRepository, ProductRepository theProductRepository, InvoiceRepository theInvoiceRepository)
    {
        customerRepository=theCustomerRepository;
        productRepository=theProductRepository;
        invoiceRepository=theInvoiceRepository;
    }

    @Override
    public Page<Customer> getPaginatedCustomers (Pageable pageable)
    {
        return  customerRepository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public List<Customer> findCustomersByName(String name)
    {
        return customerRepository.findAllByFirstNameOrLastName(name.replaceAll("\\s+",""));
    }

    @Override
    public void save(Customer customer)
    {
        customerRepository.save(customer);
    }

    @Override
    public Customer findById(long id)
    {
       return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(long id)
    {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Product> findByName(String search)
    {
        return productRepository.searchProducts("%"+search+"%");
    }

    @Override
    public Product findProductById(long id)
    {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveInvoice(Invoice invoice)
    {
        invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findInvoiceById(long id)
    {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteInvoice(long id)
    {
        invoiceRepository.deleteById(id);
    }

    @Override
    public Invoice fetchByIdWithCustomerWithInvoiceElementWithProduct(long id)
    {
        return invoiceRepository.fetchByIdWithCustomerWithInvoiceElementWithProduct(id);
    }

    @Override
    public Customer fetchByIdWithInvoice(long id)
    {
        return customerRepository.fetchByIdWithInvoice(id);
    }
}
