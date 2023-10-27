package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {  //<내가 컨트롤하고싶은 대상 엔터티, 엔터티의 ID(pk)의 타입 -> menuCode 데이터 타입 기재 해야함>

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);   //MenuPrice = menuPrice

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 오름차순으로 조회
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);  //findBy 뒤에 내가 원하는 속성
   /* contain*/
    List<Menu> findByMenuNameContaining(String menuName);
    // 전달 받은 menuCode와 menuName으로 조회
    Menu findByMenuCodeAndMenuName(Integer menuCode, String menuName);
    Menu findByMenuCodeOrMenuName(Integer menuCode, String menuName);
    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 내림차순으로 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);



}
