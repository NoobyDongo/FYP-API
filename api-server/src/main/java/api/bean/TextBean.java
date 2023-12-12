/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ison Ho
 */


public class TextBean {
    
    public String id;
    public String desc;
    public ArrayList<TranslatedTextBean> translation = new ArrayList();
    
    public TextBean(){};
    public TextBean(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }
    
}
