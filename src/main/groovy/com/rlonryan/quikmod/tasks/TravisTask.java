/*
 */
package com.rlonryan.quikmod.tasks;

import com.rlonryan.quikmod.util.ModUtil;
import java.io.IOException;
import java.nio.file.Path;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 *
 * @author RlonRyan
 */
public class TravisTask extends DefaultTask {
	
	private Path travisPath;

	public TravisTask() {
		this.setGroup("QuikMod");
		this.setDescription("Generates a default Travis.yml file.");
	}
	
	public void initTask() {
		this.travisPath = this.getProject().getProjectDir().toPath().resolve(".travis.yml");
	}
	
	@TaskAction
	public void runTask() throws IOException {
		this.initTask();
		ModUtil.copyResource(this.getClass(), "/com/rlonryan/quikmod/defaults/.travis.yml", this.travisPath);
	}
	
}
