package com.ohgiraffers.springdatajpa.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }
    /*1. id로 entity 조회 : findById*/
    public MenuDTO findMenuByCode(int menuCode){

        //Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);  //해당값이 null 이면 throw를 발생하겠다.  넘어온 파라미터가 잘못됐다

        return modelMapper.map(menu, MenuDTO.class);  //menu안에 담겨있는 값들을 MenuDTO에 담는다
//        MenuDTO menuDTO = new MenuDTO();
//        menuDTO.setMenuCode();
    }

    /* 2-1. 모든 entity 조회 : findAll(Sort) */
    public List<MenuDTO> findMenuList(){
       List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());  //전체 조회하고 싶으면 findALl 하면 됨
        //어떠한 속성값을 기준으로 내가 sort하고 싶은지  필드를 적어야한다. descending 내밀차순으로

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
        //menuList를 stream으로 바꿀때 menuList.stream
        //배열이 됐던 컬렉션이 됐던 여러가지 방식을 한개로 보기위해 Stream 사용?? 다시 한번 보기!!!!!!!!!!!!!!
    }
    /* 2-2. 페이징 된 entity 조회 : findAll(Pageable) */
    public Page<MenuDTO> findMenuList(Pageable pageable) {
//음수의 값이 넘어갔다면 0 으로 처리하겠다.
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }
   /* Query Method Test*/
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

        //List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
        //List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);  //오름 차순
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());  //내림 차순
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());

    }
    /* 이거 내가 해본 쿼리 메소든뎅 ... */
    public List<MenuDTO> findByMenuNameContaining(String menuName) {
        List<Menu> menuList = menuRepository.findByMenuNameContaining(menuName);
        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public MenuDTO findByMenuCodeAndMenuName(Integer menuCode, String menuName){
        Menu menu = menuRepository.findByMenuCodeAndMenuName(menuCode, menuName);
        return modelMapper.map(menu, MenuDTO.class);
    }

    public MenuDTO findByMenuCodeOrMenuName(Integer menuCode, String menuName) {
        Menu menu = menuRepository.findByMenuCodeOrMenuName(menuCode, menuName);
        return modelMapper.map(menu, MenuDTO.class);
    }
/*---------------------------------------------*/
    /* JPQL or Native Query Test */
    public List<CategoryDTO> findAllCategory(){

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    /* 5. Entity 저장 */
    @Transactional
    public void registNewMenu(MenuDTO menu) {
        menuRepository.save(modelMapper.map(menu, Menu.class));
    }

    /* 6. Entity 수정 */
    @Transactional
    public void modifyMenu(MenuDTO menu) {
        Menu foundMenu = menuRepository.findById(menu.getMenuCode()).orElseThrow(IllegalArgumentException::new);
        foundMenu.setMenuName(menu.getMenuName());
    }

    /* 7. Entity 삭제 */
    @Transactional
    public void deleteMenu(Integer menuCode) {
        menuRepository.deleteById(menuCode);
    }



}
