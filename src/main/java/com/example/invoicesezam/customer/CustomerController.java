package com.example.invoicesezam.customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CustomerController
{

    private static final int lastPage=5;

    private CustomerService customerService;

    private CustomerController(CustomerService theCustomerService)
    {
        customerService=theCustomerService;
    }


    @GetMapping({"/", ""})
    public String homeMapping()
    {
        return "redirect:customers/1";
    }

    @GetMapping("/customers/{page}")
    public ModelAndView customersList(@PathVariable(name="page") int page)
    {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/customers-list");

        PageRequest pageable = PageRequest.of(page - 1, lastPage);
        Page<Customer> customerPage = customerService.getPaginatedCustomers(pageable);
        int totalPages = customerPage.getTotalPages();
        if(totalPages > 0)
        {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeCustomerList", true);
        modelAndView.addObject("customerList", customerPage.getContent());

        return modelAndView;
    }


    @RequestMapping("/form")
    public ModelAndView showCustomerForm()
    {
        ModelAndView modelAndView = new ModelAndView("customer/customer-form");
        Customer customer = new Customer();
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes flash)
    {
        customer.setCreatedAt(new Date());
        customerService.save(customer);
        flash.addFlashAttribute("success", "Klient zostal dodany");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:customers/1");

        return modelAndView;
    }

    @RequestMapping("/delete")
    public String deleteCustomer(@RequestParam(value="customerId") long id, RedirectAttributes flash)
    {
        if(id > 0)
        {
            customerService.delete(id);
            flash.addFlashAttribute("success", "Klient został usunięty");
        }
        return "redirect:customers/1";
    }

    @GetMapping("/updateForm")
    public ModelAndView showFormForUpdate(@RequestParam("customerId") long theId)
    {
        ModelAndView modelAndView = new ModelAndView();

        Customer theCustomer = customerService.findById(theId);
        modelAndView.addObject("customer", theCustomer);
        modelAndView.setViewName("customer/customer-form");
        return modelAndView;
    }

    @GetMapping(value="/customer/{id}")
    public String customerDetails(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash)
    {
        Customer customer = customerService.fetchByIdWithInvoice(id);
        if(customer == null)
        {
            flash.addFlashAttribute("error", "Klient nie istnieje");
            return "redirect:customers/1";
        } else
            {
            model.put("customer", customer);
            model.put("title", "Szczegóły klienta - " + customer.getName());
        }
        return "customer/customer-details";
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(value = "search", required = false) String name, RedirectAttributes flash)
    {
        ModelAndView modelAndView = new ModelAndView("customer/customers-list");
        List<Customer> searchResults = searchResults = customerService.findCustomersByName(name);
        if (searchResults.isEmpty())
            flash.addFlashAttribute("error", "Brak klientów o podanym imieniu");

        modelAndView.addObject("customerList", searchResults);
        return modelAndView;

    }
}
