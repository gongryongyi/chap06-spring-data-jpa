package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {  //<내가 컨트롤하고싶은 대상 엔터티, 엔터티의 ID(pk) -> menuCode 데이터 타입 기재 해야함>

}
