package chata.can.chata_ai_api.fragment.main

interface MainContract
{
	fun callJWt()
	fun changeAuthenticate(isAuthenticate: Boolean)
	fun changeStateAuthenticate()
	fun getDashboards()
}