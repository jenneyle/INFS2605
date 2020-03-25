/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package githubfitfans;

/**
 *
 * @author jenneyle
 */
public class AerobicTable {
    String date;
    String aerobic;
    int km;

    public AerobicTable(String date, String aerobic, int km) {
        this.date = date;
        this.aerobic = aerobic;
        this.km = km;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAerobic() {
        return aerobic;
    }

    public void setAerobic(String aerobic) {
        this.aerobic = aerobic;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }
    
          
    
}
