package codes.hipporasy.store.data.service

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


sealed class Failure : IOException() {
    object JsonError : Failure()
    object UnknownError : Failure()
    object UnknownHostError : Failure()
    object EmptyResponse : Failure()
    object ConnectivityError : Failure()
    object InternetError : Failure()
    object UnAuthorizedException : Failure()
    object ParsingDataError : Failure()
    object IgnorableError : Failure()
    data class TimeOutError(override var message: String) : Failure()
    data class ApiError(var code: Int = 0, override var message: String) : Failure()
    data class ServerError(var code: Int = 0, override var message: String) : Failure()
    data class NotFoundException(override var message: String) : Failure()
    data class SocketTimeoutError(override var message: String) : Failure()
    data class BusinessError(override var message: String, val stackTrace: String) : Failure()
    data class HttpError(var code: Int, override var message: String) : Failure()
}


enum class HttpStatusCode(val code: Int) {

    Unknown(-1),

    // Client Errors
    BadRequest(400),
    Unauthorized(401),
    PaymentRequired(402),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    ProxyAuthenticationRequired(407),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PreconditionFailed(412),
    PayloadTooLarge(413),
    UriTooLong(414),
    UnsupportedMediaType(415),
    RangeNotSatisfiable(416),
    ExpectationFailed(417),
    ImATeapot(418),
    MisdirectedRequest(421),
    UnProcessableEntity(422),
    Locked(423),
    FailedDependency(424),
    UpgradeRequired(426),
    PreconditionRequired(428),
    TooManyRequests(429),
    RequestHeaderFieldsTooLarge(431),
    UnavailableForLegalReasons(451),

    // Server Errors
    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HttpVersionNotSupported(505),
    VariantAlsoNegates(506),
    InsufficientStorage(507),
    LoopDetected(508),
    NotExtended(510),
    NetworkAuthenticationRequired(511);
}

fun Throwable.handleThrowable(): Failure {
    // Timber.e(this)
    return if (this is UnknownHostException) {
        Failure.ConnectivityError
    } else if (this is HttpException && this.code() == HttpStatusCode.Unauthorized.code) {
        Failure.UnAuthorizedException
    } else if (this is SocketTimeoutException) {
        Failure.SocketTimeoutError(this.message!!)
    } else if (this.message != null) {
        Failure.NotFoundException(this.message!!)
    } else {
        Failure.UnknownError
    }
}