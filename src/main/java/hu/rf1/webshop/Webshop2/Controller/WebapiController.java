package hu.rf1.webshop.Webshop2.Controller;

import hu.rf1.webshop.Webshop2.Model.Employee;
import hu.rf1.webshop.Webshop2.Repository.WebapiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebapiController {

    @Autowired
    WebapiRepository repository;

    @RequestMapping("/")
    public String indexpage(Model model){
        List<Employee> emp=repository.findAll();
        model.addAttribute("emplist", emp);
        return "index";
    }

    @RequestMapping("/new")
    public String newFormforEmployee(Model model){
        Employee emp = new Employee();
        model.addAttribute("employee", emp);
        return "new_emp";
    }

    @PostMapping("/add")
    public String addemployee(Employee emp){
        repository.save(emp);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editform(@PathVariable("id")long id){
        ModelAndView mv = new ModelAndView("edit_emp");
        Employee emp= repository.findById(id).orElse(null);
        mv.addObject("employee", emp);
        return mv;
    }

    @RequestMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id")long id){
        repository.deleteById(id);
        return "redirect:/";
    }
}
