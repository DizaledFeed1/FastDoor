MRDVERKIN
Streamlining Success Through Seamless Order Coordination

last-commit repo-top-language repo-language-count
Built with the tools and technologies:

Markdown Spring Docker XML GitHub%20Actions PostgreSQL

Table of Contents
Overview
Getting Started
Prerequisites
Installation
Usage
Testing
Overview
MrDverkin is a versatile backend solution crafted to optimize order processing, installer coordination, and user management within a secure and scalable environment. Leveraging Spring Boot and Docker, it ensures consistent deployment and robust security configurations, including role-based access control and session management. The system features RESTful APIs, data transfer objects, and integrated database repositories to facilitate seamless data handling and operational workflows. Its web interface templates support user interactions, while automated notifications streamline communication. Designed for developers, MrDverkin empowers teams to build maintainable, efficient backend systems with ease. Why MrDverkin?

This project simplifies complex order and role management workflows while ensuring security and scalability. The core features include:

🛠️ API-Driven Architecture: RESTful endpoints for orders, installers, and users, enabling easy integration.
🔒 Security & Role Management: Role-based access control, session handling, and authentication for secure operations.
🐳 Dockerized Deployment: Containerized environment for consistent, portable production releases.
📧 Automation & Notifications: Automated WhatsApp alerts and scheduled tasks for streamlined communication.
🧱 Modular & Maintainable: Clear separation of concerns with DTOs, repositories, and configuration classes.
Getting Started
Prerequisites
This project requires the following dependencies:

Programming Language: Java
Package Manager: Maven
Container Runtime: Docker
Installation
Build MrDverkin from the source and install dependencies:

Clone the repository:

❯ git clone https://github.com/DizaledFeed1/MrDverkin
Navigate to the project directory:

❯ cd MrDverkin
Install the dependencies:

Using docker:

❯ docker build -t DizaledFeed1/MrDverkin .
Using maven:

❯ mvn install
Usage
Run the project with:

Using docker:

docker run -it {image_name}
Using maven:

mvn exec:java
Testing
Mrdverkin uses the {test_framework} test framework. Run the test suite with:

Using docker:

echo 'INSERT-TEST-COMMAND-HERE'
Using maven:

mvn test
⬆ Return
