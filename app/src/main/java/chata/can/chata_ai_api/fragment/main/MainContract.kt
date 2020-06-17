package chata.can.chata_ai_api.fragment.main

interface MainContract
{
	fun changeStateAuthenticate()
	fun callJWt()
	fun changeAuthenticate(isAuthenticate: Boolean)
	fun isEnableLogin(isEnable: Boolean)
	fun savePersistentData()
	fun showError(errorCode: String, errorService: String)
}