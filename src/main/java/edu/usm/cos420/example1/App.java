package edu.usm.cos420.example1;

import edu.usm.cos420.example1.controller.CItemController;
import edu.usm.cos420.example1.service.ExampleService;
import edu.usm.cos420.example1.service.impl.Example1Service;
import edu.usm.cos420.example1.view.impl.CItemView;

/**
 * Top level application class that coordinates the calls to view and Controller
 *
 */
public class App
{
    /**
     * Entry point for application : calls {@link #provideCItemAccess()}
     * @param args  main program arguments, currently not used
     */
	public static void main( String[] args )
    {
		ExampleService service = new Example1Service();
		CItemView citemView = new CItemView();
		CItemController controller = new CItemController(citemView,service);		
		controller.provideCItemAccess();
    }
}
