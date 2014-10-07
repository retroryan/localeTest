package controllers

import play.api.mvc._
import scala.concurrent.Future
import play.api.i18n.Lang
import play.api.Play
import play.api.mvc.Result
import play.api.mvc.Cookie
import play.api.http.HeaderNames

import scala.concurrent.ExecutionContext.Implicits.global

object SetLocaleFilter extends Filter {
    def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
        val maybeLang = request.getQueryString("locale").flatMap(Lang.get)
        val requestWithLang = maybeLang.fold(request) { lang =>
            val langCookie = play.api.Play.maybeApplication map { implicit app => Cookie(Play.langCookieName, lang.code) }
            val newCookies = Cookies.merge(request.headers.get(HeaderNames.COOKIE).getOrElse(""), langCookie.toSeq)
            request.copy(headers = new Headers {
                val data = (request.headers.toMap + (HeaderNames.COOKIE -> Seq(newCookies))).toSeq
            })
        }
        next(requestWithLang) map { result =>
            play.api.Play.maybeApplication.fold(result) { implicit app =>
                maybeLang.fold(result) { lang => result.withLang(lang) }
            }
        }
    }
}