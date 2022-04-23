package chata.can.chata_ai.compose.di

import chata.can.chata_ai.compose.network.NotificationApi
import chata.can.chata_ai.compose.repository.NotificationRepository
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Singleton
	@Provides
	fun provideNotificationRepository(api: NotificationApi) = NotificationRepository(api)

	@Singleton
	@Provides
	fun provideNotificationApi(): NotificationApi {
		val url = AutoQLData.domainUrl
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(NotificationApi::class.java)
	}
}