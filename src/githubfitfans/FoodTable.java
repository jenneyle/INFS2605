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
public class FoodTable {

    String date;
    String mealtime;
    String foodgroup;
    int kj;
    String mealrating;
    

    public FoodTable(String date, String mealtime, String foodgroup, int kj, String mealrating) {
        this.date = date;
        this.mealtime = mealtime;
        this.foodgroup = foodgroup;
        this.kj = kj;
        this.mealrating = mealrating;
        
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMealtime() {
        return mealtime;
    }

    public void setMealtime(String mealtime) {
        this.mealtime = mealtime;
    }

    public String getFoodgroup() {
        return foodgroup;
    }

    public void setFoodgroup(String foodgroup) {
        this.foodgroup = foodgroup;
    }

    public String getMealrating() {
        return mealrating;
    }

    public void setMealrating(String mealrating) {
        this.mealrating = mealrating;
    }

    public int getKj() {
        return kj;
    }

    public void setKj(int kj) {
        this.kj = kj;
    }

}
