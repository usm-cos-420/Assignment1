package edu.usm.cos420.example1.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.usm.cos420.example1.domain.CItem;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class TestJsonDao 
{
	JsonDao<Long, CItem> dao1; 
	JsonDao<String, String> dao2; 
	
/** 
 * Create a clean DAO before each test
 */
	@Before
	public void setupData() {
	    Type t1 = new TypeToken<Map<Long, CItem>>(){}.getType(); 
	    Type t2 = new TypeToken<Map<String, String>>(){}.getType(); 

		dao1 = new JsonDao<Long, CItem>("_test1.json",t1);
		dao2 = new JsonDao<String, String>("_test2.json",t2);
	}
	
	@Test
    public void testSaveandFind1() {
        Long id; 
        CItem retrievedItem;
        
        id = 1L;
        CItem oneItem = new CItem(id, 2, "a string"); 
        
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
        
    	
        CItem oneItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 3, "a string"); 
        
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
        
    	
        CItem oneItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 3, "a string"); 
        
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
		dao2.add("2", "Test 1");

        String retrievedItem = (String) dao2.find("1");
        
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored string and original String are not equal ", retrievedItem, "Test 1");
        
	}

	@Test
    public void testCountObjectsInFile() {
        Long id; 
        
        id = 1L;
        CItem oneItem = new CItem(id, 1, "a string"); 
        id = 2L;
        CItem twoItem = new CItem(id, 2, "a string"); 
        id = 3L;
        CItem threeItem = new CItem(id, 3, "a string"); 
        
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
	    Type t1 = new TypeToken<Map<Long, CItem>>(){}.getType(); 

 	    JsonDao<Long,CItem> pdao = new JsonDao<Long, CItem>("_ptest.json",t1);
        CItem oneItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(Long.valueOf((int) (Math.random()*100000)), 3, "a string"); 

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
    
		Path path3 = FileSystems.getDefault().getPath(".", "_ptest.json");
		try {
		    Files.delete(path3);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path3 );
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path3);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}

	}
	
	/** 
	 * Need to delete the file for next test
	 */
	@After
	public void tearDown()
	{

/*
 *   This will be run after every test ... overly ambitious
 */

		Path path1 = FileSystems.getDefault().getPath(".", "_test1.json");
		Path path2 = FileSystems.getDefault().getPath(".", "_test2.json");
		try {
		    Files.delete(path1);
		    Files.delete(path2);
		} catch (NoSuchFileException x) {
		} catch (DirectoryNotEmptyException x) {
		} catch (IOException x) {
		}
	}
	
}
