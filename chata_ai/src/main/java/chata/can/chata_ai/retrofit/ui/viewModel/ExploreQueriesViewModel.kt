package chata.can.chata_ai.retrofit.ui.viewModel

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chata.can.chata_ai.extension.dpToPx
import chata.can.chata_ai.pojo.SinglentonDrawer
import chata.can.chata_ai.pojo.color.ThemeColor
import chata.can.chata_ai.pojo.tool.DrawableBuilder
import chata.can.chata_ai.retrofit.domain.GetRelatedQueryUseCase
import chata.can.chata_ai.retrofit.domain.GetValidateQueryUseCase
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.log10

class ExploreQueriesViewModel: ViewModel() {

	//region possible data on companion object

	//endregion

	var colorSecondary = 0
	var backgroundColor = 0
	var borderColor = 0
	var textColorPrimary = 0

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

	fun getDrawableSearch(): GradientDrawable {
		return DrawableBuilder.setOvalDrawable(getAccentColor())
	}

	fun getAccentColor() = SinglentonDrawer.currentAccent

	private val getValidateQueryUseCase = GetValidateQueryUseCase()
	private val getRelatedQueryUseCase = GetRelatedQueryUseCase()

	fun validateQuery(query: String) {
		viewModelScope.launch {
			val validateQueryData = getValidateQueryUseCase.validateQuery(query)
			if (validateQueryData.replacements.isEmpty()) {
				val relatedQueryData = getRelatedQueryUseCase.getRelatedQuery(query)
				relatedQueryData.pagination
			}

//		viewModelScope.launch {
//			val retrofit = Retrofit.Builder()
//				.baseUrl("${AutoQLData.domainUrl}/autoql/$api1")
//				.addConverterFactory(GsonConverterFactory.create())
//				.build()
//
//			withContext(Dispatchers.IO) {
//				try {
//					val apiService = retrofit.create(RelatedQueriesApiClient2::class.java)
//					val call = apiService.getRelatedQuery(
//						beaverToken = "Bearer ${AutoQLData.JWT}",
//						acceptLanguage = SinglentonDrawer.languageCode,
//						apiKey = AutoQLData.apiKey,
//						search = query,
//						pageSize = 7,
//						page = 1
//					)
//					call.body()
//
//					toString()
//				} catch (ex: Exception) {
//					ex.printStackTrace()
//					toString()
//				}
//			}
//		}

//		call.enqueue(object: Callback<GenericResponse> {
//			override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
//				response
//			}
//
//			override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
//				t
//			}
//		})

//			val result = getValidateQueryUseCase.validateQuery(query)
//			if (result.replacements.isEmpty()) {
//				// getRelatedQueries
//				val result1 = getRelatedQueryUseCase.getRelatedQuery(query)
//				result1.items
//				result1.pagination
//			}
		}
	}

	companion object {
		fun Int.length() = when(this) {
			0 -> 1
			else -> log10(abs(toDouble())).toInt() + 1
		}

		@JvmStatic
		@BindingAdapter("ovalBackground")
		fun setOvalBackground(view: View, drawable: Drawable?) {
			val count = 1

			val gradientDrawable = DrawableBuilder.setOvalDrawable(SinglentonDrawer.currentAccent)
			val height = view.dpToPx(30f)
			val width = view.dpToPx(25f + (count.length() * 5))//count = 1
			gradientDrawable.setSize(width, height)
			view.background = gradientDrawable
		}
	}
}