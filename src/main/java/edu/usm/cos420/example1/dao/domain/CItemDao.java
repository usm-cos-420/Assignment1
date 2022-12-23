package edu.usm.cos420.example1.dao.domain;

import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import edu.usm.cos420.example1.dao.GenericDao;
import edu.usm.cos420.example1.dao.JsonDao;
import edu.usm.cos420.example1.domain.CItem;

/**
 * 
 *  A Data Access Object specifically for CItem entities 
 *     
 */
public class CItemDao
{
	private GenericDao<Long,CItem> cItemDao;

	/**
	 * Default constructor creates an Json file called citem.json
	 */
	public CItemDao()
	{
/*
     * TypeToken allows the GSON parser to map to/from JSON to objects 
	 * Gson needs help to unmarshall generics back into the correct type. 
	 * For more explanation see : 
	 *   https://github.com/google/gson/blob/master/UserGuide.md#serializing-and-deserializing-generic-types          
 */
		Type t = new TypeToken<Map<Long, CItem>>(){}.getType(); 
		cItemDao = new JsonDao<>("citem.json",t); 
	}

	/**
	 * Constructor where the filename is provided 
	 * @param filename name of the file to store CItem objects
	 */
	public CItemDao(String fileName)
	{
         Type t = new TypeToken<Map<Long, CItem>>(){}.getType(); 
		 cItemDao = new JsonDao<>(fileName,t); 
	}

	/**
	 * Support for other DAOs is provided
	 * @param dao a Data Access Object class that implements GenericDao<Long,CItem> 
	 */
	public CItemDao(GenericDao<Long,CItem> dao)
	{
		cItemDao = dao;
	}
	
	/**
	 * Returns the DAO used in the class
	 * @return a DAO that implements GenericDao<Long,CItem> 
	 */
	public GenericDao<Long,CItem> getCItemDao() {
		return cItemDao;
	}

	/**
	 * Add a CItem to the DAO repository
	 * @param entity any CItem object
	 */
	public void add(CItem entity)
	{
		cItemDao.add(entity.getId(), entity);
	}
	
	/**
	 * Update a CItem in the DAO repository
	 * @param entity any CItem object
	 */
	public void update(CItem entity) 
	{
		cItemDao.update(entity.getId(), entity);
	}
	
	/**
	 * Remove a CItem in the DAO repository
	 * @param id of the CItem object to remove
	 */

	public void remove(Long id)
	{
		cItemDao.remove(id);
	}
	
	/**
	 * Find a CItem in the DAO repository
	 * @param id of the CItem object to locate
	 * @return the CItem with id field equal to key
	 */
	public CItem find(Long key)
	{
		return cItemDao.find(key);
	}
    
	/**
	 * Generate a list of CItems in the DAO repository
	 * @return List of CItems 
	 */

	public List<CItem> list() {
		return cItemDao.list();
	}

}

