/*
 */

package com.rlonryan.quikmod

import java.nio.file.Path
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Optional
import org.gradle.util.GradleVersion

/**
 *
 * @author RlonRyan
 */
class GenerateTask extends DefaultTask {
	
	@TaskAction
	def generate() {
		def base = "${project.getRootDir()}/src/main"
		def pack = "${project.mod.group.replaceAll("\\.", "/")}/${project.mod.id}"
		
		def ref = new File("${base}/java/${pack}/${project.mod.reference_class}")
		if(!ref.exists()) {
			ref.toPath().getParent().toFile().mkdirs()
			ref.createNewFile()
			def template = this.getClass().getResourceAsStream("defaults/reference.template").text
			// insert packagename
			ref.write(template.replaceFirst('\\$\\{package\\}', pack.replaceAll("/", ".")))
		}
		
		def info = new File("${base}/resources/assets/${pack}/mcmod.info")
		if(!info.exists()) {
			info.toPath().getParent().toFile().mkdirs()
			info.createNewFile()
			info.write(this.getClass().getResourceAsStream("defaults/mcmod.info").text)
		}
	}
	
}

