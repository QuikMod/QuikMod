package com.rlonryan.quikmod

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.Project
import org.gradle.util.GradleVersion

class ModPropertiesLoader {
	
    public static void loadModProperties(Project target) {
		
		def defaults = new Properties()
		defaults.load(ModPropertiesLoader.class.getResourceAsStream("defaults/mod.properties"))
		
		def mod = new Properties()
		def file = new File("${target.getRootDir()}/mod.properties")
		if(!file.exists()) {
			defaults.store(file.newWriter(), "QuikMod Mod Properties")
		}
		mod.load(file.newReader())
		
		defaults.each{ prop ->
			if (mod.getProperty(prop.key) == null) {
				println "Missing mod property: ${prop.key}!"
				mod.put(prop.key, prop.value)
			}
		}
		
		// Set Properties
		target.ext.mod = mod
		
	}
	
}
