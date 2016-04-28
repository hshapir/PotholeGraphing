/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package potholegraphing;

import java.io.*;



public class Settings implements java.io.Serializable {
    public transient static Settings instance;
    private Settings() {}
    private static Pothole[] potholes;
    private static String filterValue;
    
    public static void init() {
        if(instance == null) {
            try
            {
                FileInputStream fileIn = new FileInputStream("settings.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                instance = (Settings) in.readObject();
                in.close();
                fileIn.close();
            }catch(IOException i)
            {
            instance = new Settings();
            return;
      }catch(ClassNotFoundException c)
      {
         System.out.println("Settings class not found");
         c.printStackTrace();
         return;
      }
        }
    }
    
    public static void save() {
        init();
        try
        {
         FileOutputStream fileOut = new FileOutputStream("settings.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(instance);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved in settings.ser");
        }catch(IOException i)
        {
          i.printStackTrace();
        }
    }
    
    public static String getFilterValue() {
        init();
        return instance.filterValue;
    }
    
    public static void setFilterValue(String s) {
        init();
        instance.filterValue = s;
    }
    
    public static Pothole[] getPotholes(){
        init();
        return instance.potholes;
    }
    
    public static void setPotholes(Pothole[] ps){
        init();
        instance.potholes = ps;
    }
    
}
