package chata.can.chata_ai.screens.exploreQueries

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.compose.data.DataOrException
import chata.can.chata_ai.compose.model.RelatedQueriesResponse
import chata.can.chata_ai.compose.model.ValidateQueryResponse
import chata.can.chata_ai.compose.repository.RelatedQueriesRepository
import chata.can.chata_ai.compose.repository.ValidateQueryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class ExploreQueriesViewModel @Inject constructor(
	private val validateQueryRepository: ValidateQueryRepository,
	private val relatedQueriesRepository: RelatedQueriesRepository
) : ViewModel() {
	val data: MutableState<DataOrException<ValidateQueryResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))
	val relatedQueryData: MutableState<DataOrException<RelatedQueriesResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))

	fun validateQuery(query: String) {
		viewModelScope.launch {
			data.value.loading = true
			data.value = validateQueryRepository.validateQuery(query = query)
			data.value.loading = false

			val replacements = data.value.data?.data?.replacements ?: listOf()
			if (replacements.isEmpty()) {
				relatedQuery(query = query)
			}
		}
	}

	fun relatedQuery(query: String, pageSize: Int = 12, page: Int = 1) {
		val queryEncoded = URLEncoder.encode(query, "UTF-8").replace("+", " ")
		viewModelScope.launch {
			relatedQueryData.value.loading = true
			relatedQueryData.value = relatedQueriesRepository.getRelatedQueries(
				search = queryEncoded,
				pageSize = pageSize,
				page = page
			)
			relatedQueryData.value.loading = false
		}
	}
}