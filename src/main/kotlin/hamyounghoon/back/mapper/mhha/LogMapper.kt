package hamyounghoon.back.mapper.mhha

import hamyounghoon.back.model.mhha.LogCompModel
import hamyounghoon.back.model.mhha.LogModel
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.session.RowBounds
import org.springframework.stereotype.Repository

/**
 * 사용자가 뭐 했나 보려고 쓰는 거
 * @constructor Create empty Log mapper
 */
@Repository
@Mapper
interface LogMapper {
    /**
     * all log data count
     * @return Int
     */
    fun countOfLog(): Int

    /**
     * all log data
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List LogModel
     */
    fun findLog(rowBounds: RowBounds): List<LogModel>

    /**
     * person_seq에 따른 log data count
     * @param person_seq t_mhha_person 순번
     * @return Int
     */
    fun countOfLogByPersonSeq(@Param("person_seq") person_seq: Int): Int

    /**
     * person_seq에 따른 log data
     * @param person_seq t_mhha_person 순번
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List LogModel
     */
    fun findLogByPersonSeq(@Param("person_seq") person_seq: Int, rowBounds: RowBounds): List<LogModel>

    /**
     * date에 따른 log data count
     * @param start_date 시작일
     * @param end_date 끝일
     * @return Int
     */
    fun countOfLogByDate(@Param("start_date") start_date: String, @Param("end_date") end_date: String): Int

    /**
     * date 에 따른 log data
     * @param start_date 시작일
     * @param end_date 끝일
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List LogModel
     */
    fun findLogByDate(@Param("start_date") start_date: String, @Param("end_date") end_date: String, rowBounds: RowBounds): List<LogModel>

    /**
     * all log Complete data count
     * @return Int
     */
    fun countOfLogComp(): Int

    /**
     * all log Complete data
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List LogModel
     */
    fun findLogComp(rowBounds: RowBounds): List<LogCompModel>

    /**
     * person_seq에 따른 log Complete data count
     * @param person_seq t_mhha_person 순번
     * @return Int
     */
    fun countOfLogCompByPersonSeq(@Param("person_seq") person_seq: Int): Int

    /**
     * person_seq에 따른 log Complete data
     * @param person_seq t_mhha_person 순번
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List LogModel
     */
    fun findLogCompByPersonSeq(@Param("person_seq") person_seq: Int, rowBounds: RowBounds): List<LogCompModel>

    /**
     * date에 따른 log Complete data count
     * @param start_date 시작일
     * @param end_date 끝일
     * @return Int
     */
    fun countOfLogCompByDate(@Param("start_date") start_date: String, @Param("end_date") end_date: String): Int

    /**
     * date 에 따른 log Complete data
     * @param start_date 시작일
     * @param end_date 끝일
     * @param rowBounds 콜할 개수 index ?: 0, size ?: 0
     * @return List LogModel
     */
    fun findLogCompByDate(@Param("start_date") start_date: String, @Param("end_date") end_date: String, rowBounds: RowBounds): List<LogCompModel>

    /**
     * insert log Complete data
     * @param logDate 날짜
     * @param person_seq t_mhha_person 순번
     * @param query 내용 [insert or update or delete]\t내용\t키 : 값
     */
    fun addLog(@Param("log_date") logDate: String, @Param("person_seq") person_seq: Int, @Param("query") query: String)
}
