package com.greedy.springjpa.menu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	/* 메뉴 입력하기 */
	@PostMapping("/regist")
	public String registNewMenu(@ModelAttribute MenuDTO newMenu) { /* @ModelAttribute는 생략되어도 정상 동작하지만 명시 */
		
		menuService.registNewMenu(newMenu);
		
		return "redirect:/menu/list";
	}
	
	/* '메뉴 수정하기'를 위한 화면 전환 */
	@GetMapping("/modify")
	public void modifyPage() {}
	
	/* 메뉴명 수정하기 */
	@PostMapping("/modify")
	public String menuModify(@ModelAttribute MenuDTO menu) {
		
		menuService.modifyMenu(menu);
		
		/* 수정된 메뉴의 상세페이지로 이동 */
		return "redirect:/menu/" + menu.getMenuCode();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
