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
public class GymTable {
    String date;
    String gym;
    int gymhrs;

    public GymTable(String date,String gym, int gymhrs) {
        this.date = date;
        this.gym = gym;
        this.gymhrs = gymhrs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public int getGymhrs() {
        return gymhrs;
    }

    public void setGymhrs(int gymhrs) {
        this.gymhrs = gymhrs;
    }
    
    
}
