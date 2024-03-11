package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Item.Book;
import jpabook.jpashop2.domain.Item.Item;
import jpabook.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    @Transactional
    public void updateItem(Long itemId,String name,int price, int StockQuantity){
       Item item=  itemRepository.findOne(itemId);
       item.setPrice(price);
       item.setName(name);
       item.setStockQuantity(StockQuantity);

    }

    public List<Item> fineItem(){
        return  itemRepository.findAll();
    }

    public Item findOne(Long itemID){
        return  itemRepository.findOne(itemID);
    }
}
