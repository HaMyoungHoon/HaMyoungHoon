package hamyounghoon.back.service.mhha

import hamyounghoon.back.advice.exception.NotFoundUserException
import hamyounghoon.back.advice.exception.SignInFailedException
import hamyounghoon.back.advice.exception.SignUpFailedException
import hamyounghoon.back.config.security.JwtTokenProvider
import hamyounghoon.back.mapper.mhha.LogMapper
import hamyounghoon.back.mapper.mhha.person.PersonMapper
import hamyounghoon.back.model.mhha.person.PersonModel
import hamyounghoon.back.model.mhha.roles.SystemDeptListModel
import hamyounghoon.back.model.mhha.roles.SystemRolesListModel
import org.apache.ibatis.session.RowBounds
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class SignService {
    @Autowired lateinit var personMapper: PersonMapper
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired lateinit var logMapper: LogMapper
    @Autowired lateinit var rolesService: RolesService
    @Autowired lateinit var passwordEncoder: PasswordEncoder

    /**
     * 로그인
     * @param id t_mhha_person id
     * @param pw t_mhha_person pw
     * @return
     */
    fun signIn(id: String, pw: String): String {
        val user = Optional.ofNullable(personMapper.findPersonByID(id)).orElseThrow { NotFoundUserException() }
        if (!passwordEncoder.matches(pw, user.pw)) {
            throw SignInFailedException()
        }

        return jwtTokenProvider.createToken(user)
    }
    /**
     * 가입
     * @param token 로그인 토큰 ADMIN 체크
     * @param id t_mhha_person id
     * @param pw t_mhha_person pw
     * @param name t_mhha_person name
     * @param entry_date 가입일
     */
    fun signUp(token: String, id: String, pw: String, name: String, entry_date: String) {
        rolesService.isAdmin(token)

        if (personMapper.findPersonByID(id) != null) {
            throw SignUpFailedException()
        }

        var user = rolesService.getPerson(token)
        val encodePW = passwordEncoder.encode(pw)
        personMapper.addPerson(id, encodePW, name, entry_date)
        addLog(user.seq, "[INSERT]\t유저 추가\tid:$id,pw:$encodePW,name:$name,entry_date:$entry_date")
        user = personMapper.findPersonByID(id)!!
        if (id == "mhha") {
            val tokenBuff = jwtTokenProvider.createToken(user)
            rolesService.addPersonRoles(tokenBuff, user.seq, 2, SystemRolesListModel.SystemRolesListEnum.PORTAL_ADMIN.toString(), true)
            rolesService.addPersonDept(tokenBuff, user.seq, 1, SystemDeptListModel.SystemDeptListEnum.NONE.toString(), true)
        } else {
            rolesService.addPersonRoles(token, user.seq, 1, SystemRolesListModel.SystemRolesListEnum.NONE.toString())
            rolesService.addPersonDept(token, user.seq, 1, SystemDeptListModel.SystemDeptListEnum.NONE.toString())
        }
    }
    /**
     * 비번 변경
     * @param token 로그인 토큰 ADMIN 체크
     * @param id t_mhha_person id
     * @param pw 기존 비번
     * @param new_pw 바꿀 비번
     */
    fun pwChange(token: String, id: String, pw: String, new_pw: String) {
        rolesService.isAdmin(token)
        val user = rolesService.getPerson(token)
        if (!passwordEncoder.matches(pw, user.pw)) {
            throw SignInFailedException()
        }
    }
    /**
     * 탈퇴
     * @param token 로그인 토큰 ADMIN 체크
     * @param id t_mhha_person id
     * @param resign_date 탈퇴일
     */
    fun resign(token: String, id: String, resign_date: String) {
        rolesService.isAdmin(token)
        val user = rolesService.getPerson(token)
        personMapper.updatePersonByIDSetResignDate(id, resign_date)
        addLog(user.seq, "[UPDATE]\t유저 정보 변경\tid:$id,resign_date:$resign_date")
        personMapper.updatePersonByIDSetStatus(id, "DELETE")
        addLog(user.seq, "[UPDATE]\t유저 정보 변경\tid:$id,status:DELETE")
    }
    /**
     * 유저 정보 개수
     * @param token 로그인 토큰 ADMIN 체크
     * @return Int
     */
    fun getUserCount(token: String): Int {
        return Optional.ofNullable(personMapper.countOfAllPerson()).orElseThrow()
    }
    /**
     * 모든 유저 정보를 가져온다
     * @param token 로그인 토큰 ADMIN 체크
     * @return 유저 정보 PersonModel
     */
    fun getAllUser(token: String, index: Int?, size: Int?): List<PersonModel> {
        val rowBounds = RowBounds(index ?: 0, size ?: 0)
        return if (rolesService.isAdmin(token, false)) {
            Optional.ofNullable(personMapper.findAllPerson(rowBounds)).orElseThrow()
        } else {
            Optional.ofNullable(personMapper.findAllPersonNotAdmin(rowBounds)).orElseThrow()
        }
    }
    /**
     * 유저 정보를 가져온다
     * @param token 로그인 토큰 ADMIN 체크
     * @param id t_mhha_person id
     * @return 유저 정보 PersonModel
     */
    fun getUserID(token: String, id: String): PersonModel {
        rolesService.isAdmin(token)
        return Optional.ofNullable(personMapper.findPersonByID(id)).orElseThrow()
    }
    /**
     * 유저 정보를 가져온다
     * @param token 로그인 토큰 ADMIN 체크
     * @param seq t_mhha_person seq
     * @return 유저 정보 PersonModel
     */
    fun getUserSeq(token: String, seq: Int): PersonModel {
        rolesService.isAdmin(token)
        return Optional.ofNullable(personMapper.findPersonBySeq(seq)).orElseThrow()
    }

    private fun addLog(userid: Int, logFormat: String) {
        logMapper.addLog(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), userid, logFormat)
    }
}
