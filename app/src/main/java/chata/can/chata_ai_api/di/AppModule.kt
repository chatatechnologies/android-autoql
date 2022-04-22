package chata.can.chata_ai_api.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Singleton
	@Provides
	fun provideAppPreference(@ApplicationContext context: Context): SharedPreferences =
		context.applicationContext.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
}