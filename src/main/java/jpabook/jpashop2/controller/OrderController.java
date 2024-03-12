package jpabook.jpashop2.controller;

import jpabook.jpashop2.domain.Item.Item;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.Orders;
import jpabook.jpashop2.repository.OrderSearch;
import jpabook.jpashop2.service.ItemService;
import jpabook.jpashop2.service.MemberService;
import jpabook.jpashop2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members= memberService.findmemeber();
        List<Item> items= itemService.fineItem();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String create(@RequestParam("memberId") Long memberId,
                         @RequestParam("itemId") Long itemId,
                         @RequestParam("count") int count){

       orderService.Order(memberId,itemId,count);


        return "redirect:orders";
    }

    @GetMapping("/orders")
    public String orderlist(@ModelAttribute("orderSearch")OrderSearch orderSearch,Model model){
         List<Orders> orders=  orderService.findOrder(orderSearch);
         model.addAttribute("orders",orders);
         return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId")Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:orders";
    }
}
