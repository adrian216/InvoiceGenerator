package com.example.invoicesezam.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>
{
    @Query("select i from Invoice i join fetch i.customer c join fetch i.invoiceElementSet l join fetch l.product where i.id=?1")
    Invoice fetchByIdWithCustomerWithInvoiceElementWithProduct(Long id);
}
