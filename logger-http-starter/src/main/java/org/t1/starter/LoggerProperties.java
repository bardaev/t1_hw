package org.t1.starter;

import ch.qos.logback.classic.Level;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoggerProperties {

    private boolean enabled = true;
    private Level logLevel = Level.INFO;

}
