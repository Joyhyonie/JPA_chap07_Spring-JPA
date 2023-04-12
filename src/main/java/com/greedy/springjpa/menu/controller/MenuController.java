package com.greedy.springjpa.menu.controller;

import java.util.Collections; 
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	/* '메뉴 등록하기'를 위한 화면 전환 */
	@GetMapping("/regist")
	public void registPage() {}
	
	/* 카테고리 리스트를 조회하는 비동기 통신(ajax) */
	@GetMapping(value="category", produces="application/json; charset=UTF-8")
	@ResponseBody /* 반환하는 값이 곧 응답되는 값의 바디 */
	public List<CategoryDTO> findCategoryList() {
		
		return menuService.findAllCategory();
	}
	
	/* 메뉴 등록하기 */
	@PostMapping("/regist")
	public String registNewMenu(@ModelAttribute MenuDTO newMenu, RedirectAttributes rttr) { /* @ModelAttribute는 생략되어도 정상 동작하지만 명시 */
		
		menuService.registNewMenu(newMenu);
		
		rttr.addFlashAttribute("message", "메뉴 등록 성공! 등록된 메뉴를 확인하세요 🥳");
		
		return "redirect:/menu/list#menu-regist";
	}
	
	/* 메뉴 수정하기 */
	@GetMapping("/modify")
	public void modifyPage() {}
	
	@PostMapping("/modify")
	public String menuModify(@ModelAttribute MenuDTO menu, RedirectAttributes rttr) {
		
		menuService.modifyMenu(menu);
		
		rttr.addFlashAttribute("message", "메뉴 수정 성공! 수정된 메뉴를 확인하세요 😍");
		
		/* 수정된 메뉴의 상세페이지로 이동 */
		return "redirect:/menu/" + menu.getMenuCode() + "#menu-modify";
	}
	
	/* 메뉴 삭제하기 */
	@GetMapping("/remove")
	public void removePage() {}
	
	/* 메뉴 리스트를 조회하는 비동기 통신(ajax) */
	@GetMapping(value="menu", produces="application/json; charset=UTF-8")
	@ResponseBody /* 반환하는 값이 곧 응답되는 값의 바디 */
	public List<MenuDTO> findMenuList() {
		
		return menuService.findAllMenu();
	}
	
	@PostMapping("/remove")
	public String menuRemove(@ModelAttribute MenuDTO menu, RedirectAttributes rttr) {
		
		menuService.removeMenu(menu);
		
		rttr.addFlashAttribute("message", "메뉴 삭제 성공! 👋");
		
		return "redirect:/menu/list#menu-remove";
	}
	
	/* 메뉴 검색하기 */
	@GetMapping("/search")
	public String searchedPage(@RequestParam(value="keyword", required=false) String keyword, Model model) {
		
		List<MenuDTO> menuList = menuService.searchMenu(keyword);
		
		model.addAttribute("menuList", menuList);
		
		return "/menu/list";
	}
	
	/* 오늘의 메뉴 추천받기 */
	@GetMapping("/recommend")
	public String recommendPage(Model model) {
		
		List<MenuDTO> menuList = menuService.findAllMenu();
		
		Collections.shuffle(menuList); // shuffle() 메소드로 menuList 안에 있는 값 랜덤으로 재배치
		List<MenuDTO> recommendedMenulist = menuList.subList(0, 5); // subList() 메소드로 0부터 4번 인덱스까지만 추출
		
		model.addAttribute("menuList", recommendedMenulist);
		
		return "/menu/list";
	}

}
