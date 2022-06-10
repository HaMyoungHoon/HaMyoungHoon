package hamyounghoon.back.controller.v1

import hamyounghoon.back.config.security.JwtTokenProvider
import hamyounghoon.back.model.common.*
import hamyounghoon.back.model.mhha.person.PersonModel
import hamyounghoon.back.model.mhha.roles.*
import hamyounghoon.back.service.common.ResponseService
import hamyounghoon.back.service.mhha.RolesService
import hamyounghoon.back.service.mhha.SignService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "SignController")
@RestController
@RequestMapping(value = ["/v1/sign"])
class SignController {
    @Autowired lateinit var signService: SignService
    @Autowired lateinit var rolesService: RolesService
    @Autowired lateinit var responseService: ResponseService

    @Operation(summary = "login", description = "")
    @PostMapping(value = ["/signIn"])
    fun signIn(@Parameter(name = "id", required = true) @RequestParam id : String,
               @Parameter(name = "pw", required = true) @RequestParam pw : String): SingleResult<String> {
        return responseService.getSingleResult(signService.signIn(id, pw))
    }
    @Operation(summary = "sign up", description = "")
    @PostMapping(value = ["/signUp"])
    fun signUp(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
               @Parameter(name = "id", required = true) @RequestParam id : String,
               @Parameter(name = "pw", required = true) @RequestParam pw : String,
               @Parameter(name = "name", required = true) @RequestParam name : String,
               @Parameter(name = "entry_date", required = true) @RequestParam entry_date : String): CommonResult {
        signService.signUp(token, id, pw, name, entry_date)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "password change")
    @PutMapping(value = ["/set/pw"])
    fun setPW(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
              @Parameter(name = "id", required = true) @RequestParam id: String,
              @Parameter(name = "pw") @RequestParam pw: String,
              @Parameter(name = "new_pw") @RequestParam new_pw: String) : CommonResult {
        signService.pwChange(token, id, pw, new_pw)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "resign")
    @PutMapping(value = ["/resign"])
    fun resign(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
               @Parameter(name = "id", required = true) @RequestParam id: String,
               @Parameter(name = "resign_date") @RequestParam resign_date: String) : CommonResult {
        signService.resign(token, id, resign_date)
        return responseService.getSuccessResult()
    }

    @Operation(summary = "get user data", description = "get this user data")
    @GetMapping(value = ["/get/user"])
    fun getUser(@RequestHeader(value = JwtTokenProvider.authToken) token: String): SingleResult<PersonModel> {
        return responseService.getSingleResult(rolesService.getPerson(token))
    }

    @Operation(summary = "account info", description = "")
    @GetMapping(value = ["/get/user/one/id/{login_id}"])
    fun getUserID(@RequestHeader(value = JwtTokenProvider.authToken) token : String,
                  @PathVariable login_id: String): SingleResult<PersonModel> {
        return responseService.getSingleResult(signService.getUserID(token, login_id))
    }
    @Operation(summary = "account info", description = "")
    @GetMapping(value = ["/get/user/one/seq/{seq}"])
    fun getUserSeq(@RequestHeader(value = JwtTokenProvider.authToken) token : String,
                   @PathVariable seq: Int): SingleResult<PersonModel> {
        return responseService.getSingleResult(signService.getUserSeq(token, seq))
    }
    @Operation(summary = "post user data", description = "get all user data")
    @PostMapping(value = ["/post/user/all"])
    fun getAllUser(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                   @Parameter(name = "index") @RequestParam index: Int?,
                   @Parameter(name = "size") @RequestParam size: Int?): ListResult<PersonModel> {
        return responseService.getListResult(signService.getAllUser(token, index, size))
    }

