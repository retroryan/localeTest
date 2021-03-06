package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current

object Application extends BaseController {

  def index = Action {

      implicit request =>
          println("Languages: " + request.acceptLanguages.map(_.code).mkString(", "))

          decorate(lang => views.html.index("Hello"),
              "Online Check-In - Norwegian Cruise Line",
              views.html.layout.apply)
  }

    def second = Action {

        implicit request =>
            println("Languages: " + request.acceptLanguages.map(_.code).mkString(", "))

            Redirect(controllers.routes.Application.index())
    }

}