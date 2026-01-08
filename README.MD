# ATS Analyzer (API) — Java + Spring Boot

API para analisar descrições de vagas (Job Descriptions) e extrair estatísticas de termos (unigram/bigram/trigram), destacando palavras-chave recorrentes para ajudar a entender padrões de requisitos (stack, ferramentas, práticas etc.).

Este projeto nasceu para apoiar estudos e preparação para entrevistas, além de ser um projeto de portfólio focado em **backend**: REST API, persistência, pipelines de texto, estatística de termos e evolução incremental por PBs (Product Backlog).

---

## Objetivo do projeto

Receber uma lista de descrições de vagas, executar um pipeline de texto (normalização + tokenização) e gerar métricas como:

- Contagem total de descrições
- Total de tokens processados
- Frequência relativa de termos
- Rankings com destaques (highlight rules)

E **persistir cada execução** (AnalysisRun) no banco para histórico e rastreabilidade.

---

##  Arquitetura (visão geral)

- **Controller**: expõe endpoints REST.
- **Service**: coordena pipeline + estatística + persistência.
- **Preprocessing**:
    - `NormalizationService`: normalização (lowercase, remoções, etc.)
    - `TextPreprocessingService`: tokenização / pipeline
- **Statistics**:
    - `NGramStatisticsService`: unigrams/bigrams/trigrams
    - `TermHighlightRulesService`: regras de destaque
- **Persistence**:
    - entidade `AnalyzesRun` (UUID)
    - `AnalysisRunRepository` (Spring Data JPA)

---

## Status atual (PB)

-  **PB-19**: Persistir `AnalysisRun` (entidade `AnalyzesRun`)
-  Pipeline roda, calcula total de tokens, salva run no banco e retorna `savedAnalysisId`
- **PB-21 (ATUAL)**: Endpoint `GET /analyses/{id}` para buscar uma execução salva e retornar detalhes

---

##  Como executar o projeto

### Pré-requisitos

- Java **21**
- Maven **3.9+**
- Docker Desktop (opcional, para Postgres)
- IntelliJ IDEA (opcional)

---

## Executando local com H2 (mais simples)

> Ideal para testar rápido sem Docker.

### 1) Rodar a aplicação

```mvn spring-boot:run```

### 2) Acessar console H2

Por padrão, o projeto habilita o console em:

```http://localhost:8080/h2-console```

> Observação: se você estiver com profile Postgres ativo, o console H2 pode ainda aparecer mas apontar para outro datasource.
Para usar H2 de verdade, rode com profile h2 (se existir) ou configure o application.properties.

### 3) Configuração típica do H2
Exemplo (caso você use H2 em memória):

- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (vazio)

## Executando com PostgreSQL via Docker

### 1) Subir o Postgres
```docker compose up -d```
### 2) Verificar se subiu
``docker ps``  
- Você deve ver um container do Postgres rodando.
### 3) Porta do Postgres
No seu log você usou:

``jdbc:postgresql://localhost:5433/ats_analyzer``

Ou seja:

- Host: localhost
- Porta: 5433 (porta exposta)
- DB: ats_analyzer

Se seu compose estiver com ``5432:5432``, então a URL vira ``localhost:5432``.

## Endpoints (atuais)

- POST /analysis

Recebe descrições e dispara análise + persistência do AnalysisRun.

```json 
{
    "descriptions": [
        "Conhecimento em Java e Spring Boot...",
        "Experiência com SQL e APIs REST..."
    ]
}
```

Response (exemplo):
```json
{
  "savedAnalysisId": "99115eaa-95c1-4f3e-9b25-2d3d32b0e5f0",
  "descriptionCount": 2
}
```

## Roadmap / Etapas futuras
Próximas entregas prováveis após PB-21:

1. Persistir também os resultados (unigrams/bigrams/trigrams) vinculados a um AnalysisRun
2. Retornar ranking completo via endpoint
3. Regras configuráveis de “highlight”
4. Paginação e filtros (top N, mínimo de frequência, etc.)
5. Exportação (CSV/JSON)


### Autor
Raul Maciel  
Desenvolvedor Backend Java | Spring Boot | APIs REST | Microservices | SQL | Testes Automatizados | Git

Linkedin: https://www.linkedin.com/in/raulgmaciel/



