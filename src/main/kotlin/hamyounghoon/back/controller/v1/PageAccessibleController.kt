package hamyounghoon.back.controller.v1

import hamyounghoon.back.advice.exception.NotOwnerException
import hamyounghoon.back.config.security.JwtTokenProvider
import hamyounghoon.back.model.common.CommonResult
import hamyounghoon.back.model.mhha.roles.SystemRolesListModel
import hamyounghoon.back.service.common.ResponseService
import hamyounghoon.back.service.mhha.RolesService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "PageAccessibleController")
@RestController
@RequestMapping(value = ["/v1/page-accessible"])
class PageAccessibleController {
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var rolesService: RolesService

    @Operation(summary = "HaMyoungHoon.github.io schedule 접근 가능한지 확인")
    @GetMapping(value = ["/get/hamyounghoon/schedule"])
    fun canAccessSchedule(@RequestHeader(value = JwtTokenProvider.authToken) token: String): CommonResult {
        val roles: MutableList<SystemRolesListModel.SystemRolesListEnum> = arrayListOf()
        roles.add(SystemRolesListModel.SystemRolesListEnum.PORTAL_ADMIN)
        roles.add(SystemRolesListModel.SystemRolesListEnum.HAMYOUNGHOON_SCHEDULE_READ)
        roles.add(SystemRolesListModel.SystemRolesListEnum.HAMYOUNGHOON_SCHEDULE_WRITE)
        isOwner(token, roles)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "HaMyoungHoon.github.io contract 접근 가능한지 확인")
    @GetMapping(value = ["/get/hamyounghoon/contract"])
    fun canAccessContract(@RequestHeader(value = JwtTokenProvider.authToken) token: String): CommonResult {
        val roles: MutableList<SystemRolesListModel.SystemRolesListEnum> = arrayListOf()
        roles.add(SystemRolesListModel.SystemRolesListEnum.PORTAL_ADMIN)
        roles.add(SystemRolesListModel.SystemRolesListEnum.HAMYOUNGHOON_CONTRACT_READ)
        roles.add(SystemRolesListModel.SystemRolesListEnum.HAMYOUNGHOON_CONTRACT_WRITE)
        isOwner(token, roles)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "HaMyoungHoon.github.io setting 접근 가능한지 확인")
    @GetMapping(value = ["/get/hamyounghoon/setting"])
    fun canAccessSetting(@RequestHeader(value = JwtTokenProvider.authToken) token: String): CommonResult {
        val roles: MutableList<SystemRolesListModel.SystemRolesListEnum> = arrayListOf()
        roles.add(SystemRolesListModel.SystemRolesListEnum.PORTAL_ADMIN)
        roles.add(SystemRolesListModel.SystemRolesListEnum.HAMYOUNGHOON_ADMIN)
        isOwner(token, roles)
        return responseService.getSuccessResult()
    }

    private fun isOwner(token: String, roles: List<SystemRolesListModel.SystemRolesListEnum>, failThrow: Boolean = true): Boolean {
        val user = rolesService.getPerson(token)
        return if (SystemRolesListModel.tryFindList(user.rolesList(), roles)) {
            true
        } else if (failThrow) {
            throw NotOwnerException()
        } else {
            false
        }
    }
}
