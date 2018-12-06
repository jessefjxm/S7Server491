package com.bdoemu.webserver;

import com.bdoemu.commons.thread.ThreadPool;
import com.bdoemu.commons.utils.ServerInfoUtils;
import com.bdoemu.core.configs.ServerConfig;
import com.bdoemu.core.configs.WebserverConfig;
import com.bdoemu.core.startup.StartupComponent;
import com.bdoemu.gameserver.model.team.guild.Guild;
import com.bdoemu.gameserver.model.team.guild.model.GuildComment;
import com.bdoemu.gameserver.model.team.guild.services.GuildService;
import com.bdoemu.gameserver.worldInstance.World;
import com.bdoemu.webserver.engine.ThymeleafTemplateEngine;
import com.bdoemu.webserver.model.BoardGameRequest;
import com.bdoemu.webserver.model.BoardGameResponse;
import com.bdoemu.webserver.model.view.AdminViewModel;
import com.bdoemu.webserver.model.view.BaseViewModel;
import com.bdoemu.webserver.model.view.BeautyAlbumViewModel;
import com.bdoemu.webserver.model.view.GuildCommentViewModel;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Spark;
import spark.TemplateEngine;
import spark.utils.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@StartupComponent("Service")
public class WebService implements Runnable {
    private static final Logger log;
    private static final AtomicReference<Object> instance;
    private static Gson gson;

    static {
        log = LoggerFactory.getLogger((Class) WebService.class);
        instance = new AtomicReference<Object>();
        WebService.gson = new Gson();
    }

    private ThymeleafTemplateEngine templateEngine;

    public WebService() {
        ServerInfoUtils.printSection("WebService Loading");
        if (WebserverConfig.ENABLE) {
            this.templateEngine = new ThymeleafTemplateEngine();
            ThreadPool.getInstance().executeGeneral((Runnable) this);
        } else {
            WebService.log.info("Ingame webservice disabled duo config.");
        }
    }

    public static WebService getInstance() {
        Object value = WebService.instance.get();
        if (value == null) {
            synchronized (WebService.instance) {
                value = WebService.instance.get();
                if (value == null) {
                    final WebService actualValue = new WebService();
                    value = ((actualValue == null) ? WebService.instance : actualValue);
                    WebService.instance.set(value);
                }
            }
        }
        return (WebService) ((value == WebService.instance) ? null : value);
    }

