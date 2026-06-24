/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmarsh40
 */
abstract public class STIG {
    public boolean enabled = false; // toggle to enable/disable benchmark
    
    public abstract String apply();
    
    /* Return STIG info */
    abstract public String getInfo();
    
    /* Toggle / Configure STIG */
    public void enable(boolean en){
        enabled = en;
        System.out.println("Enabled set to: " + String.valueOf(enabled));
    }
    
    abstract public void configure(boolean en);
    
    /* save benchmark config */
    public List<String> save(){
        /* Convert attributes to list form */
        List<String> out = new ArrayList<>();
        String en = "false";
        if (enabled) {
            en = "true";
        }
        out.add(en);
        return out;
    }
    
    /* Get STIG Category */
    abstract public int getCat();
}
