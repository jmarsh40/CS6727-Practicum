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
    private boolean enabled; // toggle to enable/disable benchmark
    
    /* Return STIG info */
    abstract public String getInfo();
    
    /* Toggle STIG */
    public void enable(boolean en){
        enabled = en;
    }
}
