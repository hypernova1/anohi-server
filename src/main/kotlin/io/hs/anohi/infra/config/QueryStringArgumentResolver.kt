package io.hs.anohi.infra.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.hs.anohi.infra.annotations.QueryStringArgumentResolver
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Configuration
class QueryStringArgumentResolver: HandlerMethodArgumentResolver {
    @Autowired
    private val mapper: ObjectMapper? = null


    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.getParameterAnnotation(QueryStringArgumentResolver::class.java) != null
    }


    @Throws(Exception::class)
    override fun resolveArgument(
        methodParameter: MethodParameter,
        modelAndViewContainer: ModelAndViewContainer?,
        nativeWebRequest: NativeWebRequest,
        webDataBinderFactory: WebDataBinderFactory?
    ): Any {
        val request: HttpServletRequest = nativeWebRequest.nativeRequest as HttpServletRequest
        if (request.queryString == null) {
            return methodParameter.getParameterType().newInstance()
        }
        val json: String = qs2json(request.queryString)
        return mapper!!.readValue(json, methodParameter.getParameterType())
    }


    private fun qs2json(a: String): String {
        var res = "{\""
        for (i in a.indices) {
            if (a[i] == '=') {
                res += "\"" + ":" + "\""
            } else if (a[i] == '&') {
                res += "\"" + "," + "\""
            } else {
                res += a[i]
            }
        }
        res += "\"" + "}"
        return res
    }
}