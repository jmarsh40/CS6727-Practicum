/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gatech.stig.deploy;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author root
 */
public class StigDeploy {
    private static final String menu = "Please select an option: \nCreate a new configuration: 1 \nLoad a configuration: 2 \nExit: 3\n";
    public static void main(String[] args) {
        Scanner selector = new Scanner(System.in);
        while (true) {
            System.out.println(menu);
            String choice = selector.nextLine();
            if ("3".equals(choice)) {
                return;
            }
        }
    }
}
