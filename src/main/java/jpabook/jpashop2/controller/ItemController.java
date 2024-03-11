package jpabook.jpashop2.controller;


import jpabook.jpashop2.domain.Item.Book;
import jpabook.jpashop2.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItem(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book= new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";

    }

    @GetMapping("/items")
    public String list(Model model){

        model.addAttribute("items", itemService.fineItem());
        return "items/itemList";
    }


    @GetMapping("items/{itemId}/edit")
    public String updateItemFrom(@PathVariable("itemId") Long itemId,Model model){
        Book item= (Book) itemService.findOne(itemId);

        BookForm form= new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String update(@ModelAttribute("form")BookForm form, @PathVariable Long itemId){
        // PK 값을 담굴때 db 한번 갔다와서 있는 데이터인지 알수있을것이다. (준영속 상태)
        // 영속성 관리를 하지않는다.
        // 컨트롤러에서 엔티티 만들어서 짜지말자.
         /* Book book=new Book();   예시.
                book.setId(form.getId());
                book.setName(form.getName());
                book.setPrice(form.getPrice());
                book.setStockQuantity(form.getStockQuantity());
                book.setAuthor(form.getAuthor());
                book.setIsbn(form.getIsbn());*/
        itemService.updateItem(itemId, form.getName(), form.getPrice(),form.getStockQuantity());
        return "redirect:/items";
        
    }
}
