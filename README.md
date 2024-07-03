Swagger UI: http://127.0.0.1:8081/swagger-ui/index.html

啟動Redis Docker Service
docker run --name redis -p 6379:6379 -d redis

檢查Redis personal-controller /person (findAll方法) 緩存是否有內容
指令: redis-cli GET "personCache::SimpleKey []"

檢查Redis personal-controller /person (findAll方法) 緩存是否有內容
指令: redis-cli GET "assetsCache::SimpleKey []"

測試webSocket
開啟 WebSocket Test F12 開發人員工具測試
