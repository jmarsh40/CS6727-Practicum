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
abstract public class Device {
    public String name; // device name
    public List<STIG> sList = new ArrayList<>(); // list of device-specific stigs
    private ACL[] aclList; // list of user-configure ACLs
    public String address; // IP address of device for SSH
    
    /* Return device name */
    public String getName() {
        return name;
    }
    
    /* Return device address */
    public String getAddress() {
        return address;
    }
    
    /* Return the device's list of STIGs */
    abstract public List<STIG> getStigs();
            
    /* Create an Ansible Playbook for the device configuration */
    abstract public void printStigs();
    
    /* Configure and toggle the STIGs for the device */
    abstract public void editDevice();
    
    public void setName(String in){
        name = in;
    }
    public void setAddress(String in){
        address = in;
    }
}
