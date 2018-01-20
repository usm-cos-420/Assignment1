package edu.usm.cos420.example1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.usm.cos420.example1.dao.TestJsonDao;
import edu.usm.cos420.example1.dao.TestObjectStreamDao;
import edu.usm.cos420.example1.dao.domain.TestCItemDao;
import edu.usm.cos420.example1.service.impl.IntegrationTestExample1Service;

@RunWith(Suite.class)
@SuiteClasses({  TestObjectStreamDao.class, IntegrationTestExample1Service.class, TestCItemDao.class, TestJsonDao.class })
public class AllTests {

}
