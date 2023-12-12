/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.util;

/**
 *
 * @author Ison Ho
 * @param <T>
 */
public class SQLPair<T> {
    public String column;
    public T data;


    public SQLPair(String column, T data) {
        this.column = column;
        this.data = data;
    }
    
    public String toCondition(boolean start){
        return " `" + column + "` = ? ";
    }
    
    public String toCondition(){
        return " and " + toCondition(false);
    }
    
    @Override
    public String toString(){
        return data.toString();
    }
}
