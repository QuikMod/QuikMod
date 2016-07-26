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
class NetbeansTask extends DefaultTask {
	
	@TaskAction
	def netbeans() {
		def file = new File("${project.getRootDir()}/.nb-gradle-properties")
		if(!file.exists()) {
			file.createNewFile()
			file.write(this.getClass().getResourceAsStream("defaults/.nb-gradle-properties").text)
		}
	}
	
}

