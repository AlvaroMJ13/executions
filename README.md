# Documentación Executions

# Flujo de estados

```mermaid

 graph TD;
    A(1-CREATED) -->|Devuelve idExecution| B(2-SAAS);
    B -->|FeignClient| C[GATEWAY];
    C -->|HttpStatus 200| D(3-SAAS-PENDING);
    D -->|Kafka Consumer| E(4-EMIT);
    E -->|FeignClient| F[GATEWAY];
    F -->|HttpStatus 200| G(5-EMITTED);
```

# Diagrama Entidad Relación

```mermaid

 erDiagram;
	  executions ||--|{ executions_status : "has";
	  executions ||--|| entity : "contains";
	  executions_status ||--|| status : "contains";
	  entity ||--|{ entity_status : "has";
	  status ||--|{ entity_status : "has";
```

# Diagrama de secuencia

```mermaid

 sequenceDiagram;
    REQUEST->>+executions: {"entity":"ONE-APP","gtsMessageId":"31081150"};
    executions->>+executions: [CREATED];
    executions-->>+REQUEST: {"id":"86a31ea9-7d38-4aa9-b7f3-67f23aca6109"};
    executions->>+executions: [SAAS];
    executions->>+GATEWAY: gtsMessageId, idExecution;
    GATEWAY->>+otherMicro: gtsMessageId, idExecution;
    otherMicro-->>-GATEWAY: 200 OK;
    GATEWAY-->>-executions: 200 OK;
    executions->>+executions: [SAAS-PENDING];
    KAFKA->>+executions: {"gtsMessageId":"5464564","idExecution":"86a31ea9-7d38-4aa9-b7f3-67f23aca6109"};
    executions->>+executions: [EMIT];
    executions->>+GATEWAY: gtsMessageId, idExecution;
    GATEWAY->>+otherMicro: gtsMessageId, idExecution;
    otherMicro-->>-GATEWAY: 200 OK;
    GATEWAY-->>-executions: 200 OK;
    executions->>+executions: [EMITTED];
```