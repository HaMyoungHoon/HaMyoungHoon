package hamyounghoon.back.model.common

import io.swagger.v3.oas.annotations.media.Schema

open class CommonResult {
    @field:Schema(description = "response state", example = "true, false") var success: Boolean = false
    @field:Schema(description = "response code", example = "0 : ok, other : fail") var code: Int = -9999
    @field:Schema(description = "response error message") var msg: String = ""
}
