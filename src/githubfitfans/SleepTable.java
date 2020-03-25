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
public class SleepTable {
    String date;
    int hrs;
    String rating;
    String sleepJournal;

    public SleepTable(String date, int hrs, String rating, String sleepJournal) {
        this.date = date;
        this.hrs = hrs;
        this.rating = rating;
        this.sleepJournal = sleepJournal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHrs() {
        return hrs;
    }

    public void setHrs(int hrs) {
        this.hrs = hrs;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSleepJournal() {
        return sleepJournal;
    }

    public void setSleepJournal(String sleepJournal) {
        this.sleepJournal = sleepJournal;
    }
    
    
}
