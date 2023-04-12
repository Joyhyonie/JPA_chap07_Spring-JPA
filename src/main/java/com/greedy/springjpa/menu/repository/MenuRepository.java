package com.greedy.springjpa.menu.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.greedy.springjpa.menu.entity.Menu;

@Repository
public class MenuRepository {

	public Menu findMenuByCode(EntityManager entityManager, int menuCode) {
		
		return entityManager.find(Menu.class, menuCode);
	}
}
