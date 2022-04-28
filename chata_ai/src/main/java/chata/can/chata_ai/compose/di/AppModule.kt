package chata.can.chata_ai.compose.di

import chata.can.chata_ai.compose.network.AutocompleteApi
import chata.can.chata_ai.compose.network.NotificationApi
import chata.can.chata_ai.compose.network.RelatedQueriesApi
import chata.can.chata_ai.compose.network.ValidateQueryApi
import chata.can.chata_ai.compose.repository.AutocompleteRepository
import chata.can.chata_ai.compose.repository.NotificationRepository
import chata.can.chata_ai.compose.repository.RelatedQueriesRepository
import chata.can.chata_ai.compose.repository.ValidateQueryRepository
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
	fun provideRetrofit(): Retrofit {
		val gson = GsonBuilder().setLenient().create()
		return Retrofit.Builder()
			.baseUrl("${getMainURL()}${api1}")
			.addConverterFactory(GsonConverterFactory.create(gson))
			.build()
	}

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
	fun provideValidateQueryRepository(api: ValidateQueryApi) = ValidateQueryRepository(api)

	@Singleton
	@Provides
	fun provideValidateQueryApi(): ValidateQueryApi {
		val url = AutoQLData.domainUrl.ifEmpty { urlBase }
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(ValidateQueryApi::class.java)
	}

	@Singleton
	@Provides
	fun provideRelatedQueriesRepository(api: RelatedQueriesApi) = RelatedQueriesRepository(api)

	@Singleton
	@Provides
	fun provideRelatedQueries(): RelatedQueriesApi {
		val url = AutoQLData.domainUrl.ifEmpty { urlBase }
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(RelatedQueriesApi::class.java)
	}

	@Singleton
	@Provides
	fun provideAutocompleteRepository(api: AutocompleteApi) = AutocompleteRepository(api)

	@Singleton
	@Provides
	fun provideAutocomplete(): AutocompleteApi {
		val url = AutoQLData.domainUrl.ifEmpty { urlBase }
		return Retrofit.Builder()
			.baseUrl("${url}/autoql/$api1")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(AutocompleteApi::class.java)
	}
}