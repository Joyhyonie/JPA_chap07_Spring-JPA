package com.greedy.springjpa.menu.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.greedy.springjpa.menu.entity.Category;
import com.greedy.springjpa.menu.entity.Menu;

@Repository
public class MenuRepository {

	/* 메뉴 코드로 해당 메뉴 조회하기 */
	public Menu findMenuByCode(EntityManager entityManager, int menuCode) {
		
		/* find()는 테이블의 PK를 통해 해당 데이터를 가져오는 메소드이므로 여기서도 EntityManager를 활용하여 가져옴 */
		return entityManager.find(Menu.class, menuCode);
	}
	
	/* 모든 메뉴 리스트 조회하기 */
	public List<Menu> findAllMenu(EntityManager entityManager) {
		
		String jpql = "SELECT m FROM Menu AS m ORDER BY m.menuCode ASC";
		
		return entityManager.createQuery(jpql, Menu.class).getResultList();
	}
	
	/* '메뉴 등록하기'에서 필요한 카테고리 리스트 조회하기(ajax) */
	public List<Category> findAllCategory(EntityManager entityManager) {
		
		String jpql = "SELECT c FROM Category AS c ORDER BY c.categoryCode ASC";
				
		return entityManager.createQuery(jpql, Category.class).getResultList();
	}
	
	/* 메뉴 등록하기 */
	public void registNewMenu(EntityManager entityManager, Menu menu) {
		
		/* entityManager야, 영속성 컨텍스트에 menu를 저장하고 관리해주렴! */
		entityManager.persist(menu);
	}
	
	/* 메뉴 수정하기 */
	public void modifyMenu(EntityManager entityManager, Menu menu) {
		
		/* menu : 클라이언트로부터 입력받은 menuCode, menuName, menuPrice, categoryCode, orderableStatus가 넘어옴 */
		System.out.println(menu);
		
		/* 전달 받은 메뉴 정보를 통해 해당 엔티티를 먼저 조회 */
		Menu selectedMenu = entityManager.find(Menu.class, menu.getMenuCode());
		
		/* 조회된 메뉴 객체를 수정 */
		selectedMenu.setMenuName(menu.getMenuName());
		selectedMenu.setMenuPrice(menu.getMenuPrice());
		selectedMenu.setCategoryCode(menu.getCategoryCode());
		selectedMenu.setOrderableStatus(menu.getOrderableStatus());
	}
	
	/* 메뉴 삭제하기 */
	
	/* 메뉴 검색하기 */
	
	
	
	
	
	
	
	
	
	
	
}
