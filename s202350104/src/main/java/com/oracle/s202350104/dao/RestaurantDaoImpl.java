package com.oracle.s202350104.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.s202350104.model.CommonCodes;
import com.oracle.s202350104.model.Restaurants;
import com.oracle.s202350104.model.RestaurantsContent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RestaurantDaoImpl implements RestaurantDao {
	
	private final SqlSession session;
	private final PlatformTransactionManager transactionManager;

	@Override
	public List<RestaurantsContent> listRestaurant(RestaurantsContent restaurant) {
		List<RestaurantsContent> restaurantList = null;
		log.info("RestaurantImpl listRestaurant Start...");
		
		try {
			log.info("RestaurantDaoImpl listRestaurant ");
			restaurantList = session.selectList("joRestaurantListAll",restaurant);
			log.info("RestaurantDaoImpl listRestaurant restaurantList.size()->" + restaurantList.size());
		} catch (Exception e) {
			log.info("RestaurantDaoImpl listRestaurant e.getMessage()->" + e.getMessage());
		}
		return restaurantList;
	}

	@Override
	public RestaurantsContent detailRestaurant(int contentId) {
		RestaurantsContent restaurant = new RestaurantsContent();
		
		try {
			restaurant = session.selectOne("joRestaurantDetail", contentId);
			log.info("RestaurantsContent detailRestaurant() restaurant.getTitle ->" + restaurant.getTitle());
					
		} catch (Exception e) {
			log.info("RestaurantsContent detailRestaurant() ->" + e.getMessage());
		}
		return restaurant;
	}

	@Override
	public int totalRestaurant() {
		int totalRestaurant = 0;
		try {
			log.info("RestaurantDaoImpl totalRestaurant");
			totalRestaurant = session.selectOne("joRestaurantTotal");
		} catch (Exception e) {
			log.info("RestaurantDaoImpl totalRestaurant() Exception ->" + e.getMessage());
		}
		
		return totalRestaurant;
	}

	@Override
	public int conTotalRestaurant(RestaurantsContent restaurant) {
		int conTotalRestaurant = 0;
		try {
			conTotalRestaurant = session.selectOne("joConTotalRestaurant", restaurant);
		} catch (Exception e) {
			log.info("RestaurantDaoImpl joConTotalRestaurant() Exception ->" + e.getMessage());
		}
				
		return conTotalRestaurant;
	}

	@Override
	public List<RestaurantsContent> listSearchRestaurant(RestaurantsContent restaurant) {
		List<RestaurantsContent> listSearchRestaurant = null;
		try {
			listSearchRestaurant = session.selectList("joListSearchRestaurant", restaurant);
		} catch (Exception e) {
			 log.info("RestaurantDaoImpl joListSearchRestaurant() Exception ->" + e.getMessage());
		}
		return listSearchRestaurant;
	}

	@Override
	public List<RestaurantsContent> listRestaurant() {
		List<RestaurantsContent> listRestaurant = null;
		try {
			listRestaurant = session.selectList("RestaurantListAll");
			log.info("listRestaurant.size" + listRestaurant.size());
		} catch(Exception e) {
			log.info("{}",e.getMessage());
		}
		return listRestaurant;
	}

	@Override
	public int adminConTotalRestaurant(RestaurantsContent restaurant) {
		int adminConTotalRestaurant = 0 ;
		try {
			adminConTotalRestaurant = session.selectOne("joAdminConTotalRestaurant", restaurant);
			log.info("RestaurantDaoImpl joAdminConTotalRestaurant -> " + adminConTotalRestaurant);
				
		} catch (Exception e) {
			log.info("RestaurantDaoImpl joAdminConTotalRestaurant() Exception ->" + e.getMessage());
		}
		
		return adminConTotalRestaurant;
	}

	@Override
	public List<RestaurantsContent> adminListSearchRestaurant(RestaurantsContent restaurant) {
		List<RestaurantsContent> adminListSearchRestaurant = null;
		
		try {
			adminListSearchRestaurant = session.selectList("joAdminListSearchRestaurant", restaurant);
			log.info("RestaurantDaoImpl joAdminListSearchRestaurant.size() -> " + adminListSearchRestaurant.size()); 
		} catch (Exception e) {
			log.info("RestaurantDaoImpl joAdminListSearchRestaurant() Exception ->" + e.getMessage());
		}
		
		return adminListSearchRestaurant;
	}

	@Override
	public int insertRestaurant(RestaurantsContent restaurant) {
		int result = 0;
		TransactionStatus txStatus = 
						transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			log.info("RestaurantDaoImpl insertRestaurant start");
			result = session.insert("joInsertRestaurant", restaurant);
			log.info("RestaurantDaoImpl joInsertRestaurant ->" + result);
			result = session.insert("joInsertContent", result);
			log.info("RestaurantDaoImpl joInsertContent ->" + result);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			log.info("RestaurantDaoImpl insertRestaurant Exception" + e.getMessage()); 
			result = -1;
		}
		return result;
	
	}

}
