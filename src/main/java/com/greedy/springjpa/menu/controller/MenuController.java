package com.greedy.springjpa.menu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greedy.springjpa.menu.dto.CategoryDTO;
import com.greedy.springjpa.menu.dto.MenuDTO;
import com.greedy.springjpa.menu.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private MenuService menuService;
	
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	/* 메뉴 코드로 해당 메뉴 조회하기 */
	@GetMapping("/{menuCode}")
	public String findMenuByCode(@PathVariable int menuCode, Model model) {
		
		MenuDTO menu = menuService.findMenuByCode(menuCode);
//		System.out.println("menu : " + menu);
		
		model.addAttribute("menu", menu);
		
		return "menu/one";
	}
	
	/* 모든 메뉴 리스트 조회하기 */
	@GetMapping("/list")
	public String findAllMenu(Model model) {
		
		List<MenuDTO> menuList = menuService.findAllMenu();
		
		model.addAttribute("menuList", menuList);
		
		return "menu/list";
	}
	
	/* 메뉴 입력하기를 위한 화면 전환 */
	@GetMapping("/regist")
	public void registPage() {}
	
	/* 카테고리 리스트를 조회하는 비동기 통신(ajax) */
	@GetMapping(value="category", produces="application/json; charset=UTF-8")
	@ResponseBody /* 반환하는 값이 곧 응답되는 값의 바디 */
	public List<CategoryDTO> findCategoryList() {
		
		return menuService.findAllCategory();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
