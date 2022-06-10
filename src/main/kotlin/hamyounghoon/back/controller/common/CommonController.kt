package hamyounghoon.back.controller.common

import hamyounghoon.back.HaMyoungHoonBackApplication
import hamyounghoon.back.advice.exception.NotFoundLanguageException
import hamyounghoon.back.advice.exception.NotOwnerException
import hamyounghoon.back.config.security.JwtTokenProvider
import hamyounghoon.back.mapper.mhha.LogMapper
import hamyounghoon.back.mapper.mhha.person.PersonMapper
import hamyounghoon.back.model.common.*
import hamyounghoon.back.model.mhha.LogCompModel
import hamyounghoon.back.model.mhha.LogModel
import hamyounghoon.back.model.mhha.person.PersonModel
import hamyounghoon.back.service.common.ResponseService
import hamyounghoon.back.service.mhha.RolesService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.ibatis.session.RowBounds
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "CommonController")
@RestController
@RequestMapping(value = ["/common"])
class CommonController {
    @Autowired
    lateinit var responseService: ResponseService
    @Autowired
    lateinit var logMapper: LogMapper
    @Autowired
    lateinit var personMapper: PersonMapper
    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired
    lateinit var rolesService: RolesService

    /**
     * Set language
     * exception 등 backend 언어를 바꾼다.
     * @param lang 한글, 영어만 가능.
     * @return CommonResult 성공, 실패
     */
    @Operation(summary = "language set", description = "")
    @PostMapping(value = ["/lang"])
    fun setLanguage(@Parameter(name = "lang") @RequestParam lang: String): CommonResult {
        when (lang) {
            "ko" -> LocaleContextHolder.setDefaultLocale(Locale.KOREA)
            "en" -> LocaleContextHolder.setDefaultLocale(Locale.ENGLISH)
            else -> throw NotFoundLanguageException()
        }

        return responseService.getSuccessResult()
    }

    @PostMapping(value  = ["/shutdown"])
    fun shutdown(@Parameter(name = "key") @RequestParam key: String): CommonResult {
        if (key != "mhha") {
            throw NotOwnerException()
        }

        HaMyoungHoonBackApplication.ctx?.close()

        return responseService.getSuccessResult()
    }

    @PostMapping(value = ["/post/list/person"])
    fun getPersonList(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                      @Parameter(name = "index", required = false) @RequestParam index: Int? = null,
                      @Parameter(name = "size", required = false) @RequestParam size: Int? = null): ListResult<PersonModel> {
        rolesService.isAdmin(token)
        return responseService.getListResult(personMapper.findAllPerson(RowBounds(index ?: 0, size ?: 0)))
    }

    @PostMapping(value = ["/post/log/count/person"])
    fun getCountOfLogByPerson(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                              @Parameter(name = "person_seq") @RequestParam person_seq: Int): SingleResult<Int> {
        rolesService.isAdmin(token)
        return responseService.getSingleResult(logMapper.countOfLogByPersonSeq((person_seq)))
    }
    @PostMapping(value = ["/post/log/person"])
    fun getLogByPerson(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                       @Parameter(name = "person_seq") @RequestParam person_seq: Int,
                       @Parameter(name = "index", required = false) @RequestParam index: Int? = null,
                       @Parameter(name = "size", required = false) @RequestParam size: Int? = null): ListResult<LogModel> {
        rolesService.isAdmin(token)
        return responseService.getListResult(logMapper.findLogByPersonSeq(person_seq, RowBounds(index ?: 0, size ?: 0)))
    }
    @PostMapping(value = ["/post/log/count/date"])
    fun getCountOfLogByDate(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                            @Parameter(name = "start_date") @RequestParam start_date: String,
                            @Parameter(name = "end_date") @RequestParam end_date: String): SingleResult<Int> {
        rolesService.isAdmin(token)
        return responseService.getSingleResult(logMapper.countOfLogByDate(start_date, end_date))
    }
    @PostMapping(value = ["/post/log/date"])
    fun getLogByDate(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                     @Parameter(name = "start_date") @RequestParam start_date: String,
                     @Parameter(name = "end_date") @RequestParam end_date: String,
                     @Parameter(name = "index", required = false) @RequestParam index: Int? = null,
                     @Parameter(name = "size", required = false) @RequestParam size: Int? = null): ListResult<LogModel> {
        rolesService.isAdmin(token)
        return responseService.getListResult(logMapper.findLogByDate(start_date, end_date, RowBounds(index ?: 0, size ?: 0)))
    }
    @PostMapping(value = ["/post/log_comp/count/person"])
    fun getCountOfLogCompByPerson(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                                  @Parameter(name = "person_seq") @RequestParam person_seq: Int): SingleResult<Int> {
        rolesService.isAdmin(token)
        return responseService.getSingleResult(logMapper.countOfLogCompByPersonSeq((person_seq)))
    }
    @PostMapping(value = ["/post/log_comp/person"])
    fun getLogCompByPerson(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                           @Parameter(name = "person_seq") @RequestParam person_seq: Int,
                           @Parameter(name = "index", required = false) @RequestParam index: Int? = null,
                           @Parameter(name = "size", required = false) @RequestParam size: Int? = null): ListResult<LogCompModel> {
        rolesService.isAdmin(token)
        return responseService.getListResult(logMapper.findLogCompByPersonSeq(person_seq, RowBounds(index ?: 0, size ?: 0)))
    }
    @PostMapping(value = ["/post/log_comp/count/date"])
    fun getCountOfLogCompByDate(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                                @Parameter(name = "start_date") @RequestParam start_date: String,
                                @Parameter(name = "end_date") @RequestParam end_date: String): SingleResult<Int> {
        rolesService.isAdmin(token)
        return responseService.getSingleResult(logMapper.countOfLogCompByDate(start_date, end_date))
    }
    @PostMapping(value = ["/post/log_comp/date"])
    fun getLogCompByDate(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                         @Parameter(name = "start_date") @RequestParam start_date: String,
                         @Parameter(name = "end_date") @RequestParam end_date: String,
                         @Parameter(name = "index", required = false) @RequestParam index: Int? = null,
                         @Parameter(name = "size", required = false) @RequestParam size: Int? = null): ListResult<LogCompModel> {
        rolesService.isAdmin(token)
        return responseService.getListResult(logMapper.findLogCompByDate(start_date, end_date, RowBounds(index ?: 0, size ?: 0)))
    }

    @GetMapping(value = ["/exception/entryPoint"])
    fun entrypointException(): CommonResult {
        throw NotOwnerException()
    }
    @GetMapping(value = ["/exception/accessDenied"])
    fun accessDeniedException(): CommonResult {
        throw AccessDeniedException("")
    }
}
