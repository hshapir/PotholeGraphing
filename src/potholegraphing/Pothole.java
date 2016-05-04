/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potholegraphing;

import java.io.Serializable;

/**
 *
 * @author csstudent
 */
class Pothole implements Serializable{
    private int zip;
    private String completion_date;
    private String status;
    
    public Pothole() {
    }
    
    public int getZip() {
        return zip;
    }
    
    public int getDate() {
        if(completion_date.contains("2013")){
            return 2013;
        } else if(completion_date.contains("2012")){
            return 2012;
        } else if(completion_date.contains("2011")){
            return 2011;
        }
        return -1;
    }
    
    public boolean completed(){
        if(status.contains("plete")){
            return true;
        } else {
            return false;
        }
    }
    
    
    @Override
    public String toString() {
        String compl = null;
        if(completed()){
            compl = "Completed";
        } else {
            compl = "Incomplete";
        }
        return "" + zip + " " + compl;
    }
}
