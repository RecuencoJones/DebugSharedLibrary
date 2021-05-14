package com.debug.shared.library

import com.debug.shared.library.util.BasePipelineTest

import org.junit.Test

class MyPipelinesTest extends BasePipelineTest {
    @Test
    void 'it should run example Jenkinsfile successfully' () {
        runScript('examples/Jenkinsfile')

        printCallStack()

        assertJobStatusSuccess()
    }
}