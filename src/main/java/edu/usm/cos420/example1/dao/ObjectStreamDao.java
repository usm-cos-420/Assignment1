package edu.usm.cos420.example1.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.usm.cos420.example1.dao.util.AppendingObjectOutputStream;
import edu.usm.cos420.example1.exceptions.DaoException;

/**
 * 
 * A Data Access Class that persists objects using ObjectOutputStream class in the Java io package
 *
 * @param <IDType> the primary key type
 * @param <T>  the entity type
 */
public class ObjectStreamDao<IDType, T extends Serializable> implements GenericDao<IDType, T>  {
	
// collection class to read entities in & out of persistent storage  
	private Map<IDType,T> entityMap = new HashMap<IDType,T>();
// manages the persistence of objects to the file system 	
    private ObjectOutputStream oos;
// filename associated with the ObjectStream
    private String fileName;
    
    /**
     * Default Constructor creates an ObjectStream associated with the filename "noname.ser" 
     */
    public ObjectStreamDao()
	{
		this("noname.ser");
	}
	
    /**
     *  Constructor to create new ObjectStream
     * @param fileName name of the file to be written to, filename is wrt current project directory
     */
	public ObjectStreamDao(String fileName) 
	{
        this.fileName = fileName;
        File file = new File(fileName);

		try {
			// if file doesn't exists, then create it
			if (!file.exists()) {
				FileOutputStream fos = new FileOutputStream(fileName);
				oos = new ObjectOutputStream(fos);
			} else {
				FileOutputStream fos = new FileOutputStream(file, true);
				oos = new AppendingObjectOutputStream(fos);
			    entityMap = readStreamIntoMap();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not find " + fileName + e);
		} catch (IOException e) {
			System.err.println("Some Error writing to stream " + e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	/**
	 *  Add an entity to the ObjectStream 
	 *  @param id  id of the entity to be added with Type IDType
	 *  @param entity object of Type T to be added to the collection  
	 */
	public void add(IDType id, T entity) 
	{
	      entityMap.put(id,entity);
	      writeMapIntoStream(entityMap);
    }

	/**
	 *  Update an entity in the ObjectStream  
	 *  @param id  id of the entity to located in the collection
	 *  @param entity new object of Type T to replace existing item  
	 */
	public void update(IDType id, T entity) 
	{
		if (entityMap.get(id) == null) 
				throw new DaoException("attempting to update non-existent entity ");
		entityMap.put(id,entity);
		writeMapIntoStream(entityMap);
	}

	/**
	 *  Remove an entity from the ObjectStream 
	 *  @param id  id of the entity to be removed with Type IDType
	 */

	public void remove(IDType id) 
	{
		entityMap.remove(id);
		writeMapIntoStream(entityMap);
	}

	/**
	 *  Find an entity in the ObjectStream
	 *  @param key  id of the entity to be located in the persisted collection with Type IDType
	 *  @return entity object of Type T that has the id field equal to key  
	 */
	
	public T find(IDType key) {
	    entityMap = readStreamIntoMap();
		if (entityMap.isEmpty()) {
			return null;
		}
		return entityMap.get(key);
	}

	/**
	 *  List all entities in the persisted collection 
	 *  @return List of all entity objects of Type T in the collection  
	 */
	
	public List<T> list() {
	    entityMap = readStreamIntoMap();
    	    return new ArrayList<T> (entityMap.values());    		                     
	}

	/**
	 * Calculates the number of Objects currently in the Object Stream
	 * @return number of Objects currently in the Object Stream persistent storage 
	 */
	public int determineNumberOfObjectsInStream() 
	    	{
	    		int tmpCount = 0;
	            File file = new File(fileName);
    			if (!file.exists()) 
    			{
    	    		    return 0;
    			}

	        	try {
	        		FileInputStream fis = new FileInputStream(file);
	        		ObjectInputStream ois = new ObjectInputStream(fis);
	        		while (fis.available() > 0)
	        		{
	        			IDType id = (IDType) ois.readObject();
	                    T entity = (T) ois.readObject();
	        			tmpCount++;
	        		}
	        		ois.close();
	        	}   		
	        	catch (FileNotFoundException e) {
	        		System.err.println("Could not find " + fileName + "  "+ e);   	
	        	}
	        	catch (EOFException e) {
	        	}
	        	catch (IOException e) {
	        		System.err.println("Some Error writing to stream " + e);   	
	        	}
	        	catch (Exception e){
	        		System.err.println(e);
	        	}	

	        	return tmpCount;    		                     
		}

// utility routine to read the entire object stream into a map
	
	private Map<IDType, T> readStreamIntoMap() {
    	entityMap.clear();

    	try {
    		FileInputStream fis = new FileInputStream(fileName);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		while (fis.available() > 0)
    		{
    			IDType id = (IDType) ois.readObject();
            T entity = (T) ois.readObject();
    			entityMap.put(id, entity);	
    		}
    		ois.close();
    	}   		
    	catch (FileNotFoundException e) {
    		System.err.println("Could not find " + fileName + "  "+ e);   	
    	}
    	catch (EOFException e) {
    	}
    	catch (IOException e) {
    		System.err.println("Some Error writing to stream " + e);   	
    	}
    	catch (Exception e){
    		System.err.println(e);
    	}	

    	return entityMap;    		                     
	}

	// utility routine to write the entire object stream into a map

	private void writeMapIntoStream(Map<IDType, T> map) 
	{
    	try {
    		oos.close();
    	}
    	catch (FileNotFoundException e) {
    		System.err.println("Could not find test.ser " + e);   	
    	}
    	catch (IOException e) {
    		System.err.println("Some Error writing to stream " + e);   	
    	}
    	catch (Exception e){
    		System.err.println(e);
    	}	

    	try {
    		FileOutputStream fos = new FileOutputStream(fileName);
    		oos = new ObjectOutputStream(fos);
    		for (Map.Entry<IDType, T> entry : map.entrySet())
    		{
        		oos.writeObject(entry.getKey());
        		oos.writeObject(entry.getValue());
    		}   
        oos.close();
    	}   		
    	catch (FileNotFoundException e) {
    		System.err.println("Could not find " + fileName + "  "+ e);   	
    	}
    	catch (IOException e) {
    		System.err.println("Some Error writing to stream " + e);   	
    	}
    	catch (Exception e){
    		System.err.println(e);
    	}	
	}
	

	

}
