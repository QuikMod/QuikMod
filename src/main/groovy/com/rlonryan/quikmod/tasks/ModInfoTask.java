/*
 */
package com.rlonryan.quikmod.tasks;

import com.rlonryan.quikmod.util.ModUtil;
import java.io.IOException;
import java.util.Properties;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GradleVersion;

/**
 *
 * @author RlonRyan
 */
public class ModInfoTask extends DefaultTask {

	public ModInfoTask() {
		this.setGroup("QuikMod");
		this.setDescription("Prints information about the QuikMod.");
	}

	@TaskAction
	public void runTask() throws IOException {
		System.out.println("");
		System.out.println("#################################################");
		System.out.println("\tQuikMod");
		System.out.println("#################################################");

		// Fetch mod settings.
		Properties props = ModUtil.getModProperties(this.getProject());

		// Verify mod id follows proper conventions.
		final String modid = props.getProperty("id");
		assert modid.equals(modid.toLowerCase());
		assert modid.equals(modid.replace(" ", ""));
		assert modid.equals(modid.replace(".", ""));

		// Verify mod package follows proper conventions.
		final String pack = props.getProperty("package");
		assert pack.equals(pack.toLowerCase());
		assert pack.equals(pack.replace(" ", ""));

		// Merge Gradle Version
		props.put("version_gradle", GradleVersion.current().getVersion());

		// Print Information Tokens
		props.entrySet().forEach((e) -> System.out.printf(":%1$-20s = %2$s%n", e.getKey(), e.getValue()));

		System.out.println("#################################################");
		System.out.println("");
	}

}
