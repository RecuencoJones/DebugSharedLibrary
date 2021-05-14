package com.debug.shared.library.util

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.ProjectSource.projectSource

import com.lesfurets.jenkins.unit.BasePipelineTest as SourceBasePipelineTest

import org.junit.Before

class BasePipelineTest extends SourceBasePipelineTest {

    @Override
    @Before
    void setUp() {
        super.setUp()

        def debugSharedLibrary = library()
                        .name('DebugSharedLibrary')
                        .retriever(projectSource())
                        .defaultVersion('<notNeeded>')
                        .targetPath('<notNeeded>')
                        .allowOverride(true)
                        .implicit(false)
                        .build()

        helper.registerSharedLibrary(debugSharedLibrary)
    }
}