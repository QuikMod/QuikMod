package com.rlonryan.quikmod

import org.gradle.api.Project
import org.gradle.api.Plugin

class QuikMod implements Plugin<Project> {
	
    void apply(Project target) {
		
		// Apply ForgeGradle
		target.apply(plugin:'net.minecraftforge.gradle.forge')
		ModPropertiesLoader.loadModProperties(target)
		target.task('modInfo', type: ModInfoTask)
		
		// Fetch mod settings
		def mod = target.mod
		
		target.sourceCompatibility = mod.version_java
		target.targetCompatibility = mod.version_java
		target.version = "${mod.version_major}.${mod.version_minor}.${mod.version_patch}"
		mod.version = target.version
		target.group = "${mod.group}.${mod.id}"
		target.archivesBaseName = mod.id
		
		// Handle Forge Stuff
		target.minecraft {
			version = "${mod.version_minecraft}-${mod.version_forge}"
			mappings = mod.version_mappings
			runDir = "/run/${mod.version_minecraft}"
			makeObfSourceJar = false
			useDepAts = true

			// Replace mod information in reference class.
			replaceIn mod.reference_class
			mod.each { prop ->
				replace "\${mod.${prop.key}}", prop.value
				replace "/*^", "\""
				replace "^*/", "\"; // Default:"
			}
		}
		
		// Correct Resources
		target.processResources {
			// this will ensure that this task is redone when the versions change.
			inputs.property "version", project.version
			inputs.property "mcversion", project.minecraft.version

			// replace stuff in mcmod.info, nothing else
			from(target.sourceSets.main.resources.srcDirs) {
				include 'mcmod.info'
				expand 'version':project.version, 'mcversion':project.minecraft.version
			}

			from(target.sourceSets.main.resources.srcDirs) {
				exclude 'mcmod.info'
			}
		}
		
		// Add Shading Abilities
		target.configurations {
			shade
			compile.extendsFrom shade
		}
		
		// Enable Jar Shading
		target.jar {
			target.configurations.shade.copyRecursive().setTransitive(false).each { artifact ->
				from (target.zipTree(artifact)) {
					exclude 'META-INF', 'META-INF/**'
				}
			}
		}
		
		// Add login options to runClient
		target.runClient {
			if( target.hasProperty('minecraft_username') && target.hasProperty('minecraft_password') ) {
				args "--username=${target.minecraft_username}"
				args "--password=${target.minecraft_password}"
			}
		}
    }

}
