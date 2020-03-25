/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package githubfitfans;

public class WellnessTable {

    String date, har, journal, med;

    public WellnessTable(String date, String har, String journal, String med) {
        this.date = date;
        this.har = har;
        this.journal = journal;
        this.med = med;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHar() {
        return har;
    }

    public void setHar(String har) {
        this.har = har;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

}
