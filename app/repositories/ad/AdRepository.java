package repositories.ad;

import com.fasterxml.jackson.databind.JsonNode;
import models.Ad;
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
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;


@Singleton
public class AdRepository implements IAdRepository {

    private final JPAApi jpaApi;
    private final AdExecutionContext ec;
    private final CircuitBreaker<Optional<Ad>> circuitBreaker =
            new CircuitBreaker<Optional<Ad>>().withFailureThreshold(1).withSuccessThreshold(3);
    private final SqlSessionFactory sqlSessionFactory;


    @Inject
    public AdRepository(JPAApi api, AdExecutionContext ec, MyBatisConfig myBatisConfig) {
        this.jpaApi = api;
        this.ec = ec;
        this.sqlSessionFactory = myBatisConfig.sqlSessionFactory();
    }

    @Override
    public CompletionStage<List> list(Http.Request request) {
        return supplyAsync(() -> wrap(em -> {
            List listres = null;
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                String sqlRes = getSQlQueryForFilter(request);
                listres = em.createNativeQuery(sqlRes, Ad.class).getResultList();
            } catch (Exception e) {
                e.getStackTrace();
            }
            return listres;
        }), ec);
    }

    @Override
    public CompletionStage<Optional<Ad>> getByID(Long id) {
        return supplyAsync(() -> Failsafe.with(circuitBreaker).get(() -> {
            Ad ad = null;
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                ad = sqlSession.selectOne("ads.getAdByID", id);
            } catch (Exception e) {
                e.getStackTrace();
            }
            return Optional.ofNullable(ad);
        }), ec);
    }

    @Override
    public CompletionStage<Optional<Ad>> updateByID(Long id, Http.Request request) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> {
            Ad adReq = null;
            final Ad data = em.find(Ad.class, id);
            if (data != null) {
                try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                    JsonNode jsonNode = request.body().asJson();
                    adReq = Json.fromJson(jsonNode, Ad.class);
                    adReq.setId(id);
                    sqlSession.update("ads.updateAd", adReq);
                    sqlSession.commit();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            return Optional.ofNullable(adReq);
        })), ec);
    }

    @Override
    public CompletionStage<Optional<Integer>> deleteByID(Long id) {
        return supplyAsync(() -> wrap(em -> {
            final Ad data = em.find(Ad.class, id);
            Integer delCnt = null;
            if (data != null) {
                try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                    delCnt = sqlSession.delete("ads.deleteAdByID", id);
                    sqlSession.commit();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            return Optional.ofNullable(delCnt);
        }), ec);

    }

    @Override
    public CompletionStage<Ad> create(Http.Request request) {
        return supplyAsync(() -> {
            Ad adReq = null;
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                JsonNode jsonNode = request.body().asJson();
                adReq = Json.fromJson(jsonNode, Ad.class);
                Integer in = sqlSession.insert("ads.create", adReq);
                sqlSession.commit();
            } catch (Exception e) {
                e.getStackTrace();
            }
            return adReq;
        }, ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private String getSQlQueryForFilter(Http.Request request){
        Map<String, String[]> qMap = request.queryString();
        StringBuilder sql = new StringBuilder("SELECT * FROM ads");
        if(qMap.size() > 0){
            sql.append(" WHERE ");
            for (Map.Entry<String, String[]> entry : qMap.entrySet()) {
                sql.append(entry.getKey() + " = " + entry.getValue()[0] + " AND ");
            }
            return sql.substring(0, sql.length() - 5);
        }else{
            return sql.toString();
        }
    }

}
