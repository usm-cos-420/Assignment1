package edu.usm.cos420.example1.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Data Access Interface that defines characteristics of basic CRUD operations
 *
 * @param <T> the entity type
 * @param <ID> the primary key type
 */
public interface GenericDao<IDType, T extends Serializable> {

	/**
	 *  Add an entity to the persisted collection 
	 *  @param id  id of the entity to be added with Type IDType
	 *  @param entity object of Type T to be added to the collection  
	 */
	void add(IDType id, T entity);
	
	/**
	 *  Update an entity in the persisted collection 
	 *  @param id  id of the entity to located in the collection
	 *  @param entity new object of Type T to replace existing item  
	 */
	void update(IDType id, T entity);
	
	/**
	 *  Remove an entity in the persisted collection 
	 *  @param id  id of the entity to be removed with Type IDType
	 */
	void remove(IDType id);
	
	/**
	 *  Find an entity to the persisted collection 
	 *  @param key  id of the entity to be located in the persisted collection with Type IDType
	 *  @return entity object of Type T that has the id field equal to key  
	 */
	T find(IDType key);
    
	/**
	 *  List all entities in the persisted collection 
	 *  @return List of all entity objects of Type T in the collection  
	 */
	List<T> list();
}
