// @GENERATOR:play-routes-compiler
// @SOURCE:conf/brands.routes

package brands

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:1
  BrandController_0: v1.brand.BrandController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:1
    BrandController_0: v1.brand.BrandController
  ) = this(errorHandler, BrandController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    brands.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, BrandController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """v1.brand.BrandController.list(request:Request)"""),
    ("""POST""", this.prefix, """v1.brand.BrandController.create(request:Request)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """v1.brand.BrandController.getByID(request:Request, id:String)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """v1.brand.BrandController.updateByID(request:Request, id:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<[^/]+>""", """v1.brand.BrandController.deleteByID(request:Request, id:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:1
  private[this] lazy val v1_brand_BrandController_list0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val v1_brand_BrandController_list0_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      BrandController_0.list(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "brands",
      "v1.brand.BrandController",
      "list",
      Seq(classOf[play.mvc.Http.Request]),
      "GET",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:2
  private[this] lazy val v1_brand_BrandController_create1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val v1_brand_BrandController_create1_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      BrandController_0.create(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "brands",
      "v1.brand.BrandController",
      "create",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """""",
      """""",
      Seq()
    )
  )

  // @LINE:3
  private[this] lazy val v1_brand_BrandController_getByID2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val v1_brand_BrandController_getByID2_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      BrandController_0.getByID(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "brands",
      "v1.brand.BrandController",
      "getByID",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "GET",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:4
  private[this] lazy val v1_brand_BrandController_updateByID3_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val v1_brand_BrandController_updateByID3_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      BrandController_0.updateByID(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "brands",
      "v1.brand.BrandController",
      "updateByID",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "PUT",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:5
  private[this] lazy val v1_brand_BrandController_deleteByID4_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val v1_brand_BrandController_deleteByID4_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      BrandController_0.deleteByID(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "brands",
      "v1.brand.BrandController",
      "deleteByID",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "DELETE",
      this.prefix + """""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:1
    case v1_brand_BrandController_list0_route(params@_) =>
      call { 
        v1_brand_BrandController_list0_invoker.call(
          req => BrandController_0.list(req))
      }
  
    // @LINE:2
    case v1_brand_BrandController_create1_route(params@_) =>
      call { 
        v1_brand_BrandController_create1_invoker.call(
          req => BrandController_0.create(req))
      }
  
    // @LINE:3
    case v1_brand_BrandController_getByID2_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        v1_brand_BrandController_getByID2_invoker.call(
          req => BrandController_0.getByID(req, id))
      }
  
    // @LINE:4
    case v1_brand_BrandController_updateByID3_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        v1_brand_BrandController_updateByID3_invoker.call(
          req => BrandController_0.updateByID(req, id))
      }
  
    // @LINE:5
    case v1_brand_BrandController_deleteByID4_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        v1_brand_BrandController_deleteByID4_invoker.call(
          req => BrandController_0.deleteByID(req, id))
      }
  }
}
