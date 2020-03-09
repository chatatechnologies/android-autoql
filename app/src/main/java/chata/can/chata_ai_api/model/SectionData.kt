package chata.can.chata_ai_api.model

import android.util.SparseArray
import chata.can.chata_ai_api.R

object SectionData
{
	private val saTheme = SparseArray<String>()
	private val saPlacement = SparseArray<String>()

	init {
		saPlacement.put(R.id.tvTop, "Top")
		saPlacement.put(R.id.tvBottom, "tvBottom")
		saPlacement.put(R.id.tvLeft, "tvLeft")
		saPlacement.put(R.id.tvRight, "tvRight")
	}

	val mData = linkedMapOf(
		"Data Source" to
			arrayListOf(
				DemoParameter("Demo data", TypeParameter.TOGGLE),
				DemoParameter("Show UI Overlay", TypeParameter.TOGGLE)
			),
		"You must login to access data" to
			arrayListOf(
				DemoParameter("Customer ID *", TypeParameter.INPUT),
				DemoParameter("User ID (email) *", TypeParameter.INPUT),
				DemoParameter("API key *", TypeParameter.INPUT),
				DemoParameter("Domain URL *", TypeParameter.INPUT),
				DemoParameter("Username *", TypeParameter.INPUT),
				DemoParameter("Password *", TypeParameter.INPUT),
				DemoParameter("Authenticate", TypeParameter.BUTTON)
			),
		"Drawer Props" to
			arrayListOf(
				DemoParameter("Reload Drawer", TypeParameter.BUTTON),
				DemoParameter("Open Drawer", TypeParameter.BUTTON),
				DemoParameter("Show Drawer Handle", TypeParameter.TOGGLE),
				DemoParameter("Shift Screen on Open/Close", TypeParameter.TOGGLE),
				DemoParameter("Theme", TypeParameter.SEGMENT, options = saTheme),
				DemoParameter("Drawer Placement", TypeParameter.SEGMENT, options = saPlacement),
				DemoParameter("Currency code", TypeParameter.INPUT, "USD"),
				DemoParameter("Language code", TypeParameter.INPUT, "en-US"),
				DemoParameter("Format for Month, Year", TypeParameter.INPUT, "MMM YYYY"),
				DemoParameter("Format for Day, Month, Year", TypeParameter.INPUT, "MMM DD, YYYY"),
				DemoParameter("Number of Decimals for Currency Values", TypeParameter.INPUT, "2"),
				DemoParameter("Number of Decimals for Quantity Values", TypeParameter.INPUT, "1"),
				DemoParameter("Customer Name", TypeParameter.INPUT, "Carlos"),
				DemoParameter("Intro Message", TypeParameter.INPUT),
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