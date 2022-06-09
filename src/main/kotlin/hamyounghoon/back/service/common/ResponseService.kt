package hamyounghoon.back.service.common

import hamyounghoon.back.model.common.*
import org.springframework.stereotype.Service

@Service
class ResponseService {
    /**
     * Rest API return 형식
     * @property code 0, -1실패
     * @property msg 성공, 실패
     * @constructor Create empty Common response
     */
    enum class CommonResponse(var code: Int, var msg: String) {
        SUCCESS(0, "성공"),
        FAIL(-1, "실패")
    }

    /**
     * Rest API 단일(T) 리턴
     * @param T Structure or Class or String or Int ...
     * @param data T
     * @return CommonResult + T
     */
    fun <T> getSingleResult(data : T) = SingleResult<T>().apply {
        this.data = data
        setSuccessResult(this)
    }

    /**
     * Rest API 리스트(List<T>) 리턴
     * @param T Structure or Class or String or Int ...
     * @param data List<T>
     * @return CommonResult + List<T>
     */
    fun <T> getListResult(data : List<T>) = ListResult<T>().apply {
        this.data = data
        setSuccessResult(this)
    }

    /**
     * Rest API 성공 리턴
     * @return CommonResult
     */
    fun getSuccessResult() = CommonResult().apply {
        setSuccessResult(this)
    }

    /**
     * Rest API 실패 리턴
     * @param code fail code
     * @param msg fail message
     * @return CommonResult
     */
    fun getFailResult(code : Int, msg : String) = CommonResult().apply {
        this.success = false
        this.code = code
        this.msg = msg
    }

    /**
     * Rest API set 성공 리턴
     * @param result CommonResult
     */
    fun setSuccessResult(result : CommonResult) {
        result.success = true
        result.code = CommonResponse.SUCCESS.code
        result.msg = CommonResponse.SUCCESS.msg
    }
}
