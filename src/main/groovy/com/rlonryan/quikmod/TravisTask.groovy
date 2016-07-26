/*
 */

package com.rlonryan.quikmod

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Optional
import org.gradle.util.GradleVersion

/**
 *
 * @author RlonRyan
 */
class TravisTask extends DefaultTask {
	
	@TaskAction
	def travis() {
		def file = new File("${project.getRootDir()}/travis.yml")
		if(!file.exists()) {
			file.createNewFile()
			file.write(this.getClass().getResourceAsStream("defaults/.travis.yml").text)
		}
	}
	
}