    @Override
    public void run() {
        Spark.staticFiles.externalLocation(WebserverConfig.DATA_PATH);
        Spark.secure("E:\\GitHub\\Developers In Pijamas\\BlackDesertOnline\\src\\data\\keystore.jks", "password", null, null);
        Spark.port(WebserverConfig.PORT);
        Spark.threadPool(WebserverConfig.THREAD_COUNT_MAX, WebserverConfig.THREAD_COUNT_MIN, WebserverConfig.TIMEOUT);
        if (!WebserverConfig.DEBUG) {
            Spark.before(new Filter[]{(request, response) -> {
                boolean authenticated = false;
                if (request.queryParams("userNo") != null) {
                    final int accountId = Integer.parseInt(request.queryParams("userNo"));
                    if (accountId >= 0 && World.getInstance().getPlayerByAccount(accountId) != null) {
                        authenticated = true;
                    }
                }
                if (request.queryParams("certKey") == null) {
                    authenticated = false;
                }
                if (!request.userAgent().equals("BlackDesert")) {
                    authenticated = false;
                }
                if (!authenticated) {
                    Spark.halt(403, "You are not welcome here");
                }
            }});
        }
        Spark.get("/", (req, res) -> "BDO WebServer v0.1");
        Spark.get("/ErrorPage", (req, res) -> Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage"), (TemplateEngine) this.templateEngine);
        this.registerAdminPath();
        this.registerGuildCommentPath();
        this.register50LvlGuidePath();
        this.registerBeautyAlbumPath();
        this.registerPearlMarblePath();
        Spark.awaitInitialization();
    }

    private void registerAdminPath() {
        Spark.get("/Admin/", (request, response) -> {
            final AdminViewModel model = new AdminViewModel(request.queryMap().toMap());
            return Spark.modelAndView((Object) model.toMap(), "Areas/Admin/Index");
        }, (TemplateEngine) this.templateEngine);
    }

    private void registerGuildCommentPath() {
        Spark.get("/guild/", (request, response) -> {
            response.redirect("/guild/GuildComments?" + request.queryString());
            return null;
        });
        Spark.get("/guild/GuildComments", (request, response) -> {
            final GuildCommentViewModel model = new GuildCommentViewModel(request.queryMap().toMap());
            final Guild guild = GuildService.getInstance().getGuild(model.getGuildNo());
            if (guild == null) {
                return Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage");
            }
            if (!guild.containsMember(model.getUserNo())) {
                return Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage");
            }
            final List<GuildComment> commentsToShow = new ArrayList<GuildComment>();
            final SortedMap<Long, GuildComment> comments = (model.getCommentNo() == 0L) ? guild.getComments() : guild.getComments().tailMap(model.getCommentNo());
            for (final Map.Entry<Long, GuildComment> entry : comments.entrySet()) {
                commentsToShow.add(entry.getValue());
                if (commentsToShow.size() == 10) {
                    if (comments.values().size() > 10) {
                        model.setCommentNo(entry.getKey());
                        break;
                    }
                    break;
                }
            }
            model.setComments(commentsToShow);
            return Spark.modelAndView((Object) model.toMap(), "Areas/Guild/GuildComments");
        }, (TemplateEngine) this.templateEngine);
        Spark.post("/guild/WriteGuildComment", (request, response) -> {
            final GuildCommentViewModel model = new GuildCommentViewModel(request.queryMap().toMap());
            if (StringUtils.isEmpty((Object) model.getComment())) {
                model.setParam("An error occurred while executing the command definition. See the inner exception for details.");
                return Spark.modelAndView((Object) model.toMap(), "Areas/Guild/Error");
            }
            final Guild guild = GuildService.getInstance().getGuild(model.getGuildNo());
            if (guild == null) {
                return Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage");
            }
            if (!guild.containsMember(model.getUserNo())) {
                return Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage");
            }
            guild.addComment(model.getUserNo(), model.getComment());
            return Spark.modelAndView((Object) model.toMap(), "Areas/Guild/GuildComments");
        }, (TemplateEngine) this.templateEngine);
        Spark.get("/guild/DeleteGuildComment", (request, response) -> {
            final GuildCommentViewModel model = new GuildCommentViewModel(request.queryMap().toMap());
            if (model.getCmtId() <= 0) {
                model.setParam("Invalid comment id.");
                return Spark.modelAndView((Object) model.toMap(), "Areas/Guild/Error");
            }
            final Guild guild = GuildService.getInstance().getGuild(model.getGuildNo());
            if (guild == null) {
                return Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage");
            }
            if (!guild.containsMember(model.getUserNo())) {
                return Spark.modelAndView((Object) Collections.EMPTY_MAP, "ErrorPage");
            }
            guild.removeComment(model.getCmtId());
            return Spark.modelAndView((Object) model.toMap(), "Areas/Guild/GuildComments");
        }, (TemplateEngine) this.templateEngine);
        Spark.get("/guild/Error", (request, response) -> {
            final Map<String, String> map = new HashMap<String, String>();
            map.put("param", request.queryParams("param"));
            return Spark.modelAndView((Object) map, "Areas/Guild/Error");
        }, (TemplateEngine) this.templateEngine);
    }

    private void register50LvlGuidePath() {
        Spark.get("/Guide", (request, response) -> Spark.modelAndView((Object) Collections.EMPTY_MAP, "Areas/Guide/Guide"), (TemplateEngine) this.templateEngine);
    }

    private void registerBeautyAlbumPath() {
        Spark.get("/customizing/", (request, response) -> {
            response.redirect("/customizing/Frame?" + request.queryString());
            return null;
        });
        Spark.get("/customizing/Frame", (request, response) -> {
            final BeautyAlbumViewModel model = new BeautyAlbumViewModel(request.queryMap().toMap());
            return Spark.modelAndView((Object) model.toMap(), "Areas/Customizing/Frame");
        }, (TemplateEngine) this.templateEngine);
        Spark.get("/customizing/MyFolder", (request, response) -> {
            final BeautyAlbumViewModel model = new BeautyAlbumViewModel(request.queryMap().toMap());
            return Spark.modelAndView((Object) model.toMap(), "Areas/Customizing/MyFolder");
        }, (TemplateEngine) this.templateEngine);
        Spark.post("/customizing/MyFolder/GetCustomizingMyFolder", (request, response) -> {
            final BeautyAlbumViewModel model = new BeautyAlbumViewModel(request.queryMap().toMap());
            return Spark.modelAndView((Object) model.toMap(), "Areas/Customizing/MyFolder");
        }, (TemplateEngine) this.templateEngine);
    }

    private void registerPearlMarblePath() {
        Spark.get("/BoardGame/", (request, response) -> {
            response.redirect("/BoardGame/Game/Start?" + request.queryString());
            return null;
        });
        Spark.get("/BoardGame/Game/Start", (request, response) -> {
            final BaseViewModel model = new BaseViewModel(request.queryMap().toMap());
            return Spark.modelAndView((Object) model.toMap(), "Areas/BoardGame/Start");
        }, (TemplateEngine) this.templateEngine);
        Spark.post("/BoardGame/Game/Update", (request, response) -> {
            final BoardGameRequest gameRequest = new BoardGameRequest(request.queryMap().toMap());
            if (gameRequest.getUserId() < 0 || World.getInstance().getPlayerByAccount(gameRequest.getUserId()) == null) {
                return null;
            }
            if (gameRequest.getServerNo() < 0 || gameRequest.getServerNo() != ServerConfig.SERVER_ID) {
                return null;
            }
            final BoardGameResponse boardGameResponse = new BoardGameResponse();
            return boardGameResponse;
        }, WebService.gson::toJson);
    }

    private void registerGuildInformationPath() {
    }

    private void registerGuildRankPath() {
    }
}
