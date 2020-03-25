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
public class MedicalTable {
    String date;
    String checkuptype;
    String checkupcomment;

    public MedicalTable(String date, String checkuptype, String checkupcomment) {
        this.date = date;
        this.checkuptype = checkuptype;
        this.checkupcomment = checkupcomment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckuptype() {
        return checkuptype;
    }

    public void setCheckuptype(String checkuptype) {
        this.checkuptype = checkuptype;
    }

    public String getCheckupcomment() {
        return checkupcomment;
    }

    public void setCheckupcomment(String checkupcomment) {
        this.checkupcomment = checkupcomment;
    }
    
    
}
