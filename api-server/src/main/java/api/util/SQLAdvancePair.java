/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.util;

/**
 *
 * @author Ison Ho
 */
public class SQLAdvancePair extends SQLPair{
    public String requirement;
    public String operator;
    
    public SQLAdvancePair(String requirement, String column, String operator, Object data) {
        super(column, data);
        this.requirement = requirement == null? "and" : requirement;
        this.operator = operator == null? "=" : operator;
    }
    
    @Override
    public String toCondition(boolean start){
        return " `" + column + "` " + operator +  " ? ";
    }
    
    @Override
    public String toCondition(){
        return " " + requirement + toCondition(false);
    }
}
