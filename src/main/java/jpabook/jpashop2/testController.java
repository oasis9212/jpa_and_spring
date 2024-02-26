package jpabook.jpashop2;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("test")
    public String test(Model model){
        model.addAttribute("data","test");
        return "hello";
    }
}
