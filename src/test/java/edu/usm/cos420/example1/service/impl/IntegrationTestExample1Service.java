package edu.usm.cos420.example1.service.impl;

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
import edu.usm.cos420.example1.dao.domain.CItemDao;
import edu.usm.cos420.example1.domain.CItem;
import edu.usm.cos420.example1.service.ExampleService;

public class IntegrationTestExample1Service {
	GenericDao<Long, CItem> dao;
	CItemDao citemDao;
    ExampleService testService;
    
	@Before
	public void setupData() {
	   dao = new ObjectStreamDao<Long, CItem>("_test.ser");
	   citemDao = new CItemDao(dao);
	   testService = new Example1Service(citemDao);
	}
	
	@Test
    public void testaddACItem() {
        CItem retrievedItem;
        List<CItem> clist;
        
        testService.addACItem();
        
        clist = citemDao.list();
        
        retrievedItem = clist.get(0);
        
        assertNotNull("Dao returns a null item.", retrievedItem);
	}

	@Test
    public void testmaxId() {
        Long id, newMax; 
        List<CItem> clist;
                
        id = testService.maxCItemId();
        
	    CItem oneItem = new CItem(2, "test string"); 
        oneItem.setId(id + 5);
        citemDao.add(oneItem);
        newMax = testService.maxCItemId();
        clist = citemDao.list();
        
        CItem retrievedItem = clist.get(0);
        System.out.println(retrievedItem);
        
        assertEquals("Stored Id and original Id are not equal ", newMax.longValue(), id.longValue() + 5);
	}
	
	/** 
	 * Need to delete the file for next test
	 */
	@After
	public void tearDown()
	{

		Path path = FileSystems.getDefault().getPath(".", "_test.ser");
		try {
		    Files.delete(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}


	}
}

