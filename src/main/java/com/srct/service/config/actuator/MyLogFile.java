package com.srct.service.config.actuator;

import java.io.File;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.srct.service.utils.log.Log;

/**
 * 
 * @author srct
 * @TODO My dream is check log through the web browser
 */
// @Component
@WebEndpoint(id = "mylogfile")
public class MyLogFile {

    @ReadOperation
    public Resource logFile(@Selector String name) {
        Log.i("============logFile============");
        Resource logFileResource = new FileSystemResource(new File(name));
        if (logFileResource == null || !logFileResource.isReadable()) {
            return null;
        }
        return logFileResource;
    }
}
