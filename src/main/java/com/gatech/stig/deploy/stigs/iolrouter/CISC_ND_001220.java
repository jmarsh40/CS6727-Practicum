/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gatech.stig.deploy.stigs.iolrouter;

import com.gatech.stig.deploy.IP;
import com.gatech.stig.deploy.STIG;
import java.util.Scanner;

/**
 *
 * @author jmarsh40
 */
public class CISC_ND_001220 extends STIG {

    private String title = "CISC-ND-001220"; // stig ID
    private int cat = 2;
    private String description = "The Cisco router must be configured to protect against known types of denial-of-service (DoS) attacks\n by employing organization-defined security safeguards.";
    private String ospf = "10.1.0.1"; // osfp host address
    private String pim = "10.1.0.2"; // pim host address
    private String igmp = "10.10.10.0"; // igmp subnet address
    private String igmpMask = "192.168.1.0"; // igmp subnet mask
    private String tcp1 = "10.0.0.5"; // tcp 1 host address
    private String tcp2 = "10.0.0.6"; // tcp 1 host address
    private String tcp3 = "10.0.0.7"; // tcp 1 host address
    private String tcp4 = "192.168.1.0"; // tcp 4 subnet address
    private String tcp4Mask = "0.0.0.255"; // management subnet address
    private String snmp = "10.1.0.3"; // management subnet address
    private String ntp = "10.1.0.4"; // management subnet address

