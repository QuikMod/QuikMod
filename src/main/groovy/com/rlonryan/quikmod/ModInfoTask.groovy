package com.rlonryan.quikmod

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.util.GradleVersion

class ModInfoTask extends DefaultTask {
	
    @TaskAction
    def modInfo() {
		println ""
		println "#################################################"
		println "	QuikMod"
		println "#################################################"
		
		// Fetch mod settings.
		def mod = project.mod

		// Verify mod id follows proper conventions.
		assert mod.id == mod.id.toLowerCase();
		assert mod.id == mod.id.replace(' ', '');
		assert mod.id == mod.id.replace('.', '');

		// Verify mod group follows proper conventions.
		assert mod.group == mod.group.toLowerCase();
		assert mod.group == mod.group.replace(' ', '');

		// Print Gradle Information
		println ":" + "version_gradle".padRight(20) + " = " + GradleVersion.current().version

		// Print Information Tokens
		mod.each { prop ->
			println ":" + prop.key.padRight(20) + " = " + prop.value
		}

		println "#################################################"
		println ""
	}
	
}
