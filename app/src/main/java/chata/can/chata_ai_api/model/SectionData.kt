package chata.can.chata_ai_api.model

import chata.can.chata_ai_api.R

object SectionData
{
	private val aTheme = ArrayList<Segment>()
	private val aPlacement = ArrayList<Segment>()
	private val aColors = ArrayList<Color>()

	init {
		with(aTheme)
		{
			add(Segment(R.id.tvLight,"Light", true))
			add(Segment(R.id.tvDark,"Dark"))
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
				DemoParameter("Demo data", TypeParameter.TOGGLE, idView = R.id.swDemoData),

				DemoParameter("Project ID *", TypeParameter.INPUT, labelId = R.id.hProjectId, idView = R.id.etProjectId),
				DemoParameter("User ID (email) *", TypeParameter.INPUT, labelId = R.id.hUserId,idView = R.id.etUserId),
				DemoParameter("API key *", TypeParameter.INPUT, labelId = R.id.hApiKey, idView = R.id.etApiKey),
				DemoParameter("Domain URL *", TypeParameter.INPUT, labelId = R.id.hDomainUrl, idView = R.id.etDomainUrl),
				DemoParameter("Username *", TypeParameter.INPUT, labelId = R.id.hUsername, idView = R.id.etUsername),
				DemoParameter("Password *", TypeParameter.INPUT, labelId = R.id.hPassword, idView = R.id.etPassword),
				DemoParameter("Authenticate", TypeParameter.BUTTON, idView = R.id.btnAuthenticate)
			),
		"Drawer Props" to
			arrayListOf(
				DemoParameter("Reload Drawer", TypeParameter.BUTTON, idView = R.id.btnReloadDrawer),
				DemoParameter("Open Drawer", TypeParameter.BUTTON, idView = R.id.btnOpenDrawer),
				DemoParameter("Show Drawer Handle", TypeParameter.TOGGLE, value = "true", idView = R.id.swDrawerHandle),
				DemoParameter("Theme", TypeParameter.SEGMENT, options = aTheme),
				DemoParameter("Drawer Placement", TypeParameter.SEGMENT, options = aPlacement),
				DemoParameter("Currency code", TypeParameter.INPUT, "USD", idView = R.id.etCurrencyCode),
				DemoParameter("Language code", TypeParameter.INPUT, "en-US", idView = R.id.etLanguageCode),
				DemoParameter("Format for Month, Year", TypeParameter.INPUT, "MMM YYYY", idView = R.id.etFormatMonthYear),
				DemoParameter("Format for Day, Month, Year", TypeParameter.INPUT, "MMM DD, YYYY", idView = R.id.etFormatDayMonthYear),
				DemoParameter("Number of Decimals for Currency Values", TypeParameter.INPUT, "2", idView = R.id.etDecimalsCurrency),
				DemoParameter("Number of Decimals for Quantity Values", TypeParameter.INPUT, "1", idView = R.id.etDecimalsQuantity),
				DemoParameter("Customer Name", TypeParameter.INPUT, idView = R.id.etCustomerMessage),
				DemoParameter("Intro Message", TypeParameter.INPUT, idView = R.id.etIntroMessage),
				DemoParameter("Query Input PlaceHolder", TypeParameter.INPUT, idView = R.id.etQueryPlaceholder),
				DemoParameter("Clear All Messages on Close", TypeParameter.TOGGLE, idView = R.id.swClearMessage),
				DemoParameter("Title", TypeParameter.INPUT, "Data Messenger", idView = R.id.etTitle)
				//,DemoParameter("Font Family", TypeParameter.INPUT, "sans-serif"),
				//DemoParameter("Show Drawer Handle", TypeParameter.TOGGLE)
			),
		"Chat Colors" to
			arrayListOf(
				DemoParameter("This an array of colors used for the charts...", TypeParameter.COLOR, colors = aColors, idView = R.id.llColors),
				DemoParameter("", TypeParameter.INPUT, idView = R.id.etAddColor, hint = "New Color"),
				DemoParameter("Light Theme Accent Color", TypeParameter.COLOR, "#28A8E0", idView = R.id.etLightThemeColor),
				DemoParameter("Dark Theme Accent Color", TypeParameter.COLOR, "#525252", idView = R.id.etDarkThemeColor)
			),
		"More Configurations" to
			arrayListOf(
				DemoParameter("Maximum Number of Message", TypeParameter.INPUT, "10", idView = R.id.etMaxNumberMessage),
				DemoParameter("Enable Autocomplete", TypeParameter.TOGGLE, "true", idView = R.id.swEnableAutocomplete),
				DemoParameter("Enable Query Validation", TypeParameter.TOGGLE, "true", idView = R.id.swEnableQuery),
				DemoParameter("Enable Query Suggestions", TypeParameter.TOGGLE, "true", idView = R.id.swEnableSuggestion),
				DemoParameter("Enable Drilldowns", TypeParameter.TOGGLE, "true", idView = R.id.swEnableDrillDown),
				DemoParameter("Enable Speech to Text", TypeParameter.TOGGLE, "true", idView = R.id.swEnableSpeechText),
				DemoParameter("Debug Mode - Show copy to SQL button in message toolbar", TypeParameter.TOGGLE, "true"),
				DemoParameter("Test Mode (Provides extra logging on the server side)", TypeParameter.TOGGLE, "true")
			)
	)
}