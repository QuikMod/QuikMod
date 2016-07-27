package com.rlonryan.quikmod

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import com.rlonryan.quikmod.tasks.*;
import static org.junit.Assert.*

class ModTasksTest {
	
    @Test
    public void canAddTasksToProject() {
        Project project = ProjectBuilder.builder().build()
		
		// Mod Info
        def task = project.task('modInfo', type: ModInfoTask)
        assertTrue(task instanceof ModInfoTask)
		
		// Netbeans
		task = project.task('netbeans', type: NetbeansTask)
        assertTrue(task instanceof NetbeansTask)
		
		// Travis
		task = project.task('travis', type: TravisTask)
        assertTrue(task instanceof TravisTask)
		
		// Defaults
		task = project.task('generate', type: GenerateTask)
        assertTrue(task instanceof GenerateTask)
    }

}
