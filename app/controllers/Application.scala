package controllers

import play.api._
import play.api.mvc._

object Application extends BaseController {

  def index = Action {

      request =>
          println("Languages: " + request.acceptLanguages.map(_.code).mkString(", "))

          decorate(views.html.index("Hello"),
              "Online Check-In - Norwegian Cruise Line",
              views.html.layout.apply)

           Ok(views.html.index("Your new application is ready."))
  }

}