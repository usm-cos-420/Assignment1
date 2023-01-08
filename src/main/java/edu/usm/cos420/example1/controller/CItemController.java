package edu.usm.cos420.example1.controller;


import java.util.List;

import edu.usm.cos420.example1.domain.CItem;
import edu.usm.cos420.example1.service.CItemService;
import edu.usm.cos420.example1.view.impl.CItemView;

/**
 *   A Controller class to execute user's menu choice.
 *     List of possible choices can be found at {@link edu.usm.cos420.example1.view.TextUI}
 *   
 */	 
public class CItemController {

	private CItemService citemService; 
	private CItemView view;
	
	/**
	 * Constructor : pass in a service class which can provide access to cItem operations. 
	 * @param view 
	 * @param service
	 */
	public CItemController(CItemView view, CItemService service)
	{
		this.view = view;
		this.citemService = service;
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
	      	citemService.addACItem();
	   	    view.addNotification();
	    }
	    else if (choice == CItemView.DISPLAYALL)
	    {
	    	List<CItem> clist = citemService.getAll();
			view.displayCItems(clist);
	    }
	    else if (choice == CItemView.EXIT)
	        view.exitNotification();
	    else 
	        view.invalidChoice();
	  }
	  
}
