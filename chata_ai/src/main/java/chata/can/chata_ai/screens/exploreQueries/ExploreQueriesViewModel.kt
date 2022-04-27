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
	private val validateQueryData: MutableState<DataOrException<ValidateQueryResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, true, Exception("")))
	val relatedQueryData: MutableState<DataOrException<RelatedQueriesResponse, Boolean, Exception>> =
		mutableStateOf(DataOrException(null, null, Exception("")))
	var loading: MutableState<Boolean> = mutableStateOf(false)
	private var queryRequested = ""

	fun validateQuery(query: String) {
		viewModelScope.launch {
			loading.value = true
			validateQueryData.value = validateQueryRepository.validateQuery(query = query)
			val replacements = validateQueryData.value.data?.data?.replacements ?: listOf()
			if (replacements.isEmpty()) {
				relatedQuery(query = query)
			}
		}
	}

	fun relatedQuery(query: String, pageSize: Int = 12, page: Int = 1) {
		if (queryRequested.isEmpty()) {
			queryRequested = query
		}
		relatedQueryData.value.data = null
		if (!loading.value) {
			loading.value = true
		}
		val queryEncoded = URLEncoder.encode(queryRequested, "UTF-8").replace("+", " ")
		viewModelScope.launch {
			val dataOrException = relatedQueriesRepository.getRelatedQueries(
				search = queryEncoded,
				pageSize = pageSize,
				page = page
			)
			relatedQueryData.value = dataOrException
			loading.value = false
		}
	}
}