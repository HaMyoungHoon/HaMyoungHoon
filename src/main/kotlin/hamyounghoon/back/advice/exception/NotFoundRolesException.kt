package hamyounghoon.back.advice.exception

class NotFoundRolesException: RuntimeException {
    constructor(msg : String, t : Throwable): super(msg, t)
    constructor(msg : String): super(msg)
    constructor(): super()
}
