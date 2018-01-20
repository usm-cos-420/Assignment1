package edu.usm.cos420.example1.service.impl;

import java.util.Iterator;
import java.util.List;

import edu.usm.cos420.example1.dao.domain.CItemDao;
import edu.usm.cos420.example1.domain.CItem;
import edu.usm.cos420.example1.service.ExampleService;

/**
 * 
 * The Example1 Service Layer Implementation is based a design pattern which
 * aims to organize the functionality of the application into logical units that
 * are typically layered on top of much of the low level functionality of the
 * application. This organization helps support service oriented architectures.
 *
 */
public class Example1Service implements ExampleService {

	CItemDao dao;

	/**
	 * Default Constructor creates a default CItemDao object
	 */
	public Example1Service() {
		this.dao = new CItemDao();
	}

	/**
	 * Constructor with the DAO provided
	 * 
	 * @param dao
	 *            Data Access Object to use in the service
	 */
	public Example1Service(CItemDao dao) {
		this.dao = dao;
	}

	/**
	 * Add a randomly generated CItem element to the repository
	 */
	public void addACItem() 
	{
		Long id = this.maxCItemId() + 1;
		int randomNum = 1 + (int) (Math.random() * 100000);
		CItem anItem = new CItem(id, randomNum, "String with random number " + randomNum);
		dao.add(anItem);
	}

	public List<CItem> getAll()
	{
		return dao.list();
	}
	
	/**
	 * Calculate the maximum ID value of elements in the repository
	 * 
	 * @return the maximum id of a CItem in the repository
	 */

	public Long maxCItemId() {
		List<CItem> clist = dao.list();
		Long max = 0L;
		if (clist.isEmpty())
			return max;
		else {
		Iterator<CItem> iter = clist.iterator();
   		 max = iter.next().getId();
   		 while (iter.hasNext())
   		 {
   			 CItem anItem = iter.next();
   			 if (anItem.getId() > max)
   			     max = anItem.getId();
   		 }
   		 return max;	
		}
	}
}
