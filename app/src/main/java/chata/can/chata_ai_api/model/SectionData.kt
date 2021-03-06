package chata.can.chata_ai_api.model

import chata.can.chata_ai_api.R

object SectionData
{
	private val aCustom = ArrayList<Segment>()
	private val aTheme = ArrayList<Segment>()
	private val aPlacement = ArrayList<Segment>()
	private val aTabs = ArrayList<Segment>()
	private val aColors = ArrayList<Color>()

	init {
		with(aCustom)
		{
			add(Segment(R.id.btnReloadDrawer, "Reload Data Messenger", idResource = R.drawable.ic_reload))
			add(Segment(R.id.btnOpenDrawer, "Open Data Messenger", true, R.drawable.ic_menu_fold))
		}

		with(aTheme)
		{
			add(Segment(R.id.tvLight,"Light"))
			add(Segment(R.id.tvDark,"Dark", true))
		}

		with(aTabs)
		{
			add(Segment(R.id.tvDataMessenger, "data-messenger", true))
			add(Segment(R.id.tvExploreQueries, "explore-queries"))
		}

		with(aPlacement)
		{
			add(Segment(R.id.tvTop,"Top"))
			add(Segment(R.id.tvBottom,"Bottom"))
			add(Segment(R.id.tvLeft,"Left"))
			add(Segment(R.id.tvRight,"Right", true))
		}

		aColors.add(Color("0","#355C7D"))
		aColors.add(Color("1","#6C5B7B"))
		aColors.add(Color("2", "#C06C84"))
		aColors.add(Color("3", "#F67280"))
		aColors.add(Color("4", "#F8B195"))
	}

