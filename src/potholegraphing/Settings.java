/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potholegraphing;



public class Settings {
    public Settings instance;
    private Settings() {}
    private static Pothole[] potholes;
    private static String filterValue;
    
    public static void init() {
        if(instance == null) {
            instance = new Settings();
        }
    }
    
    public static String getFilterValue() {
        init();
        return instance.filterValue;
    }
    
    public static void setFilterValue(String s) {
        init();
        filterValue = s;
    }
    
    public static Pothole[] getPotholes(){
        init();
        return instance.potholes;
    }
    
    public static void setPotholes(Pothole[] ps){
        init();
        potholes = ps;
    }
    
}
