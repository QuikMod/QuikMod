package com.rlonryan.quikmod

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class QuikModTest {
	
    @Test
    public void testAddTasksToProject() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.rlonryan.quikmod'
        assertTrue(project.tasks.modInfo instanceof ModInfoTask)
    }
	
}
