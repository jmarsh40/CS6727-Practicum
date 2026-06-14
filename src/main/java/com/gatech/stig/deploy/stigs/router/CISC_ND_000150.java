/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.router;

/**
 *
 * @author Ragnarak
 */
public class CISC_ND_000150 {
    private String title = "CISC-ND-000150"; // stig ID
    private String description = "The Cisco router must be configured to enforce the limit of three consecutive invalid logon attempts, after which time it must lock out the user account from accessing the device for 15 minutes.";
    private String scriptText = "";
    
    /* return the text of the script to be written to the ansible playbook */
    public String apply(){
        /* TBD */
        return "";
    }
}
