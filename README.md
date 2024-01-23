<p>&nbsp;</p>
<h1>Hollywood Production Application</h1>
<p>The Hollywood Production Application is a demo project for managing videos, users, and interactions within a Hollywood production house.</p>
<h2>Table of Contents</h2>
<ul>
<li><a href="#prerequisites">Prerequisites</a></li>
<li><a href="#getting-started">Getting Started</a>
<ul>
<li><a href="#running-the-application-locally">Running the Application Locally</a></li>
<li><a href="#building-and-running-with-docker">Building and Running with Docker</a></li>
</ul>
</li>
<li><a href="#api-documentation">API Documentation</a></li>
<li><a href="#application-structure">Application Structure</a></li>
<li><a href="#dependencies">Dependencies</a></li>
<li><a href="#contributing">Contributing</a></li>
<li><a href="#license">License</a></li>
</ul>
<h2 id="prerequisites">Prerequisites</h2>
<p>Before running the application, make sure you have the following prerequisites installed:</p>
<ul>
<li><a href="https://openjdk.java.net/projects/jdk/17/">Java 17</a></li>
<li><a href="https://maven.apache.org/download.cgi">Maven</a> (for building the application)</li>
<li><a href="https://www.docker.com/get-started">Docker</a> (for running the application in a container)</li>
</ul>
<h2 id="getting-started">Getting Started</h2>
<h3 id="running-the-application-locally">Running the Application Locally</h3>
<ol>
<li>Clone this repository to your local machine:
<pre><code>git clone https://github.com/abuabbasli/Hollywood-Production.git</code></pre>
</li>
<li>Navigate to the project's root directory:
<pre><code>cd Hollywood-Production</code></pre>
</li>
<li>Build and run the application using Maven:
<pre><code>mvn spring-boot:run</code></pre>
</li>
<li>Your application should now be running locally. Access it by opening a web browser and navigating to <a href="http://localhost:8080">http://localhost:8080</a>.</li>
</ol>
<h3 id="building-and-running-with-docker">Building and Running with Docker</h3>
<ol>
<li>Clone this repository to your local machine (if you haven't already):
<pre><code>git clone https://github.com/abuabbasli/Hollywood-Production.git</code></pre>
</li>
<li>Navigate to the project's root directory:
<pre><code>cd Hollywood-Production</code></pre>
</li>
<li>Build the Docker image using the provided Dockerfile:
<pre><code>docker build -t hollywood-production-app .</code></pre>
<ul>
<li><code>-t hollywood-production-app</code> tags the Docker image with a name of your choice. In this example, we used "hollywood-production-app."</li>
</ul>
</li>
<li>Run the Docker container:
<pre><code>docker run -p 8080:8080 hollywood-production-app</code></pre>
<ul>
<li><code>-p 8080:8080</code> maps port 8080 from the host to port 8080 inside the container.</li>
<li><code>hollywood-production-app</code> is the name of the Docker image you built.</li>
</ul>
</li>
<li>Access the application in your web browser by navigating to <a href="http://localhost:8080">http://localhost:8080</a>.</li>
</ol>
<h2 id="api-documentation">API Documentation</h2>
<p>The API documentation is generated using SpringDoc OpenAPI. You can access it by navigating to <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a> (when running locally).</p>
<h2 id="application-structure">Application Structure</h2>
<ul>
<li><strong>Controller</strong>: Contains the REST API endpoints.</li>
<li><strong>Entity</strong>: Defines the data models (e.g., User, Video).</li>
<li><strong>Repository</strong>: Handles data access and database operations.</li>
<li><strong>Service</strong>: Implements business logic and interacts with repositories.</li>
<li><strong>Response</strong>: Defines custom response classes.</li>
<li><strong>Dockerfile</strong>: Used for building the Docker image.</li>
<li><strong>pom.xml</strong>: Maven configuration file.</li>
</ul>
<h2 id="dependencies">Dependencies</h2>
<ul>
<li>Spring Boot</li>
<li>Spring Data JPA</li>
<li>SpringDoc OpenAPI</li>
<li>PostgreSQL Database</li>
<li>Lombok</li>
<li>Docker (for containerization)</li>
</ul>
