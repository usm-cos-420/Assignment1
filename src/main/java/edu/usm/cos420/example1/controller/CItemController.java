package edu.usm.cos420.example1.controller;


import edu.usm.cos420.example1.service.ExampleService;
import edu.usm.cos420.example1.view.impl.CItemView;

/**
 *   A Controller class to execute user's menu choice.
 *     List of possible choices can be found at {@link edu.usm.cos420.example1.view.TextUI}
 *   
 */	 
public class CItemController {

	private ExampleService atMyService; 
	private CItemView view;
	
	/**
	 * Constructor : pass in a service class which can provide access to cItem operations. 
	 * @param view 
	 * @param service
	 */
	public CItemController(CItemView view, ExampleService service)
	{
		this.view = view;
		this.atMyService = service;
	}

	/**
	 * Allow the user to access the citem collection
	 * 
	 */
    public void provideCItemAccess()
    {
        int choice = CItemView.NO_CHOICE;
        while (choice != CItemView.EXIT) {
          view.displayMenu();
          choice = view.readIntWithPrompt("Enter choice: ");
          executeChoice(choice);
        }  	
    }

	/**
	 *   Performs the branching logic to call appropriate functions to satisfy user choice
	 *   @param choice represents the user selection of action they want accomplished. 
 	 */
	public void executeChoice (int choice) {
	    System.out.println();
	    if (choice == CItemView.ADDONE)
	    {
	      	atMyService.addACItem();
	   	    System.out.println("Added one item ");
	    }
	    else if (choice == CItemView.EXIT)
	      System.out.println("Goodbye.");
	  }
	  
}
