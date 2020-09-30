package chata.can.chata_ai_api.fragment.main

interface MainContract
{
	fun changeStateAuthenticate()
	fun callJWt()
	fun callRelated()
	fun callTopics()
	fun initPollService()
	fun changeAuthenticate(isAuthenticate: Boolean)
	fun isEnableLogin(isEnable: Boolean)
	fun savePersistentData()

	fun showAlert(message: String, intRes: Int)
	//fun showError(errorCode: String, errorService: String)
}