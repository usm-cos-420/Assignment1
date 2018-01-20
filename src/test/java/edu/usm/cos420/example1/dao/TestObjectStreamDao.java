package edu.usm.cos420.example1.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.usm.cos420.example1.dao.ObjectStreamDao;
import edu.usm.cos420.example1.domain.CItem;

public class TestObjectStreamDao {

	ObjectStreamDao<Long, CItem> dao1; 
	ObjectStreamDao<String, String> dao2; 
	
/** 
 * Create a clean DAO before each test
 */
	@Before
	public void setupData() {
		   dao1 = new ObjectStreamDao<Long, CItem>("_test1.ser");
		   dao2 = new ObjectStreamDao<String, String>("_test2.ser");
	}
	
	@Test
    public void testSaveandFind1() {
        Long id; 
        CItem retrievedItem;
        
        CItem oneItem = new CItem(2, "a string"); 
        
        // get PK of first address
        id = oneItem.getId();
        
        dao1.add(id, oneItem);

        retrievedItem = (CItem) dao1.find(id);
        
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , oneItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , oneItem.getMyInteger());
        
	}

	@Test
    public void testSaveandRemove1() {
        Long id; 
        CItem retrievedItem;
        
    	
        CItem oneItem = new CItem(new Long((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(new Long((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(new Long((int) (Math.random()*100000)), 3, "a string"); 
        
        // get PK of first address
        id = oneItem.getId();        
        dao1.add(id, oneItem);
        id = twoItem.getId();        
        dao1.add(id, twoItem);
        id = threeItem.getId();        
        dao1.add(id, threeItem);

        dao1.remove(twoItem.getId());
        
        retrievedItem = (CItem) dao1.find(twoItem.getId());
        
        assertNull("Dao returns a null item.", retrievedItem);
        
	}

	@Test
    public void testSaveandUpdate1() {
        Long id; 
        CItem retrievedItem;
        
    	
        CItem oneItem = new CItem(new Long((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(new Long((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(new Long((int) (Math.random()*100000)), 3, "a string"); 
        
        // get PK of first address
        id = oneItem.getId();        
        dao1.add(id, oneItem);
        id = twoItem.getId();        
        dao1.add(id, twoItem);
        id = threeItem.getId();        
        dao1.add(id, threeItem);

        // CHeck one of the three items to make sure it was stored correctly
        retrievedItem = (CItem) dao1.find(twoItem.getId());
        
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , twoItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , twoItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , twoItem.getMyString());

        twoItem.setMyString("A New String");
        twoItem.setMyInteger(55);
        dao1.update(twoItem.getId(),twoItem);
        retrievedItem = (CItem) dao1.find(twoItem.getId());

        // Check that the modified elements was properly stored
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , twoItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , twoItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , twoItem.getMyString());

        retrievedItem = (CItem) dao1.find(threeItem.getId());
        
        // check one of the other elements to make sure they are ok 
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , threeItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , threeItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , threeItem.getMyString());

        
	}

	@Test
    public void testSaveandFind2() {

		dao2.add("1", "Test 1");

        String retrievedItem = (String) dao2.find("1");
        
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored string and original String are not equal ", retrievedItem, "Test 1");
        
	}

	@Test
    public void testCountObjectsInFile() {
        Long id; 
        
        CItem oneItem = new CItem(1, "a string"); 
        CItem twoItem = new CItem(2, "a string"); 
        CItem threeItem = new CItem(3, "a string"); 
        
        // get PK of first address
        id = oneItem.getId();      
        dao1.add(id, oneItem);
        id = twoItem.getId();      
        dao1.add(id, twoItem);
        id = threeItem.getId();      
        dao1.add(id, threeItem);

        int retrievedCount = dao1.determineNumberOfObjectsInStream();
        
        assertEquals("Stored object counts are not equal ", retrievedCount, 3);
        
	}

	@Test
    public void testPersistenceAcrossTests() {
        Long id; 
        
 	    ObjectStreamDao<Long,CItem> pdao = new ObjectStreamDao<Long, CItem>("_ptest.ser");
        CItem oneItem = new CItem(new Long((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(new Long((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(new Long((int) (Math.random()*100000)), 3, "a string"); 

        int initialCount = pdao.determineNumberOfObjectsInStream();

        // get PK of first address
        id = oneItem.getId();        
        pdao.add(id, oneItem);
        id = twoItem.getId();        
        pdao.add(id, twoItem);
        id = threeItem.getId();        
        pdao.add(id, threeItem);

        int updatedCount = pdao.determineNumberOfObjectsInStream();

        assertEquals("Stored object counts are not equal after updating file", initialCount + 3, updatedCount);
    
	}
	
	/** 
	 * Need to delete the file for next test
	 */
	@After
	public void tearDown()
	{

/*
 * Run after every test ... overly ambitious		
 */
		
		Path path1 = FileSystems.getDefault().getPath(".", "_test1.ser");
		Path path2 = FileSystems.getDefault().getPath(".", "_test2.ser");
		Path path3 = FileSystems.getDefault().getPath(".", "_ptest.ser");
		try {
		    Files.delete(path1);
		    Files.delete(path2);
		    Files.delete(path3);
		} catch (NoSuchFileException x) {
		} catch (DirectoryNotEmptyException x) {
		} catch (IOException x) {
		}
	}
	
}
