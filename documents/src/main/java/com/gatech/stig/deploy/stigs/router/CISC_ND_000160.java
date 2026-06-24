/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.router;

import com.gatech.stig.deploy.STIG;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_000160 extends STIG {
    private String title = "CISC-ND-000160"; // stig ID
    private int cat = 2; // stig category
    private String description = "The Cisco router must be configured to display the Standard Mandatory DoD Notice and Consent Banner \nbefore granting access to the device.";

    /* Return STIG info */
    public String getInfo(){
        /* Get roman numeral for STIG category and assemble output*/
        String c = "Uncategorized";
        switch(cat) {
            case 1:
                c = "I";
                break;
            case 2:
                c = "II";
                break;
            case 3:
                c = "III";
        }
        String info = "STIG Category and ID: " + c + " - " + title + "\n"
                + "Description: " + description + "\n"
                + "Enabled: " + String.valueOf(enabled);
        return info;
    }
    
    /* return the text of the script to be written to the ansible playbook */
    public String apply() {
        /* Build task script */
        String task = "    - name: " + title + "\n"
                + "      cisco.ios.ios_banner:\n"
                + "        banner: login\n"
                + "        text:  |\n"
                + "          You are accessing a U.S. Government (USG) Information System (IS) that is provided for USG-authorized use only.\n"
                + "\n"
                + "          By using this IS (which includes any device attached to this IS), you consent to the following conditions:\n"
                + "          \n"
                + "          -The USG routinely intercepts and monitors communications on this IS for purposes including, but not limited to, penetration testing, COMSEC monitoring, network operations and defense, personnel misconduct (PM), law enforcement (LE), and counterintelligence (CI) investigations.\n"
                + "\n"
                + "          -At any time, the USG may inspect and seize data stored on this IS.\n"
                + "\n"
                + "          -Communications using, or data stored on, this IS are not private, are subject to routine monitoring, interception, and search, and may be disclosed or used for any USG-authorized purpose.\n"
                + "\n"
                + "          -This IS includes security measures (e.g., authentication and access controls) to protect USG interests--not for your personal benefit or privacy.\n"
                + "\n"
                + "          -Notwithstanding the above, using this IS does not constitute consent to PM, LE or CI investigative searching or monitoring of the content of privileged communications, or work product, related to personal representation or services by attorneys, psychotherapists, or clergy, and their assistants. Such communications and work product are private and confidential. See User Agreement for details.\n"
                + "        state: present\n\n";
        return task;
    }
    
    /* toggle and configure STIG */
    public void configure(boolean en) {
        enable(en);
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
