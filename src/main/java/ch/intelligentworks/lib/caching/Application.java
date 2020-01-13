package ch.intelligentworks.lib.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@EnableAsync
@EnableAspectJAutoProxy
public class Application
{
	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	}
}


