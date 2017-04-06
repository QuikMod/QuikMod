package com.rlonryan.quikmod

import org.gradle.api.Project;
import org.gradle.api.Plugin;
import org.gradle.api.tasks.bundling.Jar;
import com.rlonryan.quikmod.util.*;
import com.rlonryan.quikmod.util.PropWrapper;
import com.rlonryan.quikmod.tasks.*;

class QuikMod implements Plugin<Project> {
	
    void apply(Project target) {
		
		// Apply ForgeGradle
		target.apply(plugin:'net.minecraftforge.gradle.forge')
		
		// Load Mod Properties
		ModPropertiesLoader.loadModProperties(target)
		
		// Add Tasks
		target.task('modInfo', type: ModInfoTask)
		target.task('netbeans', type: NetbeansTask)
		target.task('travis', type: TravisTask)
		target.task('generate', type: GenerateTask)
		target.task('javadocJar', type: Jar, dependsOn: 'build')
		
		target.sourceCompatibility = target.mod.version_java
		target.targetCompatibility = target.mod.version_java
		target.mod.version = "${target.mod.version_major}.${target.mod.version_minor}.${target.mod.version_patch}"
		target.version = target.mod.version
		target.group = "${target.mod.package}"
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
			inputs.property "mod", target.mod
			
			// Fetch Prop Wrapper
			def wrapper = new PropWrapper(target.mod, "mod.")

			// replace stuff in mcmod.info, nothing else
			filesMatching('**/mcmod.info') {
				filter {
					wrapper.replace(it)
				}
			}
		}
		
		// Add Shading Abilities
		target.configurations {
			javadoc {
				transitive false
			}
			testJavadoc {
				transitive false
			}
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
		
		// Enable Elastic Dependencies
		target.metaClass.elastic { projectName, notation ->
			if (allprojects.find { it.name == projectName }) {
				project(projectName)
			} else {
				dependencies.create(notation)
			}
		}
		
		// Edit Javadoc Jar Task
		target.javadocJar {
			from target.javadoc.destinationDir
			classifier = 'javadoc'
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
