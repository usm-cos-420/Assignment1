/*
 * CItem.java
 *
 * Created on August 28, 2001, 11:46 AM
 */

package edu.usm.cos420.example1.domain;


/**
 *
 *  For the purposes of this example, CItem holds three 
 *  piece of data.  
 *  
 *  The id field is necessary for uniquely identifying items.
 */
public class CItem {
    
    private Long id;
	private Integer myInteger;
    private String  myString;

    /** 
     * Single field constructor : at a minimum, the id is needed 
     */
    public CItem(Long id) {
    	this.id = id;
    }

    /** 
     * Three field constructor 
     */
    public CItem(Long id, int n, String str) {
        myInteger = Integer.valueOf(n);
        myString = str;
    	this.id = id;
    }

    /**
     * get the ID of the CItem 
     * @return ID 
     */
     public Long getId() {
 		return id;
 	}

     /**
      * Set the ID of the CItem
      * @param id new id 
      */
 	public void setId(Long id) {
 		this.id = id;
 	}
     
	/**
	 * @return Returns the Integer.
	 */
	public Integer getMyInteger() {
		return myInteger;
	}
	/**
	 * @param i The integer to set.
	 */
	public void setMyInteger(int i) {
		this.myInteger = Integer.valueOf(i);
	}
	/**
	 * @return Returns  myString.
	 */
	public String getMyString() {
		return myString;
	}
	/**
	 * @param myString The string to set.
	 */
	public void setMyString(String myString) {
		this.myString = myString;
	}

    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("CItem [id=%d,myString=%s,myInteger=%s]", getId(), myString, myInteger);
    }

   
}




