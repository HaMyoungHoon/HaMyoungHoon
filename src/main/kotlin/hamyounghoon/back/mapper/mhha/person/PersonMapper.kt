package hamyounghoon.back.mapper.mhha.person

import hamyounghoon.back.model.mhha.person.*
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.session.RowBounds
import org.springframework.stereotype.Repository

/**
 * 유저 정보 보려고 하는 거
 * @constructor Create empty Person mapper
 */
@Repository
@Mapper
interface PersonMapper {
    /**
     * 유저 정보 개수
     * @return Int
     */
    fun countOfAllPerson(): Int
    /**
     * 유저 정보 리스트
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List PersonModel
     */
    fun findAllPerson(rowBounds: RowBounds): List<PersonModel>

    /**
     * 유저 정보 리스트 패스워드 제거
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List PersonModel
     */
    fun findAllPersonNotAdmin(rowBounds: RowBounds): List<PersonModel>

    /**
     * 유저 정보 by 아이디
     * @param id 아이디
     * @return PersonModel
     */
    fun findPersonByID(@Param(value = "id") id: String): PersonModel?

    /**
     * 유저 정보 by 유저 순번
     * @param seq 유저 순번
     * @return PersonModel
     */
    fun findPersonBySeq(@Param(value = "seq") seq: Int): PersonModel

    /**
     * 유저 정보 like 이름
     * @param name 이름
     * @return List PersonModel
     */
    fun findPersonLikeName(@Param(value = "name") name: String): List<PersonModel>

    /**
     * 유저 정보 추가
     * @param id 아이디
     * @param pw 비번 Crypto
     * @param name 이름
     * @param entry_date 생성일
     */
    fun addPerson(@Param(value = "id") id: String, @Param(value = "pw") pw: String, @Param(value = "name") name: String, @Param(value = "entry_date") entry_date: String)
    fun updatePersonByIDSetPW(@Param("id") id: String, @Param("pw") pw: String)
    fun updatePersonByIDSetResignDate(@Param("id") id: String, @Param("resign_date") resign_date: String)
    fun updatePersonByIDSetStatus(@Param("id") id: String, @Param("status") status: String)
}
