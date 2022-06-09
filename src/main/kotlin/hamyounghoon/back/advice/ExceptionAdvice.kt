package hamyounghoon.back.advice

import hamyounghoon.back.advice.exception.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import hamyounghoon.back.service.common.ResponseService
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionAdvice {
    @Autowired
    lateinit var responseService: ResponseService
    lateinit var messageSource: MessageSource

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun defaultException(req: HttpServletRequest, exception: Exception) =
        responseService.getFailResult(getMessage("unKnown.code").toInt(), exception.message.toString())
    @ExceptionHandler(CommunicationException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun communicationException(req: HttpServletRequest, exception: CommunicationException) =
        responseService.getFailResult(getMessage("communication.code").toInt(), "${getMessage("communication.msg")} : ${exception.message.toString()}")
    @ExceptionHandler(FileDownloadException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun fileDownloadException(req: HttpServletRequest, exception: FileDownloadException) =
        responseService.getFailResult(getMessage("fileDownload.code").toInt(), getMessage("fileDownload.msg"))
    @ExceptionHandler(FileUploadException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun fileUploadException(req: HttpServletRequest, exception: FileUploadException) =
        responseService.getFailResult(getMessage("fileUpload.code").toInt(), getMessage("fileUpload.msg"))
    @ExceptionHandler(NotExistResourceException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notExistResourceException(req: HttpServletRequest, exception: NotExistResourceException) =
        responseService.getFailResult(getMessage("notExist.code").toInt(), getMessage("notExist.msg"))
    @ExceptionHandler(NotFoundDeptException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notFoundDeptException(req: HttpServletRequest, exception: NotFoundDeptException) =
        responseService.getFailResult(getMessage("notFoundDept.code").toInt(), getMessage("notFoundDept.msg"))
    @ExceptionHandler(NotFoundLanguageException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notFoundLanguageException(req: HttpServletRequest, exception: NotFoundLanguageException) =
        responseService.getFailResult(getMessage("notFoundLanguage.code").toInt(), getMessage("notFoundLanguage.msg"))
    @ExceptionHandler(NotFoundRolesException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notFoundRolesException(req: HttpServletRequest, exception: NotFoundRolesException) =
        responseService.getFailResult(getMessage("notFoundRoles.code").toInt(), getMessage("notFoundRoles.msg"))
    @ExceptionHandler(NotFoundSystemException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notFoundSystemException(req: HttpServletRequest, exception: NotFoundSystemException) =
        responseService.getFailResult(getMessage("notFoundSystem.code").toInt(), getMessage("notFoundSystem.msg"))
    @ExceptionHandler(NotFoundUserException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notFoundUserException(req: HttpServletRequest, exception: NotFoundUserException) =
        responseService.getFailResult(getMessage("notFoundUser.code").toInt(), getMessage("notFoundUser.msg"))
    @ExceptionHandler(NotOwnerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun notOwnerException(req: HttpServletRequest, exception: NotOwnerException) =
        responseService.getFailResult(getMessage("notOwner.code").toInt(), getMessage("notOwner.msg"))
    @ExceptionHandler(SignInFailedException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun signInFailedException(req: HttpServletRequest, exception: SignInFailedException) =
        responseService.getFailResult(getMessage("signInFailed.code").toInt(), getMessage("signInFailed.msg"))
    @ExceptionHandler(SignUpFailedException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun signUpFailedException(req: HttpServletRequest, exception: SignUpFailedException) =
        responseService.getFailResult(getMessage("signUpFailed.code").toInt(), getMessage("signUpFailed.msg"))

    protected fun getMessage(code: String) =
        getMessage(code, null)
    protected fun getMessage(code: String, args: Array<Any>?) =
        messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
}
