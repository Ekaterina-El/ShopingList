package com.elka.shopinglist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class])
interface AppComponent {

  fun activityComponentFactory(): ActivityComponent.Factory

  @Component.Factory
  interface Factory {
    fun create(
      @BindsInstance application: Application
    ): AppComponent
  }
}