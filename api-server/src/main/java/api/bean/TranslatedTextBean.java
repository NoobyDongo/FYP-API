/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.bean;

/**
 *
 * @author Ison Ho
 */
public class TranslatedTextBean {
    public String languageId;
    public String desc;

    public TranslatedTextBean(String languageId, String text) {
        this.languageId = languageId;
        this.desc = text;
    }
    
    public TranslatedTextBean(){
        
    }
}
