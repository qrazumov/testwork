// @GENERATOR:play-routes-compiler
// @SOURCE:conf/brands.routes

package v1.brand;

import brands.RoutesPrefix;

public class routes {
  
  public static final v1.brand.ReverseBrandController BrandController = new v1.brand.ReverseBrandController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final v1.brand.javascript.ReverseBrandController BrandController = new v1.brand.javascript.ReverseBrandController(RoutesPrefix.byNamePrefix());
  }

}
