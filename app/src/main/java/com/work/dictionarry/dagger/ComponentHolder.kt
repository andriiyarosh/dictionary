package com.work.dictionarry.dagger

object ComponentHolder {

    private lateinit var component: Component

    fun getApplicationComponent(): Component {
        if (this::component.isInitialized.not())
            component = DaggerComponent.create()
        return component
    }
}