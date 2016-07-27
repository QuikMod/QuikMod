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
public class NetbeansTask extends DefaultTask {
	
	private Path netbeansPath;

	public NetbeansTask() {
		this.setGroup("QuikMod");
		this.setDescription("Generates a default .nb-gradle-properties file.");
	}
	
	public void initTask() {
		this.netbeansPath = this.getProject().getProjectDir().toPath().resolve(".nb-gradle-properties");
	}
	
	@TaskAction
	public void runTask() throws IOException {
		ModUtil.copyResource(this.getClass(), "defaults/.nb-gradle-properties", this.netbeansPath);
	}
	
}
