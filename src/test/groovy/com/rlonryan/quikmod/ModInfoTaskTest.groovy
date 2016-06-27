package com.rlonryan.quikmod

import org.junit.Test
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import static org.junit.Assert.*

class ModInfoTaskTest {
	
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('modInfo', type: ModInfoTask)
        assertTrue(task instanceof ModInfoTask)
    }

}
