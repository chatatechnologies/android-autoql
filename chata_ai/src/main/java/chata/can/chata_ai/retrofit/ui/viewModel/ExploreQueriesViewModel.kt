package chata.can.chata_ai.retrofit.ui.viewModel

import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.data.model.ExploreQueriesProvider
import chata.can.chata_ai.retrofit.data.model.relatedQueries.RelatedQueryPagination
import chata.can.chata_ai.retrofit.domain.GetRelatedQueryUseCase
import chata.can.chata_ai.retrofit.domain.GetValidateQueryUseCase
import kotlinx.coroutines.launch
import java.net.URLEncoder

class ExploreQueriesViewModel: ViewModel() {

	//region possible data on companion object

	//endregion

	var colorSecondary = 0
	private var backgroundColor = 0
	private var borderColor = 0
	var textColorPrimary = 0

	val isVisibleGif = MutableLiveData<Boolean>()
	val isVisibleMsg1 = MutableLiveData<Boolean>()
	val isVisibleMsg2 = MutableLiveData<Boolean>()
	val isVisibleList = MutableLiveData<Boolean>()
	val itemList = MutableLiveData<List<String>>()
	val relatedQueryPagination = MutableLiveData<RelatedQueryPagination>()

	init {
		ThemeColor.currentColor.run {
			colorSecondary = pDrawerColorSecondary
			backgroundColor = pDrawerBackgroundColor
			borderColor = drawerBorderColor
			textColorPrimary = pDrawerTextColorPrimary
		}
	}

	fun getDrawableQuery(): GradientDrawable {
		return DrawableBuilder.setGradientDrawable(backgroundColor, 64f, 1, borderColor)
	}

	fun getAccentColor() = SinglentonDrawer.currentAccent

	private val getValidateQueryUseCase = GetValidateQueryUseCase()
	private val getRelatedQueryUseCase = GetRelatedQueryUseCase()

	fun validateQuery(query: String) {
		viewModelScope.launch {
			isVisibleGif.postValue(true)
			isVisibleList.postValue(false)

			val validateQueryData = getValidateQueryUseCase.validateQuery(query)
			if (validateQueryData.replacements.isEmpty()) {
				relatedQuery(query)
			} else {
				itemList.postValue(listOf())
				isVisibleGif.postValue(false)
				isVisibleMsg1.postValue(true)
				message2Gone()
			}
		}
	}

	fun relatedQuery(
		query: String,
		pageSize: Int = 12,
		page: Int = 1
	) {
		if (isVisibleList.value != false)
			isVisibleList.postValue(false)
		isVisibleGif.postValue(true)

		val queryEncoded = URLEncoder.encode(query, "UTF-8").replace("+", " ")
		viewModelScope.launch {
			val relatedQueryData = getRelatedQueryUseCase.getRelatedQuery(queryEncoded, pageSize, page)

			relatedQueryData.run {
				ExploreQueriesProvider.lastQuery = query
				ExploreQueriesProvider.itemList.run {
					clear()
					addAll(items)
				}

				itemList.postValue(items)
				relatedQueryPagination.postValue(pagination)
				gifGone()
				isVisibleMsg1.postValue(false)
				message2Gone()
				isVisibleList.postValue(true)
			}
		}
	}

	private fun gifGone() {
		isVisibleGif.postValue(false)
	}

	private fun message2Gone() {
		isVisibleMsg2.postValue(false)
	}
}