    @Operation(summary = "get dept list", description = "get all dept list")
    @GetMapping(value = ["/get/dept_list"])
    fun getDeptList(@RequestHeader(value = JwtTokenProvider.authToken) token: String): ListResult<SystemDeptListModel> {
        return responseService.getListResult(rolesService.getAllDeptList(token))
    }
    @Operation(summary = "get roles list", description = "get all roles list")
    @GetMapping(value = ["/get/roles_list"])
    fun getRolesList(@RequestHeader(value = JwtTokenProvider.authToken) token: String): ListResult<SystemRolesListModel> {
        return responseService.getListResult(rolesService.getAllRolesList(token))
    }
    @Operation(summary = "add dept", description = "")
    @PostMapping(value = ["/add/dept_seq"])
    fun addDeptSeq(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                   @Parameter(name = "person_seq", required = true) @RequestParam person_seq: Int,
                   @Parameter(name = "system_seq", required = true) @RequestParam system_seq: Int,
                   @Parameter(name = "dept_seq", required = true) @RequestParam dept_seq: Int): CommonResult {

        rolesService.addPersonDeptSeq(token, person_seq, system_seq, dept_seq)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "add dept", description = "")
    @PostMapping(value = ["/add/dept"])
    fun addDept(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                @Parameter(name = "person_seq", required = true) @RequestParam person_seq: Int,
                @Parameter(name = "system_seq", required = true) @RequestParam system_seq: Int,
                @Parameter(name = "dept", required = true) @RequestParam dept: String): CommonResult {

        rolesService.addPersonDept(token, person_seq, system_seq, dept)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "add role", description = "")
    @PostMapping(value = ["/add/roles_seq"])
    fun addRolesSeq(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                    @Parameter(name = "person_seq", required = true) @RequestParam person_seq: Int,
                    @Parameter(name = "system_seq", required = true) @RequestParam system_seq: Int,
                    @Parameter(name = "roles_seq", required = true) @RequestParam roles_seq: Int): CommonResult {

        rolesService.addPersonRolesSeq(token, person_seq, system_seq, roles_seq)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "add role", description = "")
    @PostMapping(value = ["/add/roles"])
    fun addRoles(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                 @Parameter(name = "person_seq", required = true) @RequestParam person_seq: Int,
                 @Parameter(name = "system_seq", required = true) @RequestParam system_seq: Int,
                 @Parameter(name = "roles", required = true) @RequestParam roles: String): CommonResult {

        rolesService.addPersonRoles(token, person_seq, system_seq, roles)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "remove dept", description = "")
    @DeleteMapping(value = ["/del/dept_seq/one/{person_seq}/{system_seq}/{dept_seq}"])
    fun delDeptSeq(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                   @PathVariable person_seq: Int,
                   @PathVariable system_seq: Int,
                   @PathVariable dept_seq: Int): CommonResult {
        rolesService.delPersonDeptSeq(token, person_seq, system_seq, dept_seq, false)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "remove dept", description = "")
    @DeleteMapping(value = ["/del/dept/one/{person_seq}/{system_seq}/{dept}"])
    fun delDept(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                @PathVariable person_seq: Int,
                @PathVariable system_seq: Int,
                @PathVariable dept: String): CommonResult {
        rolesService.delPersonDept(token, person_seq, system_seq, dept, false)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "remove role", description = "")
    @DeleteMapping(value = ["/del/roles_seq/one/{person_seq}/{system_seq}/{roles_seq}"])
    fun delRolesSeq(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                    @PathVariable person_seq: Int,
                    @PathVariable system_seq: Int,
                    @PathVariable roles_seq: Int): CommonResult {
        rolesService.delPersonRolesSeq(token, person_seq, system_seq, roles_seq, false)
        return responseService.getSuccessResult()
    }
    @Operation(summary = "remove role", description = "")
    @DeleteMapping(value = ["/del/roles/one/{person_seq}/{system_seq}/{roles}"])
    fun delRoles(@RequestHeader(value = JwtTokenProvider.authToken) token: String,
                 @PathVariable person_seq: Int,
                 @PathVariable system_seq: Int,
                 @PathVariable roles: String): CommonResult {
        rolesService.delPersonRoles(token, person_seq, system_seq, roles, false)
        return responseService.getSuccessResult()
    }
}
