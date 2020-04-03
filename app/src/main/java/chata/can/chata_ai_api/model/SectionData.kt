package chata.can.chata_ai_api.model

import chata.can.chata_ai_api.R

object SectionData
{
	private val saTheme = ArrayList<Segment>()
	private val saPlacement = ArrayList<Segment>()

	init {
		saTheme.add(Segment(R.id.tvLight,"Light", true))
		saTheme.add(Segment(R.id.tvDark,"Dark"))

		saPlacement.add(Segment(R.id.tvTop,"Top"))
		saPlacement.add(Segment(R.id.tvBottom,"Bottom"))
		saPlacement.add(Segment(R.id.tvLeft,"Left"))
		saPlacement.add(Segment(R.id.tvRight,"Right", true))
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
				DemoParameter("Authenticate", TypeParameter.BUTTON, idView = R.id.btnAuthenticate),
				DemoParameter("Log Out", TypeParameter.BUTTON, idView = R.id.btnLogOut)
			),
		"Drawer Props" to
			arrayListOf(
				DemoParameter("Reload Drawer", TypeParameter.BUTTON),
				DemoParameter("Open Drawer", TypeParameter.BUTTON, idView = R.id.btnOpenDrawer),
				DemoParameter("Show Drawer Handle", TypeParameter.TOGGLE, value = "true", idView = R.id.swDrawerHandle),
				DemoParameter("Shift Screen on Open/Close", TypeParameter.TOGGLE),
				DemoParameter("Theme", TypeParameter.SEGMENT, options = saTheme),
				DemoParameter("Drawer Placement", TypeParameter.SEGMENT, options = saPlacement),
				DemoParameter("Currency code", TypeParameter.INPUT, "USD", idView = R.id.etCurrencyCode),
				DemoParameter("Language code", TypeParameter.INPUT, "en-US", idView = R.id.etLanguageCode),
				DemoParameter("Format for Month, Year", TypeParameter.INPUT, "MMM YYYY", idView = R.id.etFormatMonthYear),
				DemoParameter("Format for Day, Month, Year", TypeParameter.INPUT, "MMM DD, YYYY", idView = R.id.etFormatDayMonthYear),
				DemoParameter("Number of Decimals for Currency Values", TypeParameter.INPUT, "2", idView = R.id.etDecimalsCurrency),
				DemoParameter("Number of Decimals for Quantity Values", TypeParameter.INPUT, "1", idView = R.id.etDecimalsQuantity),
				DemoParameter("Customer Name", TypeParameter.INPUT, "Jose Carlos", idView = R.id.tvCustomerMessage),
				DemoParameter("Intro Message", TypeParameter.INPUT, idView = R.id.etIntroMessage),
				DemoParameter("Clear All Messages on Close", TypeParameter.TOGGLE),
				DemoParameter("Height", TypeParameter.INPUT, "550"),
				DemoParameter("Width", TypeParameter.INPUT, "500"),
				DemoParameter("Title", TypeParameter.INPUT, "Data Messenger"),
				DemoParameter("Font Family", TypeParameter.INPUT, "sans-serif"),
				DemoParameter("Show Drawer Handle", TypeParameter.TOGGLE)
			),
		"Chat Colors" to
			arrayListOf(
				DemoParameter("1", TypeParameter.COLOR, "#355C7D"),
				DemoParameter("2", TypeParameter.COLOR, "#6C5B7B"),
				DemoParameter("3", TypeParameter.COLOR, "#C06C84"),
				DemoParameter("4", TypeParameter.COLOR, "#F67280"),
				DemoParameter("5", TypeParameter.COLOR, "#F8B195"),
				DemoParameter("Dashboard Title Color", TypeParameter.COLOR, "#2466AE"),
				DemoParameter("AccentColor", TypeParameter.COLOR, "#28A8E0"),
				DemoParameter("Dark Theme Accent Color", TypeParameter.COLOR, "#525252")
			),
		"More Configurations" to
			arrayListOf(
				DemoParameter("Maximum Number of Message", TypeParameter.INPUT, "6"),
				DemoParameter("Display comparisons as Percent", TypeParameter.TOGGLE, "true"),
				DemoParameter("Enable Autocomplete", TypeParameter.TOGGLE, "true"),
				DemoParameter("Enable Safety Net Autocomplete", TypeParameter.TOGGLE, "true"),
				DemoParameter("Disable Drilldowns", TypeParameter.TOGGLE, "false"),
				DemoParameter("Enable Query Inspiration Tab", TypeParameter.TOGGLE, "true"),
				DemoParameter("Enable Speech to Text", TypeParameter.TOGGLE, "true"),
				DemoParameter("Debug Mode - Show copy to SQL button in message toolbar", TypeParameter.TOGGLE, "true"),
				DemoParameter("Test Mode (Provides extra logging on the server side)", TypeParameter.TOGGLE, "true")
			)
	)
}