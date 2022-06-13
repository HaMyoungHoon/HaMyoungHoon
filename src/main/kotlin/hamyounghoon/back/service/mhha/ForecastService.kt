package hamyounghoon.back.service.mhha

import hamyounghoon.back.mapper.mhha.LogMapper
import hamyounghoon.back.mapper.mhha.forecast.ForecastMapper
import hamyounghoon.back.mapper.mhha.forecast.FormatTableMapper
import hamyounghoon.back.mapper.mhha.forecast.ProjectNamesMapper
import hamyounghoon.back.mapper.mhha.forecast.ScheduleMapper
import hamyounghoon.back.model.mhha.forecast.*
import org.apache.ibatis.session.RowBounds
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ForecastService {
    @Autowired lateinit var forecastMapper: ForecastMapper
    @Autowired lateinit var formatTableMapper: FormatTableMapper
    @Autowired lateinit var projectNamesMapper: ProjectNamesMapper
    @Autowired lateinit var scheduleMapper: ScheduleMapper
    @Autowired lateinit var rolesService: RolesService
    @Autowired lateinit var logMapper: LogMapper

    fun getForecastYear(token: String, index: Int?, size: Int?): List<String> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastYear(RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun getForecast(token: String, seq: Int): ForecastModel {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastBySeq(seq)).orElseThrow()
    }
    fun getForecastList(token: String, start_date: String, end_date: String, index: Int?, size: Int?): List<ForecastModel> {
        rolesService.isValid(token)

        var endDateFormat = end_date
        if (!endDateFormat.contains(":")) {
            endDateFormat += " 23:59:59"
        }

        return Optional.ofNullable(forecastMapper.findForecastByDate(start_date, endDateFormat, RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun getForecastList(token: String, index: Int?, size: Int?): List<ForecastModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastList(RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun getForecastJoinList(token: String, index: Int?, size: Int?): List<ForecastCompModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastJoinList(RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun getForecastJoinList(token: String, year: Int, index: Int?, size: Int?): List<ForecastCompModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastYearJoinList(year, RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun addForecastList(token: String, project_names_seq: Int, principals_seq: Int, model: String, application_seq: Int,
                        price: Long?, price_etc: String?, possibility: Int, quote_date: String, contract_month: String,
                        situation: String, state_seq: Int, person_seq: Int, support_seq: Int?) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(forecastMapper.addForecast(project_names_seq, principals_seq, model, application_seq, price,
            price_etc, possibility, quote_date, contract_month, situation,
            state_seq, person_seq, support_seq)).orElseThrow()
        addLog(user.seq, "[INSERT]\tforecast 추가\tproject_names_seq:$project_names_seq,principals_seq:$principals_seq,model:$model,application_seq:$application_seq,price:$price,price_etc:$price_etc,possibility:$possibility,situation:$situation,state_seq:$state_seq,person_seq:$person_seq,support_seq:$support_seq")
    }
    fun updateForecastExceptContractMonth(token: String, seq: Int, project_names_seq: Int, principals_seq: Int, model: String , application_seq: Int,
                                          price: Long?, price_etc: String?, possibility: Int, situation: String, state_seq: Int,
                                          person_seq: Int, support_seq: Int?) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(forecastMapper.setForecastBySeqExceptContractMonth(seq, project_names_seq, principals_seq, model, application_seq, price, price_etc, possibility, situation, state_seq, person_seq, support_seq)).orElseThrow()
        addLog(user.seq, "[UPDATE]\tforecast 상태변경\tseq:$seq,project_names_seq:$project_names_seq,principals_seq:$principals_seq,model:$model,application_seq:$application_seq,price:$price,price_etc:$price_etc,possibility:$possibility,situation:$situation,state_seq:$state_seq,person_seq:$person_seq,support_seq:$support_seq")
    }
    fun updateForecastContractMonth(token: String, seq: Int, contract_month: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(forecastMapper.setForecastBySeqSetContractMonth(seq, contract_month)).orElseThrow()
        addLog(user.seq, "[UPDATE]\tforecast 상태변경\tseq:$seq,contract_month:$contract_month")
    }
    fun updateForecastSituation(token: String, seq: Int, situation: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(forecastMapper.setForecastBySeqSetSituation(seq, situation)).orElseThrow()
        addLog(user.seq, "[UPDATE]\tforecast 상태변경\tseq:$seq,situation:$situation")
    }
    fun updateForecastState(token: String, seq: Int, state_seq: Int) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(forecastMapper.setForecastBySeqSetState(seq, state_seq)).orElseThrow()
        addLog(user.seq, "[UPDATE]\tforecast 상태변경\tseq:$seq,state_seq:$state_seq")
    }

    fun getContractDate(token: String, seq: Int): ContractDateModel {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findContractDateBySeq(seq)).orElseThrow()
    }
    fun getContractDateList(token: String, forecastSeq: Int): List<ContractDateModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findContractDateByForecastSeq(forecastSeq)).orElseThrow()
    }
    fun getContractDateList(token: String, start_date: String, end_date: String, index: Int?, size: Int?): List<ContractDateModel> {
        rolesService.isValid(token)

        var endDateFormat = end_date
        if (!endDateFormat.contains(":")) {
            endDateFormat += " 23:59:59"
        }

        return Optional.ofNullable(forecastMapper.findContractDateByDate(start_date, endDateFormat, RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }

    fun getForecastComp(token: String, seq: Int): ForecastCompModel {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastCompBySeq(seq)).orElseThrow()
    }
    fun getForecastCompList(token: String, start_date: String, end_date: String, index: Int?, size: Int?): List<ForecastCompModel> {
        rolesService.isValid(token)
        var endDateFormat = end_date
        if (!endDateFormat.contains(":")) {
            endDateFormat += " 23:59:59"
        }

        return Optional.ofNullable(forecastMapper.findForecastCompByDate(start_date, endDateFormat, RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun getForecastCompList(token: String, index: Int?, size: Int?): List<ForecastCompModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastCompList(RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }

    fun getForecastForStatePieChart(token: String, year: Int): List<StatePieChartModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastForStatePieChart(year)).orElseThrow()
    }
    fun getForecastForStateBarChartByQuote(token: String, year: Int): List<StateBarChartSubModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastForStateBarChartByQuote(year)).orElseThrow()
    }
    fun getForecastForStateBarChartByContract(token: String, year: Int): List<StateBarChartSubModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(forecastMapper.findForecastForStateBarChartByContract(year)).orElseThrow()
    }

    fun getApplicationList(token: String): List<ApplicationModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(formatTableMapper.findApplication()).orElseThrow()
    }
    fun getApplication(token: String, seq: Int): ApplicationModel {
        rolesService.isValid(token)
        return Optional.ofNullable(formatTableMapper.findApplicationBySeq(seq)).orElseThrow()
    }
    fun addApplication(token: String, name: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(formatTableMapper.addApplication(name)).orElseThrow()
        addLog(user.seq, "[INSERT]\t애플리케이션 추가\tname:$name")
    }
    fun updateApplication(token: String, seq: Int, name: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(formatTableMapper.setApplicationBySeqSetName(seq, name)).orElseThrow()
        addLog(user.seq, "[UPDATE]\t애플리케이션 변경\tseq:$seq,name:$name")
    }

    fun getPrincipalsList(token: String): List<PrincipalsModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(formatTableMapper.findPrincipals()).orElseThrow()
    }
    fun getPrincipals(token: String, seq: Int): PrincipalsModel {
        rolesService.isValid(token)
        return Optional.ofNullable(formatTableMapper.findPrincipalsBySeq(seq)).orElseThrow()
    }
    fun addPrincipals(token: String, name: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(formatTableMapper.addPrincipals(name)).orElseThrow()
        addLog(user.seq, "[INSERT]\tprincipals 추가\tname:$name")
    }
    fun updatePrincipals(token: String, seq: Int, name: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(formatTableMapper.setPrincipalsBySeqSetName(seq, name)).orElseThrow()
        addLog(user.seq, "[UPDATE]\tprincipals 변경\tseq:$seq,name:$name")
    }

    fun getStateList(token: String): List<StateModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(formatTableMapper.findState()).orElseThrow()
    }
    fun getState(token: String, seq: Int): StateModel {
        rolesService.isValid(token)
        return Optional.ofNullable(formatTableMapper.findStateBySeq(seq)).orElseThrow()
    }
    fun addState(token: String, name: String, back_color: String, text_color: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(formatTableMapper.addState(name, back_color, text_color)).orElseThrow()
        addLog(user.seq, "[INSERT]\tstate 추가\tname:$name,back_color:$back_color,text_color:$text_color")
    }
    fun updateState(token: String, seq: Int, name: String, back_color: String, text_color: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(formatTableMapper.setStateBySeqSetName(seq, name, back_color, text_color)).orElseThrow()
        addLog(user.seq, "[UPDATE]\tstate 변경\tseq:$seq,name:$name,back_color:$back_color,text_color:$text_color")
    }

    fun findProjectNamesBySeq(token: String, seq: Int): ProjectNamesModel {
        rolesService.isValid(token)
        return projectNamesMapper.findProjectNamesBySeq(seq)
    }
    fun getProjectNames(token: String): List<ProjectNamesModel> {
        rolesService.isValid(token)
        return projectNamesMapper.getProjectNamesList()
    }
    fun addProjectNames(token: String, name: String, customer: String, marketField: String) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(projectNamesMapper.addProjectNames(name, customer, marketField)).orElseThrow()
        addLog(user.seq, "[INSERT]\t프로젝트 이름\tname:$name,customer:$customer,marketField:$marketField")
    }

    fun getScheduleByYear(token: String, year: String, index: Int?, size: Int?): List<ScheduleModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(scheduleMapper.findScheduleByYear(year, RowBounds(index ?: 0, size ?: 0))).orElseThrow()
    }
    fun getScheduleByMonth(token: String, month: String): List<ScheduleModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(scheduleMapper.findScheduleByMonth(month)).orElseThrow()
    }
    fun getScheduleByPerson(token: String, person_seq: Int): List<ScheduleModel> {
        rolesService.isValid(token)
        return Optional.ofNullable(scheduleMapper.findScheduleByPerson(person_seq)).orElseThrow()
    }
    fun setScheduleBySeq(token: String, seq: Int, title: String, start_date: String, end_date: String, person_seq: Int, is_del: Int) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(scheduleMapper.modifyScheduleBySeq(seq, title, start_date, end_date, person_seq, is_del)).orElseThrow()
        addLog(user.seq, "[UPDATE]\t스케줄 변경\tseq:$seq,title:$title,start_date:$start_date,end_date:$end_date,person_seq:$person_seq,is_del:$is_del")
    }
    fun addSchedule(token: String, title: String, start_date: String, end_date: String, person_seq: Int) {
        rolesService.isValid(token)
        val user = rolesService.getPerson(token)

        Optional.ofNullable(scheduleMapper.addSchedule(title, start_date, end_date, person_seq)).orElseThrow()
        addLog(user.seq, "[INSERT]\t스케줄 등록\ttitle:$title,start_date:$start_date,end_date:$end_date,person_seq:$person_seq")
    }

    private fun addLog(userid: Int, logFormat: String) {
        logMapper.addLog(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), userid, logFormat)
    }
}
