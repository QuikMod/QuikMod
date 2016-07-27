/*
 */
package com.rlonryan.quikmod.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.function.Function;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;

/**
 *
 * @author RlonRyan
 */
public final class ModUtil {

	public static Properties getModProperties(Project project) {
		Object ext = project.getExtensions().getByName("ext");
		if (ext instanceof ExtraPropertiesExtension) {
			Object obj = ((ExtraPropertiesExtension)ext).get("mod");
			if (obj instanceof Properties) {
				return (Properties) obj;
			}
		}
		throw new Error("Missing Project Mod Properties!");
	}

	public static String asInsert(String key) {
		return "${" + key + "}";
	}

	public static String toClassName(Path path) {
		String temp = path.getFileName().toString();
		return temp.substring(0, temp.lastIndexOf("."));
	}

	public static String toPackageName(Path root, Path path) {
		return root.relativize(path.getParent()).toString().replaceAll("\\\\", "/").replaceAll("/", ".");
	}

	public static boolean makeFile(File f) throws IOException {
		// Create Parent Directories
		f.toPath().getParent().toFile().mkdirs();
		// Create Actual File
		return f.createNewFile();
	}

	public static BufferedReader getResourceReader(Class clazz, String path) {
		InputStream inStream = clazz.getResourceAsStream(path);
		InputStreamReader inStreamReader = new InputStreamReader(inStream);
		return new BufferedReader(inStreamReader);
	}

	public static void copyResource(Class clazz, String from, Path to) throws IOException {
		if (!to.toFile().exists()) {
			to.getParent().toFile().mkdirs();
			try (BufferedReader reader = getResourceReader(clazz, from); BufferedWriter writer = Files.newBufferedWriter(to, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
				String line;
				while ((line = reader.readLine()) != null) {
					writer.append(line);
					writer.newLine();
				}
			}
		}
	}

	public static void copyResource(Class clazz, String from, Path to, Function<String, String> inject) throws IOException {
		if (!to.toFile().exists()) {
			to.getParent().toFile().mkdirs();
			try (BufferedReader reader = getResourceReader(clazz, from); BufferedWriter writer = Files.newBufferedWriter(to, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
				String line;
				while ((line = reader.readLine()) != null) {
					writer.append(inject.apply(line));
					writer.newLine();
				}
			}
		}
	}

}
