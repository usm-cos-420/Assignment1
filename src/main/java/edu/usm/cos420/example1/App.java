package edu.usm.cos420.example1;

import edu.usm.cos420.example1.controller.CItemController;
import edu.usm.cos420.example1.service.ExampleService;
import edu.usm.cos420.example1.service.ExampleService;
import edu.usm.cos420.example1.view.impl.CItemView;

/**
 * Top level application class that coordinates the calls to view and Controller
 *   
 *   This serves as a starting point for your Assignment 1.
 *       I have demonstrated the flow of control for a class called CItem (which is an example class)
 *       You should examine all of the code and follow the flow of control before 
 *       making classes for Customers, Inventory items, ...
 *       
 *   Work iteratively ! Do not try to solve the assignment in one go ! So, for example, 
 *       you may want to start by defining the Customer class, then try to store and 
 *       retrieve customers (you will need to make a Data Access class for Customers). 
 *       Step by step wins the day !
 */
public class App
{
    /**
     * Entry point for application : calls {@link #provideCItemAccess()}
     * @param args  main program arguments, currently not used
     */
	public static void main( String[] args )
    {
		ExampleService service = new ExampleService();
		CItemView citemView = new CItemView();
		CItemController controller = new CItemController(citemView,service);		
		controller.provideCItemAccess();
    }
}
