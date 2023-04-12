package com.greedy.springjpa.menu.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.springjpa.menu.dto.MenuDTO;
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
	public MenuDTO findMenuByCode(int menuCode) {
		
		/* Menu -> MenuDTO(비영속 객체)로 변환할 수 있는 ModelMapper 라이브러리 의존성 추가 후 사용 */
		return modelMapper.map(menuRepository.findMenuByCode(entityManager, menuCode), MenuDTO.class);
		// modelMapper.map(변환하고자하는 대상의 엔티티 객체, 변환하고자하는 타입);
	}
	
}
