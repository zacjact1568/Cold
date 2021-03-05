package net.zackzhang.code.haze.air.model.entity

data class AirEntity(val now: AirNowEntity, val stations: List<AirNowEntity>) {

    fun attachCityId(cityId: String) {
        now.cityId = cityId
        stations.forEach { it.cityId = cityId }
    }
}
