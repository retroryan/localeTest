
import controllers.SetLocaleFilter
import play.api.GlobalSettings

import play.api.Application
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.Action

import scala.concurrent.Future


object Global extends GlobalSettings {
    override def doFilter(next: EssentialAction): EssentialAction = {
        Filters(super.doFilter(next), SetLocaleFilter)
    }
}
