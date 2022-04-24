package chata.can.chata_ai.compose.di

import chata.can.chata_ai.compose.network.NotificationApi
import chata.can.chata_ai.compose.network.RuleQueryApi
import chata.can.chata_ai.compose.repository.NotificationRepository
import chata.can.chata_ai.compose.repository.RuleQueryRepository
import chata.can.chata_ai.pojo.api1
import chata.can.chata_ai.pojo.autoQL.AutoQLData
import chata.can.chata_ai.pojo.getMainURL
import chata.can.chata_ai.pojo.urlBase
import com.google.gson.GsonBuilder
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
		val url = AutoQLData.domainUrl.ifEmpty { urlBase }
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(NotificationApi::class.java)
	}

	@Singleton
	@Provides
	fun provideRuleQuery(api: RuleQueryApi) = RuleQueryRepository(api)

	@Singleton
	@Provides
	fun provideRuleQueryApi(): RuleQueryApi {
		val gson = GsonBuilder().setLenient().create()
		return Retrofit.Builder()
			.baseUrl("${getMainURL()}${api1}")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
			.create(RuleQueryApi::class.java)
	}
}