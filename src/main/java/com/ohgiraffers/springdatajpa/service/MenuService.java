package com.ohgiraffers.springdatajpa.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    public MenuDTO findMenuByCode(int menuCode){

        //Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);  //해당값이 null 이면 throw를 발생하겠다.  넘어온 파라미터가 잘못됐다

        return modelMapper.map(menu, MenuDTO.class);  //menu안에 담겨있는 값들을 MenuDTO에 담는다
//        MenuDTO menuDTO = new MenuDTO();
//        menuDTO.setMenuCode();
    }
}
