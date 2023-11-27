package com.adamsmolik.nba.base

private const val FLAVOR_PRODUCTION = "production"
private const val FLAVOR_STAGING = "staging"
private const val FLAVOR_DEVELOP = "develop"

private const val BUILD_TYPE_DEBUG = "debug"
private const val BUILD_TYPE_RELEASE = "release"
object CoreConfig {
    const val PRODUCTION_FLAVOR = BuildConfig.FLAVOR == FLAVOR_PRODUCTION
    const val STAGING_FLAVOR = BuildConfig.FLAVOR == FLAVOR_STAGING
    const val DEVELOP_FLAVOR = BuildConfig.FLAVOR == FLAVOR_DEVELOP

    const val DEBUG_BUILD_TYPE = BuildConfig.BUILD_TYPE == BUILD_TYPE_DEBUG
    const val RELEASE_BUILD_TYPE = BuildConfig.BUILD_TYPE == BUILD_TYPE_RELEASE

    const val FLAVOR = BuildConfig.FLAVOR

    object Paging {
        const val PREFETCH_OFFSET = 5
        const val INITIAL_OFFSET = 0
        const val PAGE_SIZE = 20
    }

    const val DATE_FORMATTER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}
