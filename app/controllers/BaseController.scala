package controllers

import play.twirl.api.Html
import play.api.mvc.Results.Status
import play.api.mvc.{Cookie, Controller, Result, RequestHeader}
import play.api.i18n.Lang
import play.api.Play.current


trait BaseController extends Controller {

    def decorate(
                    content: Lang => Html,
                    title: String = "Norwegian Cruise Line",
                    layout: => (String, Html) => Html,
                    status: Status = Ok
                    )(
                    implicit request: RequestHeader
                    ): Result = {
        val locale = request.getQueryString("locale")


        if (locale.isDefined) {
            val lang: Lang = Lang(locale.get)
            status(layout(title, content(lang))).withLang(lang).withCookies(Cookie("PLAY_LANG",lang.toString))
        }
        else
            status(layout(title, content(play.api.i18n.Lang.preferred(request.acceptLanguages))))
    }

/*    implicit override def request2lang(implicit request: RequestHeader) = {
        //TODO: Does not saves it in the cookie

        val localeFromGET = request.getQueryString("locale")
        val maybeLangFromCookie = request.cookies.get("PLAY_LANG").flatMap(c => Lang.get(c.value))
        val lang = if (localeFromGET.isDefined) {
            Lang(localeFromGET.get)
        } else {
            maybeLangFromCookie.getOrElse(play.api.i18n.Lang.preferred(request.acceptLanguages))
        }
        //}.getOrElse(request.acceptLanguages.headOption.getOrElse(play.api.i18n.Lang.defaultLang))

        lang
    }*/

}
