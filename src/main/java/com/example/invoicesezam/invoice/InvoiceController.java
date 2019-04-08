package com.example.invoicesezam.invoice;

import com.example.invoicesezam.customer.Customer;
import com.example.invoicesezam.invoice.Invoice;
import com.example.invoicesezam.product.Product;
import com.example.invoicesezam.customer.CustomerService;
import com.example.invoicesezam.utils.InvoiceGeneratorPdf;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("/invoice")
@Controller
public class InvoiceController
{

    private CustomerService customerService;

    public InvoiceController(CustomerService theCustomerService)
    {
        customerService=theCustomerService;
    }

    @GetMapping("/form/{customerId}")
    public String showInvoiceForm(@PathVariable(value="customerId") Long customerId, Model model, RedirectAttributes flash)
    {
        Customer customer = customerService.findById(customerId);

        if(customer == null)
        {
            flash.addFlashAttribute("error", "Klient nie istnieje");
            return "redirect:customers/1";
        } else
            {
            Invoice invoice = new Invoice();
            invoice.setCustomer(customer);
            model.addAttribute("invoice", invoice);
        }
        return "invoice/invoice-form";
    }

    @PostMapping("/save")
    public ModelAndView saveInvoice(@Valid Invoice invoice,
                                    @RequestParam(name="item_id[]", required=false) Long[] itemId,
                                    @RequestParam(name="quantity[]", required=false) Integer[] quantities,
                                    BindingResult result, RedirectAttributes redirectAttributes)
    {
        ModelAndView modelAndView = new ModelAndView();

        if(result.hasErrors())
        {
            modelAndView.setViewName("redirect:invoice/form");
            return modelAndView;
        }
        if(itemId == null || itemId.length == 0)
        {
            modelAndView.addObject("error", "Dodaj produkty do faktury!");
            modelAndView.setViewName("redirect:invoice/form");
            return modelAndView;
        }
        for(int i=0; i<itemId.length; i++)
        {
            Product product = customerService.findProductById(itemId[i]);
            InvoiceElement element = new InvoiceElement();
            element.setQuantity(quantities[i]);
            element.setProduct(product);
            invoice.addElement(element);
        }
        System.out.println(invoice.toString());
        customerService.saveInvoice(invoice);
        redirectAttributes.addFlashAttribute("success", "Faktura została dodana");
        modelAndView.setViewName("redirect:/customer/"+invoice.getCustomer().getId());

        return modelAndView;
    }

    @GetMapping(value="/products/{search}", produces={"application/json"})
    public @ResponseBody
    List<Product> loadProducts(@PathVariable String search)
    {
        return customerService.findByName(search);
    }

    @GetMapping("/delete/{invoiceId}")
    public String deleteInvoice(@PathVariable("invoiceId") long invoiceId, RedirectAttributes flash)
    {
        Invoice invoice = customerService.findInvoiceById(invoiceId);
        if (invoice != null)
        {
            customerService.deleteInvoice(invoiceId);
            flash.addFlashAttribute("success", "Faktura usunięta");
            return "redirect:/customer/" + invoice.getCustomer().getId();
        } else
            {
            flash.addFlashAttribute("error", "Faktura nie istnieje!");
            return "redirect:/customers/1";
        }
    }

    @GetMapping("/customer/{customerId}")
    public String showInvoiceDetails(@PathVariable(value="customerId") Long customerId, Model model,
                                     RedirectAttributes flash) {
        Invoice invoice = customerService.fetchByIdWithCustomerWithInvoiceElementWithProduct(customerId);
        if(invoice == null)
        {
            flash.addFlashAttribute("error", "Faktura nie istnieje");
            return "redirect:customers/1";
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("title", "Faktura: "+invoice.getDescription());
        return "invoice/invoice-details";
    }

    @GetMapping(value="/pdf/{invoiceId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> invoice(@PathVariable("invoiceId") long invoiceId) throws URISyntaxException
    {
       Invoice invoice = customerService.fetchByIdWithCustomerWithInvoiceElementWithProduct(invoiceId);
       ByteArrayInputStream bis = InvoiceGeneratorPdf.generateInvoice(invoice);

       HttpHeaders headers = new HttpHeaders();
       headers.add("Content-Disposition", "inline; filename=invoice.pdf");

       return ResponseEntity
               .ok()
               .headers(headers)
               .contentType(MediaType.APPLICATION_PDF)
               .body(new InputStreamResource(bis));
    }

}
