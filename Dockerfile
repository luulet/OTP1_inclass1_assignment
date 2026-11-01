FROM maven:3.9-eclipse-temurin-24
RUN apt-get update && apt-get install -y --no-install-recommends \
    libgtk-3-0 libxtst6 libxxf86vm1 libgl1 \
    fontconfig fonts-dejavu fonts-noto-cjk fonts-ipafont-gothic fonts-takao-gothic fonts-noto-core fonts-noto-ui-core \
    && fc-cache -f && rm -rf /var/lib/apt/lists/*
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8 JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
WORKDIR /app
COPY . .
RUN mvn -q clean compile
ENV DISPLAY=host.docker.internal:0.0
CMD ["mvn", "-q", "javafx:run"]
