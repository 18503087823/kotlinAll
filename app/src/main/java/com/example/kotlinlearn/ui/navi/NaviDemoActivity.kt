package com.example.kotlinlearn.ui.navi

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.example.kotlinlearn.databinding.ActivityNaviDemoBinding

/**
 * ## NaviDemoActivity — 高德地图导航演示
 *
 * - 打开显示地图 + 定位蓝点
 * - 「定位当前位置」→ GPS 单次定位，地图飞到当前位置
 * - 「开始导航」→ 当前位置作起点 → 拉起高德 APP 导航页，终点让用户自己搜索输入
 *
 * 高德 Key：a8eae19e3637dc7789d3612102842ba1
 */
class NaviDemoActivity : AppCompatActivity(), AMapLocationListener {

    private lateinit var b: ActivityNaviDemoBinding
    private var mapView: MapView? = null
    private var locationClient: AMapLocationClient? = null
    private var myLat: Double? = null
    private var myLng: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityNaviDemoBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.toolbar.setNavigationOnClickListener { finish() }

        // 地图
        mapView = MapView(this)
        b.mapContainer.addView(mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.map?.apply {
            isMyLocationEnabled = true
            myLocationStyle = MyLocationStyle()
                .myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
                .strokeColor(Color.TRANSPARENT)
                .radiusFillColor(Color.parseColor("#301566C0"))
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isCompassEnabled = true
            uiSettings.isScaleControlsEnabled = true
            moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(39.9042, 116.3974), 12f))
        }

        // 定位客户端
        initLocation()

        b.btnRoute.setOnClickListener { locateMe() }
        b.btnStartNavi.setOnClickListener { startNavi() }
    }

    // ── 定位 ─────────────────────────────────────────────────────────────────

    private fun initLocation() {
        locationClient = AMapLocationClient(applicationContext).apply {
            setLocationOption(AMapLocationClientOption().apply {
                locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                isOnceLocation = true
                interval = 2000
            })
            setLocationListener(this@NaviDemoActivity)
        }
    }

    private fun locateMe() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        Toast.makeText(this, "正在获取当前位置...", Toast.LENGTH_SHORT).show()
        locationClient?.startLocation()
    }

    override fun onLocationChanged(location: AMapLocation?) {
        if (location == null || location.errorCode != 0) {
            Toast.makeText(this, "定位失败: ${location?.errorInfo ?: "未知"}", Toast.LENGTH_SHORT).show()
            return
        }
        myLat = location.latitude
        myLng = location.longitude
        mapView?.map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(myLat!!, myLng!!), 18f)
        )
        Toast.makeText(this, "已定位 (±${location.accuracy.toInt()}m)", Toast.LENGTH_SHORT).show()
    }

    // ── 导航 ─────────────────────────────────────────────────────────────────

    /**
     * 拉起高德 APP 导航搜索页
     *
     * 只传起点（当前位置），终点留空 → 高德 APP 打开后让用户自己搜终点。
     *
     * URI 参数说明：
     * - slat/slon/sname = 起点坐标和名称
     * - 不传 dlat/dlon → 高德默认打开导航搜索页，等待用户输入终点
     * - t=0 → 驾车
     */
    private fun startNavi() {
        // 先定位一次再跳
        if (myLat == null || myLng == null) {
            Toast.makeText(this, "请先点击「定位当前位置」获取位置", Toast.LENGTH_SHORT).show()
            return
        }

        val sname = Uri.encode("我的位置")
        // 只传起点，不传终点 → 高德打开后让用户自己搜终点
        val uri = "amapuri://route/plan/?slat=$myLat&slon=$myLng&sname=$sname&t=0"
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
            return
        }

        // 降级：网页版（同样只传起点）
        val webUri = "https://uri.amap.com/navigation?from=$myLng,$myLat,我的位置&mode=car&callnative=1"
        startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(webUri)))
    }

    // ── 权限回调 ─────────────────────────────────────────────────────────────

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) locateMe()
    }

    // ── 生命周期 ─────────────────────────────────────────────────────────────

    override fun onResume()  { super.onResume(); mapView?.onResume() }
    override fun onPause()   { super.onPause(); mapView?.onPause() }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
        locationClient?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
