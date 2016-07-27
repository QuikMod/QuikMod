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
		def pack = "${project.mod.group}.${project.mod.id}"
		def fold = pack.replaceAll("\\.", "/")
		
		// Reference Class
		def ref = new File("${base}/java/${fold}/${project.mod.reference_class}")
		if(!ref.exists()) {
			ref.toPath().getParent().toFile().mkdirs()
			ref.createNewFile()
			def template = this.getClass().getResourceAsStream("defaults/reference.template").text
			// insert packagename
			template = template.replaceAll('\\$\\{package\\}', pack)
			template = template.replaceAll('\\$\\{class\\}', project.mod.reference_class.split("\\.")[0])
			ref.write(template)
		}
		
		// Reference Class
		def mod = new File("${base}/java/${fold}/${project.mod.id}.java")
		if(!mod.exists()) {
			mod.toPath().getParent().toFile().mkdirs()
			mod.createNewFile()
			def template = this.getClass().getResourceAsStream("defaults/mod.template").text
			// insert packagename
			template = template.replaceAll('\\$\\{package\\}', pack)
			template = template.replaceAll('\\$\\{class\\}', project.mod.id)
			mod.write(template)
		}
		
		// Mod Info File
		def info = new File("${base}/resources/mcmod.info")
		if(!info.exists()) {
			info.toPath().getParent().toFile().mkdirs()
			info.createNewFile()
			info.write(this.getClass().getResourceAsStream("defaults/mcmod.info").text)
		}
	}
	
}

