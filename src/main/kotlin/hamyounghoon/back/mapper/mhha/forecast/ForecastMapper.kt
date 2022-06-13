package hamyounghoon.back.mapper.mhha.forecast

import hamyounghoon.back.model.mhha.forecast.*
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.session.RowBounds
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface ForecastMapper {
    fun findForecastYear(rowBounds: RowBounds): List<String>
    fun findForecastBySeq(@Param(value = "seq") seq: Int): ForecastModel
    fun findForecastByDate(@Param(value = "start_date") start_date: String, @Param(value = "end_date") end_date: String, rowBounds: RowBounds): List<ForecastModel>
    fun findForecastList(rowBounds: RowBounds): List<ForecastModel>
    fun findForecastJoinList(rowBounds: RowBounds): List<ForecastCompModel>
    fun findForecastYearJoinList(@Param(value = "year") year: Int, rowBounds: RowBounds): List<ForecastCompModel>
    fun addForecast(@Param(value ="project_names_seq") project_names_seq: Int, @Param(value ="principals_seq") principals_seq: Int,
                    @Param(value ="model") model: String, @Param(value ="application_seq") application_seq: Int,
                    @Param(value ="price") price: Long?, @Param(value ="price_etc") price_etc: String?,
                    @Param(value ="possibility") possibility: Int, @Param(value ="quote_date") quote_date: String,
                    @Param(value ="contract_month") contract_month: String, @Param(value ="situation") situation: String,
                    @Param(value ="state_seq") state_seq: Int, @Param(value ="person_seq") person_seq: Int,
                    @Param(value ="support_seq") support_seq: Int?)

    fun setForecastBySeqExceptContractMonth(@Param(value = "seq") seq: Int,
                                            @Param(value ="project_names_seq") project_names_seq: Int, @Param(value ="principals_seq") principals_seq: Int,
                                            @Param(value ="model") model: String, @Param(value ="application_seq") application_seq: Int,
                                            @Param(value ="price") price: Long?, @Param(value ="price_etc") price_etc: String?,
                                            @Param(value ="possibility") possibility: Int, @Param(value ="situation") situation: String,
                                            @Param(value ="state_seq") stateSeq: Int, @Param(value ="person_seq") person_seq: Int,
                                            @Param(value ="support_seq") support_seq: Int?)
    fun setForecastBySeqSetContractMonth(@Param(value = "seq") seq: Int, @Param("contract_month") contract_month: String)
    fun setForecastBySeqSetSituation(@Param(value = "seq") seq: Int, @Param("situation") situation: String)
    fun setForecastBySeqSetState(@Param(value = "seq") seq: Int, @Param("state_seq") state_seq: Int)

    fun findContractDateBySeq(@Param(value = "seq") seq: Int): ContractDateModel
    fun findContractDateByForecastSeq(@Param(value = "seq") seq: Int): List<ContractDateModel>
    fun findContractDateByDate(@Param(value = "start_date") start_date: String, @Param(value = "end_date") end_date: String, rowBounds: RowBounds): List<ContractDateModel>

    fun findForecastCompBySeq(@Param(value = "seq") seq: Int): ForecastCompModel
    fun findForecastCompByDate(@Param(value = "start_date") start_date: String, @Param(value = "end_date") end_date: String, rowBounds: RowBounds): List<ForecastCompModel>
    fun findForecastCompList(rowBounds: RowBounds): List<ForecastCompModel>

    fun findForecastForStatePieChart(@Param(value = "year") year: Int): List<StatePieChartModel>
    fun findForecastForStateBarChartByQuote(@Param(value = "year") year: Int): List<StateBarChartSubModel>
    fun findForecastForStateBarChartByContract(@Param(value = "year") year: Int): List<StateBarChartSubModel>
}
