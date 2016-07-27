/*
 */
package com.rlonryan.quikmod.tasks;

import com.rlonryan.quikmod.util.ModUtil;
import com.rlonryan.quikmod.util.PropWrapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 *
 * @author RlonRyan
 */
public class GenerateTask extends DefaultTask {

	private Properties modProps;

	private Path basePath;
	private Path javaPath;
	private Path rescPath;
	private Path codePath;
	private Path infoPath;
	private Path modPath;
	private Path refPath;
	
	private String modPack;
	private String refPack;
	
	private String modClass;
	private String refClass;
	
	private PropWrapper wrap;

	public GenerateTask() {
		this.setGroup("QuikMod");
		this.setDescription("Generates a default set of mod files.");
	}
	
	public void initTask() {
		this.modProps = ModUtil.getModProperties(this.getProject());
		
		this.modPack = this.modProps.getProperty("package");
		
		this.basePath = this.getProject().getProjectDir().toPath();
		this.javaPath = this.basePath.resolve("src/main/java");
		this.rescPath = this.basePath.resolve("src/main/resources");
		this.codePath = this.javaPath.resolve(this.modPack.replaceAll("\\.", "/"));
		this.infoPath = this.rescPath.resolve("mcmod.info");
		this.modPath = this.codePath.resolve(this.modProps.getProperty("mod_class"));
		this.refPath = this.codePath.resolve(this.modProps.getProperty("reference_class"));
		
		this.refPack = ModUtil.toPackageName(this.javaPath, this.refPath);

		this.modClass = ModUtil.toClassName(this.modPath);
		this.refClass = ModUtil.toClassName(this.refPath);
		
		this.wrap = new PropWrapper();
		this.wrap.addProp("mod_class", this.modClass);
		this.wrap.addProp("ref_class", this.refClass);
		this.wrap.addProp("mod_package", this.modPack);
		this.wrap.addProp("ref_package", this.refPack);
	}

	@TaskAction
	public void runTask() throws IOException {
		initTask();
		ModUtil.copyResource(this.getClass(), "/com/rlonryan/quikmod/defaults/mcmod.info", this.infoPath);
		ModUtil.copyResource(this.getClass(), "/com/rlonryan/quikmod/defaults/reference.template", this.refPath, this.wrap::replace);
		ModUtil.copyResource(this.getClass(), "/com/rlonryan/quikmod/defaults/mod.template", this.modPath, this.wrap::replace);
	}

}
