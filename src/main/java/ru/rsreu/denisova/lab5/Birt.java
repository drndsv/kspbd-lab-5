package ru.rsreu.denisova.lab5;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class Birt {

    private IReportEngine reportEngine;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() throws Exception {
        EngineConfig config = new EngineConfig();
        Platform.startup(config);
        IReportEngineFactory factory =
                (IReportEngineFactory) Platform.createFactoryObject(
                        IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        reportEngine = factory.createReportEngine(config);
    }

    @PreDestroy
    public void destroy() {
        if (reportEngine != null) {
            reportEngine.destroy();
        }
        Platform.shutdown();
    }

    public void generatePDF(String id,
                            HttpServletResponse response,
                            HttpServletRequest request) {

        IRunAndRenderTask task = null;

        try {
            IReportRunnable report =
                    reportEngine.openReportDesign("users.rptdesign");

            task = reportEngine.createRunAndRenderTask(report);

            Map<String, Object> params = new HashMap<>();
            params.put("p_id",
                    (id == null || id.isEmpty()) ? null : Integer.parseInt(id));

            task.setParameterValues(params);

            PDFRenderOption options = new PDFRenderOption();
            options.setOutputFormat("pdf");
            options.setOutputStream(response.getOutputStream());

            task.setRenderOption(options);

            task.getAppContext().put(
                    "OdaJDBCDriverPassInConnection",
                    dataSource.getConnection()
            );

            response.setContentType("application/pdf");

            task.run();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (task != null) {
                task.close();
            }
        }
    }
}