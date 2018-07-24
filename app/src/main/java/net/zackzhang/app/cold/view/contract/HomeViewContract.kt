package net.zackzhang.app.cold.view.contract

interface HomeViewContract : BaseViewContract {

    fun showInitialView()

    fun showInitialFragment(restored: Boolean, shownTag: String)

    fun showToast(msg: String)

    fun switchFragment(fromTag: String, toTag: String)

    fun startActivity(tag: String)

    fun closeDrawer()

    fun onPressBackKey()

    fun onDetectedNetworkNotAvailable()

    fun onDetectedSystemLocationServiceDisabled()

    fun onDetectedNoEnoughPermissionsGranted()

    fun onLocationServicePermissionsDenied()
}
