package zhest.yan.prefetch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import zhest.yan.core.db.Database
import zhest.yan.core.db.entity.HeroEntity
import zhest.yan.core.entities.ProcessState
import javax.inject.Inject

private const val RES_HOST = "https://cdn.cloudflare.steamstatic.com/"

class PreFetchStaticDataUseCase @Inject constructor(
    retrofit: Retrofit,
    private val db: Database,
) {
    private val api = retrofit.create(Api::class.java)

    suspend operator fun invoke(): ProcessState =
        withContext(Dispatchers.IO) {
            try {
                prefetchHeroes()
                ProcessState.Success
            } catch (e: Exception) {
                ProcessState.Error(e)
            }
        }

    private suspend fun prefetchHeroes() {
        api.fetchHeroes()
            .values.map { dto ->
                HeroEntity(
                    id = dto.id,
                    name = dto.keyName,
                    localName = dto.name,
                    primaryAttr = dto.primaryAttr,
                    attackType = dto.attackType,
                    roles = dto.roles.joinToString(),
                    imgUrl = buildImageResUrl(dto.imageSuffix),
                    iconUrl = buildImageResUrl(dto.iconSuffix),
                    baseHealth = dto.baseHealth,
                    baseHealthRegen = dto.baseHealthRegen,
                    baseMana = dto.baseMana,
                    baseManaRegen = dto.baseManaRegen,
                    baseArmor = dto.baseArmor,
                    baseMr = dto.baseMr,
                    baseAttackMin = dto.baseAttackMin,
                    baseAttackMax = dto.baseAttackMax,
                    baseStr = dto.baseStr,
                    baseAgi = dto.baseAgi,
                    baseInt = dto.baseInt,
                    strGain = dto.strGain,
                    agiGain = dto.agiGain,
                    intGain = dto.intGain,
                    attackRange = dto.attackRange,
                    projectileSpeed = dto.projectileSpeed,
                    attackRate = dto.attackRate,
                    baseAttackTime = dto.baseAttackTime,
                    attackPoint = dto.attackPoint,
                    moveSpeed = dto.moveSpeed,
                    legs = dto.legs,
                    dayVision = dto.dayVision,
                    nightVision = dto.nightVision,
                )
            }
            .also { heroes ->
                db.heroDao.insertAll(heroes)
            }
    }

    private fun buildImageResUrl(suffix: String) = "$RES_HOST${suffix}"

    private interface Api {
        @GET("/constants/heroes")
        suspend fun fetchHeroes(): Map<Int, HeroInfoDto>
    }
}