    /* Return STIG info */
    public String getInfo() {
        /* Get roman numeral for STIG category and assemble output*/
        String c = "Uncategorized";
        switch (cat) {
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
        String task = "    - name: " + title + " - acl\n"
                + "      cisco.ios.ios_acls:\n"
                + "        config:\n"
                + "          - afi: ipv4\n"
                + "            acls:\n"
                + "              - name: CoPP_CRITICAL\n"
                + "                acl_type: extended\n"
                + "                aces:\n"
                + "                  - remarks:\n"
                + "                    - \"our control plane adjacencies are critical\"\n"
                + "                  - grant: permit\n"
                + "                    protocol: ospf\n"
                + "                    source:\n"
                + "                      host: " + ospf + "\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: pim\n"
                + "                    source:\n"
                + "                      host: " + pim + "\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: igmp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      address: " + igmp + " " + igmpMask + "\n"
                + "                  - grant: permit\n"
                + "                    protocol: tcp\n"
                + "                    source:\n"
                + "                      host: " + tcp1 + "\n"
                + "                      port_protocol:\n"
                + "                        eq: bgp\n"              
                + "                    destination:\n"
                + "                      host: " + tcp2 + "\n"
                + "                  - grant: deny\n"
                + "                    protocol: ip\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "              - name: CoPP_IMPORTANT\n"
                + "                acl_type: extended\n"
                + "                aces:\n"
                + "                  - grant: permit\n"
                + "                    protocol: tcp\n"
                + "                    source:\n"
                + "                      host: " + tcp3 + "\n"
                + "                      port_protocol:\n"
                + "                        eq: tacacs\n"              
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: tcp\n"
                + "                    source:\n"
                + "                      address: " + tcp4 + " " + tcp4Mask + "\n"             
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: 22\n" 
                + "                  - grant: permit\n"
                + "                    protocol: udp\n"
                + "                    source:\n"
                + "                      host: " + snmp + "\n"             
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: snmp\n" 
                + "                  - grant: permit\n"
                + "                    protocol: udp\n"
                + "                    source:\n"
                + "                      host: " + ntp + "\n"
                + "                      port_protocol:\n"
                + "                        eq: ntp\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: deny\n"
                + "                    protocol: ip\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "              - name: CoPP_NORMAL\n"
                + "                acl_type: extended\n"
                + "                aces:\n"
                + "                  - remarks:\n"
                + "                    - \"we will want to rate limit ICMP traffic\"\n"
                + "                  - grant: permit\n"
                + "                    protocol: icmp\n"
                + "                    protocol_options:\n"
                + "                      icmp:\n"
                + "                        echo: true\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: icmp\n"
                + "                    protocol_options:\n"
                + "                      icmp:\n"
                + "                        echo_reply: true\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: icmp\n"
                + "                    protocol_options:\n"
                + "                      icmp:\n"
                + "                        time_exceeded: true\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: icmp\n"
                + "                    protocol_options:\n"
                + "                      icmp:\n"
                + "                        unreachable: true\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: deny\n"
                + "                    protocol: ip\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "              - name: CoPP_UNDESIRABLE\n"
                + "                acl_type: extended\n"
                + "                aces:\n"
                + "                  - remarks:\n"
                + "                    - \"management plane traffic that should not be received\"\n"
                + "                  - grant: permit\n"
                + "                    protocol: udp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: ntp\n" 
                + "                  - grant: permit\n"
                + "                    protocol: udp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: snmp\n"
                + "                  - grant: permit\n"
                + "                    protocol: tcp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: 22\n"
                + "                  - grant: permit\n"
                + "                    protocol: tcp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: 23\n"
                + "                  - remarks:\n"
                + "                    - \"remark control plane traffic not configured on router\"\n"
                + "                  - grant: permit\n"
                + "                    protocol: eigrp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                  - grant: permit\n"
                + "                    protocol: udp\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "                      port_protocol:\n"
                + "                        eq: rip\n"
                + "                  - grant: deny\n"
                + "                    protocol: ip\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "              - name: CoPP_DEFAULT\n"
                + "                acl_type: extended\n"
                + "                aces:\n"
                + "                  - grant: permit\n"
                + "                    protocol: ip\n"
                + "                    source:\n"
                + "                      any: true\n"
                + "                    destination:\n"
                + "                      any: true\n"
                + "        state: replaced\n\n"
                + "    - name: " + title + " - maps\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - match access-group name CoPP_CRITICAL\n"
                + "          - class-map match-any CoPP_IMPORTANT\n"
                + "          - match access-group name CoPP_IMPORTANT\n"
                + "          - match protocol arp\n"
                + "          - class-map match-all CoPP_NORMAL\n"
                + "          - match access-group name CoPP_NORMAL\n"
                + "          - class-map match-any CoPP_UNDESIRABLE\n"
                + "          - match access-group name CoPP_UNDESIRABLE\n"
                + "          - class-map match-all CoPP_DEFAULT\n"
                + "          - match access-group name CoPP_DEFAULT\n"
                + "        parents:\n"
                + "          - class-map match-all CoPP_CRITICAL\n\n"
                + "    - name: " + title + " - policy\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - class CoPP_CRITICAL\n"
                + "          - police 512000 8000 conform-action transmit exceed-action transmit\n"
                + "          - class CoPP_IMPORTANT\n"
                + "          - police 256000 4000 conform-action transmit exceed-action drop\n"
                + "          - class CoPP_NORMAL\n"
                + "          - police 128000 2000 conform-action transmit exceed-action drop\n"
                + "          - class CoPP_UNDESIRABLE\n"
                + "          - police 8000 1000 conform-action drop exceed-action drop\n"
                + "          - class CoPP_DEFAULT\n"
                + "          - police 64000 1000 conform-action transmit exceed-action drop\n"
                + "        parents:\n"
                + "          - policy-map CONTROL_PLANE_POLICY\n\n"
                + "    - name: " + title + " - control\n"
                + "      cisco.ios.ios_config:\n"
                + "        lines:\n"
                + "          - service-policy input CONTROL_PLANE_POLICY\n"
                + "        parents:\n"
                + "          - control-plane\n\n";
        return task;
    }

    /* toggle and configure STIG */
    public void configure(boolean en) {
        enable(en);
                if (!en) {
            return;
        }
        Scanner selector = new Scanner(System.in);
        String choice = "";

        /* Set ospf address */
        while (true) {
            System.out.println("Enter an IP address for the ospf host");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    ospf = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set pim address */
        while (true) {
            System.out.println("Enter an IP address for the pim host");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    pim = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set igmp subnet address */
        while (true) {
            System.out.println("Enter an address for the igmp subnet");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    igmp = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set igmp subnet reverse mask */
        while (true) {
            System.out.println("Enter a reverse mask for the igmp subnet");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    igmpMask = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set tcp address 1 */
        while (true) {
            System.out.println("Enter an IP address for TCP host 1");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    tcp1 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set tcp address 2 */
        while (true) {
            System.out.println("Enter an IP address for TCP host 2");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    tcp2 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set tcp address 3 */
        while (true) {
            System.out.println("Enter an IP address for TCP host 3");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    tcp3 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set tcp 4 subnet address */
        while (true) {
            System.out.println("Enter an address for TCP subnet 4");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    tcp4 = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set management reverse mask */
        while (true) {
            System.out.println("Enter a reverse mask TCP subnet 4");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    tcp4Mask = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set snmp address */
        while (true) {
            System.out.println("Enter an IP address for the SNMP host");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    snmp = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
        /* Set ntp address */
        while (true) {
            System.out.println("Enter an IP address for the NTP host");
            choice = selector.nextLine();
            try {
                if (IP.check(choice)) { // Input is in range
                    ntp = choice;
                    break;
                } else { // Input is out of range
                    System.out.println("Not a valid IP address");
                }
            } catch (NumberFormatException e) { // Catch non-parseable input
                System.out.println("Not a valid IP address");
            }
        }
    }
    
    /* Get STIG Category */
    public int getCat(){
        return cat;
    }
}
