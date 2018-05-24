package br.com.lsd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import br.com.lsd.security.Constants;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.hibernate.HibernateSessionFactory;

@SpringBootApplication
public class PrimeMachineApplication extends SpringBootServletInitializer {
 
    private static Class<PrimeMachineApplication> applicationClass = PrimeMachineApplication.class;

	@Autowired
	ServletContext context; 

	@Autowired
	Environment env;
	/*
    @Inject
    private Environment env;*/

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_CLOUD = "cloud";

    public static final String CREDENTIAL_ADMINSTRATOR 	= "ADMIN";
    public static final String CREDENTIAL_ANALYST 		= "ANALYST";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

	public static void main(String[] args) {
		SpringApplication.run(PrimeMachineApplication.class, args);
	}

    /**
     * Initializes hipster.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.	
     */
    @PostConstruct
    public void initApplication() {

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
        	/*----- System.out.println("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");*/
        }
    }
/*
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PrimeMachineApplication.class);
        Environment env = app.run(args).getEnvironment();
        try {

        	HibernateSessionFactory.getSession();
        	
        	
        	
        	
        	try {
				GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDNaYmm_ebMMP_yBrzML_KbQAYNU2g_uuM");
				GeocodingResult[] results =  GeocodingApi.geocode(context,
				    "32 RUA JOSE AMERICO ZANGIROLANO SANTA OLGA Presidente Prudente SP").await();
				System.out.println(results[0].formattedAddress);
				System.out.println(results[0].geometry.location);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	
			String s = "\n----------------------------------------------------------\n\t" +
			        	"Application '{}' is running! Access URLs:\n\t" + env.getProperty("spring.application.name") +			        	
			        	"External: \thttp://"+
					    InetAddress.getLocalHost().getHostAddress() +":"+ 
			        	env.getProperty("server.port") +"\n----------------------------------------------------------";

			System.out.println(s);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
