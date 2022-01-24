package id.superautocash.mobile.api.controller.model

import id.superautocash.mobile.api.entity.BaseEntity

data class PaginationInfo(
    val count: Long,
    var next: String? = null,
    var pages: String? = null,
    var prev: String? = null
): BaseModel() {

    override fun toEntity(): BaseEntity? {
        return null
    }

}
