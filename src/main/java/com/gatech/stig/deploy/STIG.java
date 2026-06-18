/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

/**
 *
 * @author jmarsh40
 */
abstract public class STIG {
    private String title; // title of the benchmark
    public boolean enabled = false; // toggle to enable/disable benchmark
    
    public abstract String apply();
    
    /* Return STIG info */
    abstract public String getInfo();
    
    /* Toggle / Configure STIG */
    public void enable(boolean en){
        enabled = en;
        System.out.println("Enabled set to: " + String.valueOf(enabled));
    }
}
