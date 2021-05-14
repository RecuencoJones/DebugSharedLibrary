package com.debug.shared.library

class MyPipelines {
    private steps

    MyPipelines(def steps) {
        this.steps = steps
    }

    void setDefaultProperties() {
        steps.properties([])

        steps.echo 'Configured job properties'
    }
}