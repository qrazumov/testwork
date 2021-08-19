package repositories;

import com.fasterxml.jackson.databind.JsonNode;
import models.Brand;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Http;
import utils.MyBatisConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;


@Singleton
public class BrandRepository implements IBrandRepository{

    private final JPAApi jpaApi;
    private final BrandExecutionContext ec;
    private final CircuitBreaker<Optional<Brand>> circuitBreaker =
            new CircuitBreaker<Optional<Brand>>().withFailureThreshold(1).withSuccessThreshold(3);
    private final SqlSessionFactory sqlSessionFactory;


    @Inject
    public BrandRepository(JPAApi api, BrandExecutionContext ec, MyBatisConfig myBatisConfig) {
        this.jpaApi = api;
        this.ec = ec;
        this.sqlSessionFactory = myBatisConfig.sqlSessionFactory();
    }

    @Override
    public CompletionStage<Stream<Brand>> list() {
        return supplyAsync(() -> {
            Stream<Brand> brandStream = null;
            try(SqlSession sqlSession = sqlSessionFactory.openSession()){
                List<Brand> brands = sqlSession.selectList("brands.getBrands");
                brandStream = brands.stream();
            }catch (Exception e){e.getStackTrace();}
            return brandStream;
        }, ec);
    }

    @Override
    public CompletionStage<Optional<Brand>> getByID(Long id) {
        return supplyAsync(() -> Failsafe.with(circuitBreaker).get(() -> {
            Brand brand = null;
            try(SqlSession sqlSession = sqlSessionFactory.openSession()){
                brand = sqlSession.selectOne("brands.getBrandByID", id);
            }catch (Exception e){e.getStackTrace();}
            return  Optional.ofNullable(brand);
        }), ec);
    }

    @Override
    public CompletionStage<Optional<Brand>> updateByID(Long id, Http.Request request) {
        return supplyAsync(() -> wrap(em ->Failsafe.with(circuitBreaker).get(() -> {
                Brand brandReq = null;
                final Brand data = em.find(Brand.class, id);
                if(data != null){
                    try(SqlSession sqlSession = sqlSessionFactory.openSession()){
                        JsonNode jsonNode = request.body().asJson();
                        brandReq = Json.fromJson(jsonNode, Brand.class);
                        brandReq.setId(id);
                        sqlSession.update("brands.updateBrand", brandReq);
                        sqlSession.commit();
                    }catch (Exception e){e.getStackTrace();}
                }
                return Optional.ofNullable(brandReq);
        })), ec);
    }

    @Override
    public CompletionStage<Optional<Integer>> deleteByID(Long id) {
        return supplyAsync(() -> wrap(em ->{
            final Brand data = em.find(Brand.class, id);
            Integer delCnt = null;
            if(data != null){
                try(SqlSession sqlSession = sqlSessionFactory.openSession()){
                    delCnt = sqlSession.delete("brands.deleteBrandByID", id);
                    sqlSession.commit();
                }catch (Exception e){e.getStackTrace();}
            }
            return  Optional.ofNullable(delCnt);
        }), ec);

    }

    @Override
    public CompletionStage<Brand> create(Brand brand) {
        return supplyAsync(() -> {
            try(SqlSession sqlSession = sqlSessionFactory.openSession()){
                Integer in = sqlSession.insert("brands.create", brand);
                sqlSession.commit();
            }catch (Exception e){e.getStackTrace();}
            return brand;
        }, ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

}
