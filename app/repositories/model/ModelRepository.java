package repositories.model;

import com.fasterxml.jackson.databind.JsonNode;
import models.Model;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;


@Singleton
public class ModelRepository implements IModelRepository {

    private final JPAApi jpaApi;
    private final ModelExecutionContext ec;
    private final CircuitBreaker<Optional<Model>> circuitBreaker =
            new CircuitBreaker<Optional<Model>>().withFailureThreshold(1).withSuccessThreshold(3);
    private final SqlSessionFactory sqlSessionFactory;


    @Inject
    public ModelRepository(JPAApi api, ModelExecutionContext ec, MyBatisConfig myBatisConfig) {
        this.jpaApi = api;
        this.ec = ec;
        this.sqlSessionFactory = myBatisConfig.sqlSessionFactory();
    }

    @Override
    public CompletionStage<Stream<Model>> list() {
        return supplyAsync(() -> {
            Stream<Model> modelStream = null;
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                List<Model> models = sqlSession.selectList("models.getModels");
                modelStream = models.stream();
            } catch (Exception e) {
                e.getStackTrace();
            }
            return modelStream;
        }, ec);
    }

    @Override
    public CompletionStage<Optional<Model>> getByID(Long id) {
        return supplyAsync(() -> Failsafe.with(circuitBreaker).get(() -> {
            Model model = null;
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                model = sqlSession.selectOne("models.getModelByID", id);
            } catch (Exception e) {
                e.getStackTrace();
            }
            return Optional.ofNullable(model);
        }), ec);
    }

    @Override
    public CompletionStage<Optional<Model>> updateByID(Long id, Http.Request request) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> {
            Model modelReq = null;
            final Model data = em.find(Model.class, id);
            if (data != null) {
                try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                    JsonNode jsonNode = request.body().asJson();
                    String name = jsonNode.get("name").asText();
                    // TODO
                    Date startProd = new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.get("start_prod").asText());
                    Date endProd = new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.get("end_prod").asText());
                    modelReq = new Model(name, startProd, endProd);
                    modelReq.setId(id);
                    sqlSession.update("models.updateModel", modelReq);
                    sqlSession.commit();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            return Optional.ofNullable(modelReq);
        })), ec);
    }

    @Override
    public CompletionStage<Optional<Integer>> deleteByID(Long id) {
        return supplyAsync(() -> wrap(em -> {
            final Model data = em.find(Model.class, id);
            Integer delCnt = null;
            if (data != null) {
                try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                    delCnt = sqlSession.delete("models.deleteModelByID", id);
                    sqlSession.commit();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            return Optional.ofNullable(delCnt);
        }), ec);

    }

    @Override
    public CompletionStage<Model> create(Http.Request request) {
        return supplyAsync(() -> {
            Model modelReq = null;
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                JsonNode jsonNode = request.body().asJson();
                String name = jsonNode.get("name").asText();
                // TODO
                Date startProd = new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.get("start_prod").asText());
                Date endProd = new SimpleDateFormat("yyyy-MM-dd").parse(jsonNode.get("end_prod").asText());
                modelReq = new Model(name, startProd, endProd);
                Integer in = sqlSession.insert("models.create", modelReq);
                sqlSession.commit();
            } catch (Exception e) {
                e.getStackTrace();
            }
            return modelReq;
        }, ec);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

}
