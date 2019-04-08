package com.example.invoicesezam.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>
{
    Page<Customer> findAllByOrderByFirstNameAsc(Pageable pageable);
    Page<Customer> findAllByOrderByIdAsc(Pageable pageable);

    @Query("select c from Customer c left join fetch c.invoices i where c.id=?1")
    Customer fetchByIdWithInvoice(long id);

    @Query("select c from Customer c where c.firstName=?1 or c.lastName=?1 or concat(c.firstName, c.lastName)=?1" )
    List<Customer> findAllByFirstNameOrLastName(String firstName);

}
