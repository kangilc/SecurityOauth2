Fluent Bit의 Elasticsearch 출력 플러그인 설정에서 로그 유실 문제를 해결하기 위해 몇 가지 옵션을 수정할 수 있습니다. 다음은 권장되는 수정 사항입니다:

### 1. **Buffer_Size**
- **설명**: 현재 `Buffer_Size`가 10MB로 설정되어 있습니다. 이 값은 Elasticsearch HTTP 서비스에서 응답을 읽는 데 사용되는 버퍼 크기를 지정합니다. 이 값을 늘리면 대량의 로그 데이터를 처리할 때 도움이 될 수 있습니다.
- **수정**: 버퍼 크기를 늘려보세요.
  ```ini
  Buffer_Size 50MB
  ```

### 2. **Retry_Limit**
- **설명**: 로그 전송 실패 시 재시도 횟수를 설정합니다. 기본값은 무제한이지만, 명시적으로 설정하여 재시도 횟수를 관리할 수 있습니다.
- **추가**: 재시도 횟수를 설정합니다.
  ```ini
  Retry_Limit 5
  ```

### 3. **Buffer_Type**
- **설명**: 메모리 기반 버퍼링 대신 파일 시스템 기반 버퍼링을 사용하여 데이터 유실을 방지할 수 있습니다.
- **추가**: 파일 시스템 기반 버퍼링을 사용합니다.
  ```ini
  Buffer_Type filesystem
  Buffer_Path /var/log/fluent-bit-buffer
  ```

### 4. **Logstash_Prefix_Key**
- **설명**: 로그의 인덱스 이름을 동적으로 설정할 수 있습니다. 특정 키의 값을 인덱스 접두사로 사용하여 로그를 더 잘 분류할 수 있습니다.
- **추가**: 동적 인덱스 접두사를 설정합니다.
  ```ini
  Logstash_Prefix_Key log_prefix
  ```

### 5. **Trace_Error**
- **설명**: 오류 발생 시 자세한 로그를 출력하여 문제를 진단할 수 있습니다. 이미 `On`으로 설정되어 있어 유용합니다.
- **유지**: 이 설정은 그대로 유지합니다.

### 수정된 설정 예시
```ini
[OUTPUT]
    Name es
    Host log.javaf.net
    Port 9200
    HTTP_User elastic
    HTTP_Passwd elastic123
    Match kube.was-apps.*
    Buffer_Size 50MB
    Buffer_Type filesystem
    Buffer_Path /var/log/fluent-bit-buffer
    Index was-apps
    Type _doc
    Logstash_Format on
    Logstash_Prefix was-apps
    Logstash_DateFormat %Y%m%d
    Time_Key @timestamp
    Time_Key_Format %Y-%m-%dT%H:%M:%S
    Time_Key_Nanos Off
    Include_Tag_Key Off
    Tag_Key _flb-key
    Generate_ID Off
    Write_Operation create
    Replace_Dots Off
    Trace_Output Off
    Trace_Error On
    Current_Time_Index Off
    Suppress_Type_Name On
    Retry_Limit 5
    Logstash_Prefix_Key log_prefix
```

이러한 설정을 통해 로그 유실 문제를 줄이고, Fluent Bit과 Elasticsearch 간의 통신을 최적화할 수 있습니다. 

[1](https://docs.fluentbit.io/manual/pipeline/outputs/elasticsearch): [Fluent Bit Elasticsearch Output Plugin Documentation](https://docs.fluentbit.io/manual/pipeline/outputs/elasticsearch)

[2](https://fluentbit.net/fluent-bit-operations-and-best-practices/): [Fluent Bit Operations and Best Practices](https://fluentbit.net/fluent-bit-operations-and-best-practices/)

[3](https://github.com/fluent/fluent-bit-docs/blob/master/pipeline/outputs/elasticsearch.md): [Fluent Bit Common Issues and Solutions](https://github.com/fluent/fluent-bit/issues/2664)
