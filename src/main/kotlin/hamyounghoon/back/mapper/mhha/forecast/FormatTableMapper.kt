package hamyounghoon.back.mapper.mhha.forecast

import hamyounghoon.back.model.mhha.forecast.ApplicationModel
import hamyounghoon.back.model.mhha.forecast.PrincipalsModel
import hamyounghoon.back.model.mhha.forecast.StateModel
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface FormatTableMapper {
    fun findApplication(): List<ApplicationModel>
    fun findApplicationBySeq(@Param("seq") seq: Int): ApplicationModel
    fun addApplication(@Param("name") name: String)
    fun setApplicationBySeqSetName(@Param("seq") seq: Int, @Param("name") name: String)

    fun findPrincipals(): List<PrincipalsModel>
    fun findPrincipalsBySeq(@Param("seq") seq: Int): PrincipalsModel
    fun addPrincipals(@Param("name") name: String)
    fun setPrincipalsBySeqSetName(@Param("seq") seq: Int, @Param("name") name: String)

    fun findState(): List<StateModel>
    fun findStateBySeq(@Param("seq") seq: Int): StateModel
    fun addState(@Param("name") name: String, @Param("back_color") back_color: String, @Param("text_color") text_color: String)
    fun setStateBySeqSetName(@Param("seq") seq: Int, @Param("name") name: String, @Param("back_color") back_color: String, @Param("text_color") text_color: String)
}
