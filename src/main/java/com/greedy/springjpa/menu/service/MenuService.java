package com.greedy.springjpa.menu.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.springjpa.menu.dto.MenuDTO;
import com.greedy.springjpa.menu.entity.Menu;
import com.greedy.springjpa.menu.repository.MenuRepository;

@Service
public class MenuService {

	private MenuRepository menuRepository;	// MenuRepository를 참조하기 위해 필드 선언 및 생성자를 통한 의존성 주입
	private ModelMapper modelMapper;		// ModelMapper를 참조하기 위해 필드 선언 및 생성자를 통한 의존성 주입
	
	@PersistenceContext // 스프링 부트가 영속성 컨텍스트를 관리하는 엔티티 매니저를 주입하는 어노테이션
	private EntityManager entityManager;
	
	public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 영속성 객체(엔티티)를 그대로 사용하면 데이터가 훼손 될 가능성이 있으므로 비영속 객체로 변경하여 반환 */
	/* MenuDTO : 엔티티가 아닌 데이터를 묶어서 보낼 수 있는 객체 */
	/* 메뉴 코드로 해당 메뉴 조회하기 */
	public MenuDTO findMenuByCode(int menuCode) {
		
		/* Menu -> MenuDTO(비영속 객체)로 변환할 수 있는 ModelMapper 라이브러리 의존성 추가 후 사용 */
		return modelMapper.map(menuRepository.findMenuByCode(entityManager, menuCode), MenuDTO.class);
		// modelMapper.map(변환하고자하는 대상의 엔티티 객체, 변환하고자하는 타입);
	}
	
	/* 모든 메뉴 리스트 조회하기 */
	public List<MenuDTO> findAllMenu() {
		
		/* List<Menu> Menu 객체가 여러 개 있으므로 Stream화 시켜 MenuDTO*/
		List<Menu> menuList = menuRepository.findAllMenu(entityManager);
		
		return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
		/* menu : Menu 엔티티 객체들
		 * stream의 map()은 괄호 안의 동작을 수행하고 나서 가공된 결과를 반환 
		 * collect(Collectors.toList() : 다시 stream을 List 형태로 변환 */
		
		/* 
		 * [코드 해석]
		 * 1. menuList라는 List<Menu> 타입을 stream화 하여 map() 사용
		 * 2. menu라는 Menu 엔티티 객체들을 modelMapper의 map()메소드를 통해 MenuDTO 타입으로 변환
		 * 3. 다시 stream을 List형태로 변환
		 */
	}
}
