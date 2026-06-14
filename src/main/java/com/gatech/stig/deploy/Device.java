/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy;

/**
 *
 * @author jmarsh40
 */
abstract public class Device {
    private String name; // device name
    private STIG[] sList; // list of device-specific stigs
    private ACL[] aclList; // list of user-configure ACLs
    private String address; // IP address of device for SSH
    
    /* Return device name */
    public String getName() {
        return name;
    }
    
    /* Return the device's list of STIGs */
    abstract public String getStigs();
            
    /* Create an Ansible Playbook for the device configuration */
    abstract public void printStigs();
    
    /* Configure and toggle the STIGs for the device */
    abstract public void editDevice();
}
