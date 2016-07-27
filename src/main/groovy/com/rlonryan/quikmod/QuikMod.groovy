package com.rlonryan.quikmod

import org.gradle.api.Project
import org.gradle.api.Plugin
import com.rlonryan.quikmod.tasks.*;

class QuikMod implements Plugin<Project> {
	
    void apply(Project target) {
		
		// Apply ForgeGradle
		target.apply(plugin:'net.minecraftforge.gradle.forge')
		ModPropertiesLoader.loadModProperties(target)
		target.task('modInfo', type: ModInfoTask)
		target.task('netbeans', type: NetbeansTask)
		target.task('travis', type: TravisTask)
		target.task('generate', type: GenerateTask)
		
		target.sourceCompatibility = target.mod.version_java
		target.targetCompatibility = target.mod.version_java
		target.mod.version = "${target.mod.version_major}.${target.mod.version_minor}.${target.mod.version_patch}"
		target.version = target.mod.version
		target.group = "${target.mod.group}.${target.mod.id}"
		target.archivesBaseName = target.mod.id
		
		// Handle Forge Stuff
		target.minecraft {
			version = "${target.mod.version_minecraft}-${target.mod.version_forge}"
			mappings = target.mod.version_mappings
			runDir = "/run/${target.mod.version_minecraft}"
			makeObfSourceJar = false
			useDepAts = true

			// Replace mod information in reference class.
			// Has to be done this way due to Forge + Gradle Limitations
			replaceIn target.mod.reference_class
			target.mod.each { prop ->
				replace "\${mod.${prop.key}}", prop.value
				replace "/*^", "\""
				replace "^*/", "\"; // Default:"
			}
		}
		
		// Correct Resources
		target.processResources {
			// this will ensure that this task is redone when the versions change.
			inputs.property "mod", project.mod

			// replace stuff in mcmod.info, nothing else
			filesMatching('**/mcmod.info') {
				filter {
					return ModUtil.replaceModProps(project.mod, it)
				}
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
