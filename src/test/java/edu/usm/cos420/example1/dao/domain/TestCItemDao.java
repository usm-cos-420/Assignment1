package edu.usm.cos420.example1.dao.domain;

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

import edu.usm.cos420.example1.dao.GenericDao;
import edu.usm.cos420.example1.dao.ObjectStreamDao;
import edu.usm.cos420.example1.domain.CItem;

public class TestCItemDao {

	CItemDao dao; 
	
/** 
 * Create a clean DAO before each test
 */
	@Before
	public void setupData() {
		   dao = new CItemDao("_citemTest.ser");
		   
	}
	
	@Test
    public void testSaveandFind1() {
        Long id; 
        CItem retrievedItem;
        
        CItem oneItem = new CItem(2, "a string"); 
        
        // get PK of first address
        id = oneItem.getId();
        
        dao.add(oneItem);

        retrievedItem = dao.find(id);
        
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
        dao.add(oneItem);
        id = twoItem.getId();        
        dao.add(twoItem);
        id = threeItem.getId();        
        dao.add(threeItem);

        dao.remove(twoItem.getId());
        
        retrievedItem = dao.find(twoItem.getId());
        
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
        dao.add(oneItem);
        id = twoItem.getId();        
        dao.add(twoItem);
        id = threeItem.getId();        
        dao.add(threeItem);

        // CHeck one of the three items to make sure it was stored correctly
        retrievedItem = dao.find(twoItem.getId());
        
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , twoItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , twoItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , twoItem.getMyString());

        twoItem.setMyString("A New String");
        twoItem.setMyInteger(55);
        dao.update(twoItem);
        retrievedItem = dao.find(twoItem.getId());

        // Check that the modified elements was properly stored
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , twoItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , twoItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , twoItem.getMyString());

        retrievedItem = dao.find(threeItem.getId());
        
        // check one of the other elements to make sure they are ok 
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , threeItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , threeItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , threeItem.getMyString());

        
	}

	@Test
    public void testUpdateAdd() {
        Long id; 
        CItem retrievedItem;
        
    	
        CItem oneItem = new CItem(new Long((int) (Math.random()*100000)), 1, "a string"); 
        CItem twoItem = new CItem(new Long((int) (Math.random()*100000)), 2, "a string"); 
        CItem threeItem = new CItem(new Long((int) (Math.random()*100000)), 3, "a string"); 
        
        // get PK of first address
        id = oneItem.getId();        
        dao.add(oneItem);
        id = twoItem.getId();        
        dao.add(twoItem);

        // CHeck one of the three items to make sure it was stored correctly
        retrievedItem = dao.find(twoItem.getId());
        
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , twoItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , twoItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , twoItem.getMyString());

        twoItem.setMyString("A New String");
        twoItem.setMyInteger(55);
        dao.update(twoItem);
        retrievedItem = dao.find(twoItem.getId());

        // Check that the modified elements was properly stored
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , twoItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , twoItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , twoItem.getMyString());

        id = threeItem.getId();        
        dao.add(threeItem);
        
        retrievedItem = dao.find(threeItem.getId());
        
        // check one of the other elements to make sure they are ok 
        assertNotNull("Dao returns a null item.", retrievedItem);
        assertEquals("Stored Id and original Id are not equal ", retrievedItem.getId() , threeItem.getId());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyInteger() , threeItem.getMyInteger());
        assertEquals("Stored int and original int are not equal ", retrievedItem.getMyString() , threeItem.getMyString());

	}


		
	/** 
	 * Need to delete the file for next test
	 */
	@After
	public void tearDown()
	{

		Path path1 = FileSystems.getDefault().getPath(".", "_citemTest.ser");
		try {
		    Files.delete(path1);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path1);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path1);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}
	
}
