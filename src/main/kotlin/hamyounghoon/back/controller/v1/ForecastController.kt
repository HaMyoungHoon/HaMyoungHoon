package hamyounghoon.back.controller.v1

import hamyounghoon.back.config.security.JwtTokenProvider
import hamyounghoon.back.model.common.CommonResult
import hamyounghoon.back.model.common.ListResult
import hamyounghoon.back.model.common.SingleResult
import hamyounghoon.back.model.mhha.forecast.*
import hamyounghoon.back.service.common.ResponseService
import hamyounghoon.back.service.mhha.ForecastService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "ForecastController")
@RestController
@RequestMapping(value = ["/v1/forecast"])
class ForecastController {
    @Autowired lateinit var forecastService: ForecastService
    @Autowired lateinit var responseService: ResponseService

    @Operation(summary = "")
    @GetMapping(value =["/get/forecast/year/{index}/{size}"])
    fun getForecastYear(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<String> {
        return responseService.getListResult(forecastService.getForecastYear(token, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/forecast/one/{seq}"])
    fun getForecast(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable seq : Int) : SingleResult<ForecastModel> {
        return responseService.getSingleResult(forecastService.getForecast(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/forecast/date/{start_date}/{end_date}/{index}/{size}"])
    fun getForecast(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable start_date : String,
                    @PathVariable end_date : String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ForecastModel> {
        return responseService.getListResult(forecastService.getForecastList(token, start_date, end_date, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/forecast/all/{index}/{size}"])
    fun getForecast(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ForecastModel> {
        return responseService.getListResult(forecastService.getForecastList(token, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["get/forecast/joinAll/{index}/{size}"])
    fun getForecastJoin(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ForecastCompModel> {
        return responseService.getListResult(forecastService.getForecastJoinList(token, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["get/forecast/joinAll/{year}/{index}/{size}"])
    fun getForecastJoin(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable year: Int, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ForecastCompModel> {
        return responseService.getListResult(forecastService.getForecastJoinList(token, year, index, size))
    }
    @Operation(summary = "")
    @PostMapping(value = ["/add/forecast"])
    fun addForecast(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                    @Parameter(name ="project_names_seq") @RequestParam project_names_seq: Int, @Parameter(name ="principals_seq") @RequestParam principals_seq: Int,
                    @Parameter(name ="model") @RequestParam model: String, @Parameter(name ="application_seq") @RequestParam application_seq: Int,
                    @Parameter(name ="price") @RequestParam price: Long?, @Parameter(name ="price_etc") @RequestParam price_etc: String?,
                    @Parameter(name ="possibility") @RequestParam possibility: Int, @Parameter(name ="quote_date") @RequestParam quote_date: String,
                    @Parameter(name ="contract_month") @RequestParam contract_month: String, @Parameter(name ="situation") @RequestParam situation: String,
                    @Parameter(name ="state_seq") @RequestParam state_seq: Int, @Parameter(name ="person_seq") @RequestParam person_seq: Int,
                    @Parameter(name ="support_seq") @RequestParam support_seq: Int?) : CommonResult {
        forecastService.addForecastList(token,
            project_names_seq, principals_seq, model, application_seq, price,
            price_etc, possibility, quote_date, contract_month, situation,
            state_seq, person_seq, support_seq)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/forecast/exceptContractMonth"])
    fun setForecastExceptContractMonth(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                                       @Parameter(name = "seq") @RequestParam seq: Int, @Parameter(name = "project_names_seq") @RequestParam project_names_seq: Int,
                                       @Parameter(name = "principals_seq") @RequestParam principals_seq: Int, @Parameter(name = "model") @RequestParam model: String,
                                       @Parameter(name = "application_seq") @RequestParam application_seq: Int, @Parameter(name = "price") @RequestParam price: Long?,
                                       @Parameter(name = "price_etc") @RequestParam price_etc: String?, @Parameter(name = "possibility") @RequestParam possibility: Int,
                                       @Parameter(name = "situation") @RequestParam situation: String,@Parameter(name = "state_seq") @RequestParam  state_seq: Int,
                                       @Parameter(name = "person_seq") @RequestParam person_seq: Int, @Parameter(name = "support_seq") @RequestParam support_seq: Int?): CommonResult {
        forecastService.updateForecastExceptContractMonth(token, seq, project_names_seq, principals_seq, model, application_seq, price, price_etc, possibility, situation, state_seq, person_seq, support_seq)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/forecast/date"])
    fun setForecastContractMonth(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                                 @Parameter(name = "seq") @RequestParam seq: Int,
                                 @Parameter(name ="contract_month") @RequestParam contract_month: String): CommonResult {
        forecastService.updateForecastContractMonth(token, seq, contract_month)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/forecast/situation"])
    fun setForecastSituation(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                             @Parameter(name = "seq") @RequestParam seq: Int,
                             @Parameter(name ="situation") @RequestParam situation: String): CommonResult {
        forecastService.updateForecastSituation(token, seq, situation)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/forecast/state"])
    fun setForecastState(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                         @Parameter(name = "seq") @RequestParam seq: Int,
                         @Parameter(name ="state_seq") @RequestParam state_seq: Int): CommonResult {
        forecastService.updateForecastState(token, seq, state_seq)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/contractDate/one/{seq}"])
    fun getContractDate(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable seq : Int) : SingleResult<ContractDateModel> {
        return responseService.getSingleResult(forecastService.getContractDate(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/contractDate/forecast/{forecast_seq}"])
    fun getContractDateList(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable forecast_seq : Int) : ListResult<ContractDateModel> {
        return responseService.getListResult(forecastService.getContractDateList(token, forecast_seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/contractDate/date/{start_date}/{end_date}/{index}/{size}"])
    fun getContractDateList(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable start_date : String,
                            @PathVariable end_date : String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ContractDateModel> {
        return responseService.getListResult(forecastService.getContractDateList(token, start_date, end_date, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/forecastComp/one/{seq}"])
    fun getForecastComp(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable seq : Int) : SingleResult<ForecastCompModel> {
        return responseService.getSingleResult(forecastService.getForecastComp(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/forecastComp/date/{start_date}/{end_date}/{index}/{size}"])
    fun getForecastCompList(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable start_date : String,
                            @PathVariable end_date : String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ForecastCompModel> {
        return responseService.getListResult(forecastService.getForecastCompList(token, start_date, end_date, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/forecastComp/all/{index}/{size}"])
    fun getForecastCompList(@RequestHeader(value = JwtTokenProvider.authToken) token : String, @PathVariable index: Int?, @PathVariable size: Int?) : ListResult<ForecastCompModel> {
        return responseService.getListResult(forecastService.getForecastCompList(token, index, size))
    }

    @Operation(summary = "")
    @GetMapping(value = ["/get/application/one/{seq}"])
    fun getApplication(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable seq: Int): SingleResult<ApplicationModel> {
        return responseService.getSingleResult(forecastService.getApplication(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/application/all"])
    fun getApplicationList(@RequestHeader(value = JwtTokenProvider.authToken) token: String): ListResult<ApplicationModel> {
        return responseService.getListResult(forecastService.getApplicationList(token))
    }
    @Operation(summary = "")
    @PostMapping(value = ["/add/application"])
    fun addApplication(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                       @Parameter(name = "name") @RequestParam name: String) : CommonResult {
        forecastService.addApplication(token, name)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/application"])
    fun setApplication(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                       @Parameter(name = "seq") @RequestParam seq: Int,
                       @Parameter(name = "name") @RequestParam name: String): CommonResult {
        forecastService.updateApplication(token, seq, name)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/principals/one/{seq}"])
    fun getPrincipals(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable seq: Int): SingleResult<PrincipalsModel> {
        return responseService.getSingleResult(forecastService.getPrincipals(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/principals/all"])
    fun getPrincipalsList(@RequestHeader(value = JwtTokenProvider.authToken) token: String): ListResult<PrincipalsModel> {
        return responseService.getListResult(forecastService.getPrincipalsList(token))
    }
    @Operation(summary = "")
    @PostMapping(value = ["/add/principals"])
    fun addPrincipals(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                      @Parameter(name = "name") @RequestParam name: String) : CommonResult {
        forecastService.addPrincipals(token, name)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/principals"])
    fun setPrincipals(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                      @Parameter(name = "seq") @RequestParam seq: Int,
                      @Parameter(name = "name") @RequestParam name: String): CommonResult {
        forecastService.updatePrincipals(token, seq, name)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/state/one/{seq}"])
    fun getState(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable seq: Int): SingleResult<StateModel> {
        return responseService.getSingleResult(forecastService.getState(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/state/all"])
    fun getStateList(@RequestHeader(value = JwtTokenProvider.authToken) token: String): ListResult<StateModel> {
        return responseService.getListResult(forecastService.getStateList(token))
    }
    @Operation(summary = "")
    @PostMapping(value = ["/add/state"])
    fun addState(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                 @Parameter(name = "name") @RequestParam name: String,
                 @Parameter(name = "back_color") @RequestParam back_color: String,
                 @Parameter(name = "text_color") @RequestParam text_color: String) : CommonResult {
        forecastService.addState(token, name, back_color, text_color)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/state"])
    fun setState(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                 @Parameter(name = "seq") @RequestParam seq: Int,
                 @Parameter(name = "name") @RequestParam name: String,
                 @Parameter(name = "back_color") @RequestParam back_color: String,
                 @Parameter(name = "text_color") @RequestParam text_color: String): CommonResult {
        forecastService.updateState(token, seq, name, back_color, text_color)
        return responseService.getSuccessResult()
    }

    @Operation(summary = "")
    @GetMapping(value =["/get/chart/state/pie/{year}"])
    fun getForecastStatePieChart(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable year : Int) : ListResult<StatePieChartModel> {
        return responseService.getListResult(forecastService.getForecastForStatePieChart(token, year))
    }
    @Operation(summary = "")
    @GetMapping(value =["/get/chart/state/bar/quote/{year}"])
    fun getForecastStateBarChartByQuoteDate(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable year : Int) : ListResult<StateBarChartModel> {
        val sub = forecastService.getForecastForStateBarChartByQuote(token, year)
        val stateModel = forecastService.getStateList(token)
        return responseService.getListResult(setStateBarData(sub, stateModel))
    }
    @Operation(summary = "")
    @GetMapping(value =["/get/state/bar/contract/{year}"])
    fun getForecastStateBarChartByContractDate(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable year : Int) : ListResult<StateBarChartModel> {
        val sub = forecastService.getForecastForStateBarChartByContract(token, year)
        val stateModel = forecastService.getStateList(token)
        return responseService.getListResult(setStateBarData(sub, stateModel))
    }
    fun setStateBarData(sub: List<StateBarChartSubModel>, stateModel: List<StateModel>): List<StateBarChartModel> {
        var stateBarModel: MutableList<StateBarChartModel> = mutableListOf()
        sub.forEach {
            for (i in stateModel) {
                stateBarModel.add(StateBarChartModel(it.date, 0, i.seq, i.name, i.back_color))
            }
            stateBarModel = stateBarModel.distinct().toMutableList()
        }
        sub.forEach {
            for(i in stateBarModel) {
                if (it.date == i.date && it.state_seq == i.state_seq) {
                    stateBarModel[stateBarModel.indexOf(i)] = StateBarChartModel(it.date, it.price, it.state_seq, i.name, i.back_color)
                }
            }
        }

        return stateBarModel
    }

    @Operation(summary = "")
    @GetMapping(value = ["/get/projectNames/one/{seq}"])
    fun getProjectNames(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                        @PathVariable seq: Int): SingleResult<ProjectNamesModel> {
        return responseService.getSingleResult(forecastService.findProjectNamesBySeq(token, seq))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/projectNames/all"])
    fun getProjectNames(@RequestHeader(value = JwtTokenProvider.authToken) token: String): ListResult<ProjectNamesModel> {
        return responseService.getListResult(forecastService.getProjectNames(token))
    }
    @Operation(summary = "")
    @PostMapping(value = ["/add/projectNames"])
    fun addProjectNames(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                        @Parameter(name = "name") @RequestParam name: String,
                        @Parameter(name = "customer") @RequestParam customer: String,
                        @Parameter(name = "market_field") @RequestParam market_field: String): CommonResult {
        forecastService.addProjectNames(token, name, customer, market_field)
        return responseService.getSuccessResult()
    }

    @Operation(summary = "")
    @GetMapping(value = ["/get/schedule/year/{year}/{index}/{size}"])
    fun getScheduleByYear(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                          @PathVariable year: String, @PathVariable index: Int?, @PathVariable size: Int?): ListResult<ScheduleModel> {
        return responseService.getListResult(forecastService.getScheduleByYear(token, year, index, size))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/schedule/year/{month}"])
    fun getScheduleByMonth(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable month: String): ListResult<ScheduleModel> {
        return responseService.getListResult(forecastService.getScheduleByMonth(token, month))
    }
    @Operation(summary = "")
    @GetMapping(value = ["/get/schedule/year/{person_seq}"])
    fun getScheduleByPerson(@RequestHeader(value = JwtTokenProvider.authToken) token: String, @PathVariable person_seq: Int): ListResult<ScheduleModel> {
        return responseService.getListResult(forecastService.getScheduleByPerson(token, person_seq))
    }
    @Operation(summary = "")
    @PostMapping(value = ["/add/schedule"])
    fun addSchedule(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                    @Parameter(name = "title") @RequestParam title: String,
                    @Parameter(name = "start_date") @RequestParam start_date: String,
                    @Parameter(name = "end_date") @RequestParam end_date: String,
                    @Parameter(name = "person_seq") @RequestParam person_seq: Int): CommonResult {
        forecastService.addSchedule(token, title, start_date, end_date, person_seq)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "")
    @PutMapping(value = ["/set/schedule"])
    fun setScheduleBySeq(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                         @Parameter(name = "seq") @RequestParam seq: Int,
                         @Parameter(name = "title") @RequestParam title: String,
                         @Parameter(name = "start_date") @RequestParam start_date: String,
                         @Parameter(name = "end_date") @RequestParam end_date: String,
                         @Parameter(name = "person_seq") @RequestParam person_seq: Int,
                         @Parameter(name = "is_del") @RequestParam is_del: Int): CommonResult {
        forecastService.setScheduleBySeq(token, seq, title, start_date, end_date, person_seq, is_del)
        return responseService.getSuccessResult()
    }
}
