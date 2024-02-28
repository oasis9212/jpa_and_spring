package jpabook.jpashop2.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("M")  // dtype 의 칼럼 이름 명
public class Movie  extends  Item {

    private String director;
    private String actor;
}
