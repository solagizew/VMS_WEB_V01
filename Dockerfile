FROM tomcat:9.0

# Remove default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your ROOT.war (already at repo root) to Tomcat webapps
COPY ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
