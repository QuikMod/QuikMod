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
			file.createNewFile()
		}
		mod.load(file.newReader())
		
		defaults.each{ prop ->
			if (!mod.hasProperty(prop.key)) {
				mod.put(prop.key, prop.value)
			}
		}
		
		mod.id = "default"
		mod.name = "DefaultMod"
		mod.author = "Author"
		mod.group = "com.default"

		mod.version_major = "1"
		mod.version_minor = "0"
		mod.version_patch = "0"

		mod.version_minecraft = "1.10"
		mod.version_mappings = "snapshot_20160518"
		mod.version_forge = "12.18.0.1986-1.10.0"
		mod.version_java = "1.8"

		mod.reference_class = "reference/Reference.java"

		mod.update_url = "https://agricraft.github.io/versions/1.10/update.json"
			
		//Save Defaults
		mod.store(file.newWriter(), "QuikMod Mod Properties")
		
		// Set Properties
		target.ext.mod = mod
	}
	
}
