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
public class ResistanceTable {

    String date;
    String resistance;
    int reps;
    int kgs;

    public ResistanceTable(String date, String resistance, int reps, int kgs) {
        this.date = date;
        this.resistance = resistance;
        this.reps = reps;
        this.kgs = kgs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getKgs() {
        return kgs;
    }

    public void setKgs(int kgs) {
        this.kgs = kgs;
    }

}
