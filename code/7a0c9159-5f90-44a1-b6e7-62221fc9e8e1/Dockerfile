FROM openjdk:11
WORKDIR /app
COPY . /app
RUN javac Main.java
COPY run.sh /app/run.sh
RUN chmod +x /app/run.sh
CMD ["/app/run.sh"]