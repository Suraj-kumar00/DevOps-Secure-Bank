# SecureBank - A DevOps-Focused Banking Application

SecureBank is a modern, secure banking application that demonstrates best practices in DevOps, security, and cloud-native development. The project showcases a complete CI/CD pipeline with various security measures and monitoring solutions.

## Features

- Secure user authentication and authorization
- Account management (deposits, withdrawals, transfers)
- Transaction history tracking
- Dark/Light mode support
- Responsive design for all devices
- Real-time balance updates
- Secure session management

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL Database

### Frontend
- HTML5
- CSS3 with Bootstrap 5
- JavaScript (Vanilla)
- Thymeleaf templating

### DevOps & Infrastructure

#### Version Control & CI/CD
- Git for version control
- Jenkins for CI/CD pipeline orchestration
- SonarQube for code quality analysis
- Trivy for container security scanning

#### Containerization & Orchestration
- Docker for containerization
- Kubernetes for container orchestration
- Helm for Kubernetes package management
- ArgoCD for GitOps practices

#### Infrastructure as Code
- Terraform for infrastructure provisioning
- AWS as cloud provider

#### Monitoring & Observability
- Prometheus for metrics collection
- Grafana for visualization and monitoring
- ELK Stack for log management

## CI/CD Architecture 
![DevOps Secure Bank CI/CD Architecture]()

## Getting Started

### Prerequisites
- Docker
- Kubernetes cluster (for production deployment)

### Docker Deployment

1. Build the Docker image:
   ```bash
   # For macOS with Apple Silicon (M1/M2): if you want to just build and run the application 
   docker build --platform linux/amd64 -t securebank:latest .
   
   docker build -t securebank:latest .
   ```

2. Run the container:
   ```bash
   docker-compose up -d
   ``` 

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Create a Pull Request
 S
## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- The DevOps community for best practices and tools
- All contributors who have helped shape this project

---

⭐ If you find this project helpful, you can leave a star!
