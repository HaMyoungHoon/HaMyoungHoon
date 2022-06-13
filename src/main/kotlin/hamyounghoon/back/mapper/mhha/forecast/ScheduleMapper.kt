package hamyounghoon.back.mapper.mhha.forecast

import hamyounghoon.back.model.mhha.forecast.ScheduleModel
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.session.RowBounds
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface ScheduleMapper {
    fun findScheduleByYear(@Param(value = "year") year: String, rowBounds: RowBounds): List<ScheduleModel>
    fun findScheduleByMonth(@Param(value = "month") month: String): List<ScheduleModel>
    fun findScheduleByPerson(@Param(value = "person_seq") person_seq: Int): List<ScheduleModel>
    fun modifyScheduleBySeq(@Param(value = "seq") id: Int, @Param(value = "title") title: String,
                           @Param(value = "start_date") start: String, @Param(value = "end_date") end: String,
                           @Param(value = "person_seq") person_seq: Int, @Param(value = "is_del") is_del: Int)
    fun addSchedule(@Param(value = "title") title: String, @Param(value = "start_date") start: String,
                    @Param(value = "end_date") end: String, @Param(value = "person_seq") person_seq: Int)
}
