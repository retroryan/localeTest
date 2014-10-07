package controllers

import play.twirl.api.Html
import play.api.mvc.Results.Status
import play.api.mvc.{Controller, Result, RequestHeader}
import play.api.i18n.Lang
import play.api.Play.current


trait BaseController extends Controller {

    def decorate(
                    content: Html,
                    title: String = "Norwegian Cruise Line",
                    layout: => (String, Html) => Html,
                    status: Status = Ok
                    )(
                    implicit request: RequestHeader
                    ): Result = {
        val locale = request.getQueryString("locale")


        if (locale.isDefined)
            status(layout(title, content)).withLang(Lang(locale.get))
        else
            status(layout(title, content))
    }

    implicit override def request2lang(implicit request: RequestHeader) = {
        //TODO: Does not saves it in the cookie

        val localeFromGET = request.getQueryString("locale")
        val maybeLangFromCookie = request.cookies.get("PLAY_LANG").flatMap(c => Lang.get(c.value))
        if (localeFromGET.isDefined) {
            Lang(localeFromGET.get)
        } else {
            maybeLangFromCookie.getOrElse(play.api.i18n.Lang.preferred(request.acceptLanguages))
        }

        //}.getOrElse(request.acceptLanguages.headOption.getOrElse(play.api.i18n.Lang.defaultLang))
    }

}
