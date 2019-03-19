package com.example.propoverwr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class PropertiesLogger implements ApplicationListener<ApplicationPreparedEvent> {

  private ConfigurableEnvironment environment;

  @Override
  public void onApplicationEvent(ApplicationPreparedEvent event) {
    environment = event.getApplicationContext().getEnvironment();
    printProperties();
  }

  public void printProperties() {
    for (EnumerablePropertySource propertySource : findPropertiesPropertySources()) {

      log.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
      log.info("******* start " + propertySource.getName() + " *******");
      log.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
      String[] propertyNames = propertySource.getPropertyNames();
      Arrays.sort(propertyNames);
      for (String propertyName : propertyNames) {
        String resolvedProperty = environment.getProperty(propertyName);
        String sourceProperty = propertySource.getProperty(propertyName).toString();
        if(resolvedProperty.equals(sourceProperty)) {
          log.info("{}={}", propertyName, resolvedProperty);
        }else {
          log.info("{}={} OVERRIDDEN to {}", propertyName, sourceProperty, resolvedProperty);
        }
      }
      log.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
      log.info("******* end " + propertySource.getName() + " *******");
      log.info("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
    }
  }

  private List<EnumerablePropertySource> findPropertiesPropertySources() {
    List<EnumerablePropertySource> propertiesPropertySources = new LinkedList<>();
    for (PropertySource<?> propertySource : environment.getPropertySources()) {
      if (propertySource instanceof EnumerablePropertySource) {
        propertiesPropertySources.add((EnumerablePropertySource) propertySource);
      }
    }
    return propertiesPropertySources;
  }

}