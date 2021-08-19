// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:1
  brands_Routes_2: brands.Routes,
  // @LINE:2
  models_Routes_1: models.Routes,
  // @LINE:3
  ads_Routes_0: ads.Routes,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:1
    brands_Routes_2: brands.Routes,
    // @LINE:2
    models_Routes_1: models.Routes,
    // @LINE:3
    ads_Routes_0: ads.Routes
  ) = this(errorHandler, brands_Routes_2, models_Routes_1, ads_Routes_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, brands_Routes_2, models_Routes_1, ads_Routes_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    prefixed_brands_Routes_2_0.router.documentation,
    prefixed_models_Routes_1_1.router.documentation,
    prefixed_ads_Routes_0_2.router.documentation,
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:1
  private[this] val prefixed_brands_Routes_2_0 = Include(brands_Routes_2.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "v1/brands"))

  // @LINE:2
  private[this] val prefixed_models_Routes_1_1 = Include(models_Routes_1.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "v1/models"))

  // @LINE:3
  private[this] val prefixed_ads_Routes_0_2 = Include(ads_Routes_0.withPrefix(this.prefix + (if (this.prefix.endsWith("/")) "" else "/") + "v1/ads"))


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:1
    case prefixed_brands_Routes_2_0(handler) => handler
  
    // @LINE:2
    case prefixed_models_Routes_1_1(handler) => handler
  
    // @LINE:3
    case prefixed_ads_Routes_0_2(handler) => handler
  }
}
