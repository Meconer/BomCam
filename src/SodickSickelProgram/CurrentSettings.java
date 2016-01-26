/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import java.io.File;

/**
 *
 * @author matsandersson
 */
public class CurrentSettings {
    private String currentDirectory;
    
    private CurrentSettings() {
        currentDirectory = System.getProperty("user.home") + File.separator ;
                
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String currentDirectory) {
        this.currentDirectory = currentDirectory;
    }
    
    public static CurrentSettings getInstance() {
        return CurrentSettingsHolder.INSTANCE;
    }
    
    private static class CurrentSettingsHolder {

        private static final CurrentSettings INSTANCE = new CurrentSettings();
    }
}