	val mData = linkedMapOf(
		"Authentication" to
			arrayListOf(
				//DemoParameter("Demo data", TypeParameter.TOGGLE, idView = R.id.swDemoData),
				DemoParameter("", TypeParameter.TOGGLE_QA, value = "false"),
				DemoParameter("* Project ID", TypeParameter.INPUT, labelId = R.id.hProjectId, idView = R.id.etProjectId),
				DemoParameter("Theme Code", TypeParameter.INPUT, labelId = R.id.hThemeColor, idView = R.id.etThemeColor, isVisible = false),
				DemoParameter("* User Email", TypeParameter.INPUT, labelId = R.id.hUserId, idView = R.id.etUserId, typeInput = TypeInput.EMAIL),
				DemoParameter("* API key", TypeParameter.INPUT, labelId = R.id.hApiKey, idView = R.id.etApiKey),
				DemoParameter("* Domain URL", TypeParameter.INPUT, labelId = R.id.hDomainUrl, idView = R.id.etDomainUrl),
				DemoParameter("* Username", TypeParameter.INPUT, labelId = R.id.hUsername, idView = R.id.etUsername),
				DemoParameter("* Password", TypeParameter.INPUT, labelId = R.id.hPassword, idView = R.id.etPassword, typeInput = TypeInput.PASSWORD),
				DemoParameter("Authenticate", TypeParameter.BUTTON, idView = R.id.btnAuthenticate)
			),
		"Customize Widgets" to
			arrayListOf(
				DemoParameter("", TypeParameter.SEGMENT, options = aCustom),
			),
		"AutoQl Api Configuration Options" to
			arrayListOf(
				DemoParameter("Enable Autocomplete", TypeParameter.TOGGLE, "true", idView = R.id.swEnableAutocomplete),
				DemoParameter("Enable Query Validation", TypeParameter.TOGGLE, "true", idView = R.id.swEnableQuery),
				DemoParameter("Enable Query Suggestions", TypeParameter.TOGGLE, "true", idView = R.id.swEnableSuggestion),
				DemoParameter("Enable Drilldowns", TypeParameter.TOGGLE, "true", idView = R.id.swEnableDrillDown),
				DemoParameter("Enable Notifications", TypeParameter.TOGGLE, "true", idView = R.id.swEnableNotification)
			),
		"UI Configuration Options" to
			arrayListOf(
				DemoParameter("Show Data Messenger Button", TypeParameter.TOGGLE, value = "true", idView = R.id.swDrawerHandle),
				//Shift Screen on Open/Close    TypeParameter.TOGGLE
				//Darken Background Behind Data Messenger   TypeParameter.TOGGLE
				DemoParameter("Darken Background Behind Data Messenger", TypeParameter.TOGGLE, "false", idView = R.id.swBackgroundBehind),
				DemoParameter("Theme", TypeParameter.SEGMENT, options = aTheme),
				DemoParameter("Data Messenger Placement", TypeParameter.SEGMENT, options = aPlacement),
				DemoParameter("Default Tab", TypeParameter.SEGMENT, options = aTabs),
				DemoParameter("Currency code", TypeParameter.INPUT, "USD", idView = R.id.etCurrencyCode),
				DemoParameter("Language code", TypeParameter.INPUT, "en-US", idView = R.id.etLanguageCode),
				DemoParameter("Format for Month, Year", TypeParameter.INPUT, "MMM YYYY", idView = R.id.etFormatMonthYear),
				DemoParameter("Format for Day, Month, Year", TypeParameter.INPUT, "MMM DD, YYYY", idView = R.id.etFormatDayMonthYear),
				DemoParameter("Number of Decimals for Currency Values", TypeParameter.INPUT, "2", idView = R.id.etDecimalsCurrency, typeInput = TypeInput.INTEGER),
				DemoParameter("Number of Decimals for Quantity Values", TypeParameter.INPUT, "1", idView = R.id.etDecimalsQuantity, typeInput = TypeInput.INTEGER),
				DemoParameter("User Display Name", TypeParameter.INPUT, idView = R.id.etCustomerMessage),
				DemoParameter("Intro Message ", TypeParameter.INPUT, idView = R.id.etIntroMessage),
				DemoParameter("Query Input PlaceHolder", TypeParameter.INPUT, idView = R.id.etQueryPlaceholder),
				DemoParameter("Clear All Messages on Close", TypeParameter.TOGGLE, idView = R.id.swClearMessage),
				DemoParameter("Title", TypeParameter.INPUT, "Data Messenger", idView = R.id.etTitle),
				//,DemoParameter("Font Family", TypeParameter.INPUT, "sans-serif")
				DemoParameter("This is an array of colors used for the charts.", TypeParameter.COLOR, colors = aColors, idView = R.id.llColors),
				DemoParameter("", TypeParameter.INPUT, idView = R.id.etAddColor, hint = "New Color"),
				//DemoParameter("Dashboard Title Color", TypeParameter.INPUT, idView = 0, hint = "#48698E"),
				DemoParameter("Dashboard Background Color", TypeParameter.COLOR, "#FAFAFA", idView = R.id.etDashboardColor, typeInput = TypeInput.COLOR),
				DemoParameter("Light Theme Accent Color", TypeParameter.COLOR, "#26A7DF", idView = R.id.etLightThemeColor, typeInput = TypeInput.COLOR),
				DemoParameter("Dark Theme Accent Color", TypeParameter.COLOR, "#26A7DF", idView = R.id.etDarkThemeColor, typeInput = TypeInput.COLOR),
				DemoParameter("Maximum Number of Messages", TypeParameter.INPUT, "10", idView = R.id.etMaxNumberMessage, typeInput = TypeInput.INTEGER),
				DemoParameter("Enable Explore Queries Tab", TypeParameter.TOGGLE, "true", idView = R.id.swTabExploreQueries),
				DemoParameter("Enable Notifications Tab", TypeParameter.TOGGLE, "true", idView = R.id.swTabNotification),
				DemoParameter("Enable Speech to Text", TypeParameter.TOGGLE, "true", idView = R.id.swEnableSpeechText)
			)
		//"Chat Colors"
	)
}