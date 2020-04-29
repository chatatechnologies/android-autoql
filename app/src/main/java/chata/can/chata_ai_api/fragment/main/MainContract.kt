package chata.can.chata_ai_api.fragment.main

interface MainContract
{
	fun showError(errorCode: String, errorService: String)
	fun callJWt()
	fun changeAuthenticate(isAuthenticate: Boolean)
	fun isEnableLogin(isEnable: Boolean)
	fun changeStateAuthenticate()
	fun getDashboards()
}